package io.github.yusukeiwaki.irina_proxy.sender

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import io.github.yusukeiwaki.irina_proxy.R
import io.github.yusukeiwaki.irina_proxy.base.BaseNoDisplayActivity
import io.github.yusukeiwaki.irina_proxy.base.Logger

/**
 * 共有インテントに反応する
 */
class SendIntentProxyActivity : BaseNoDisplayActivity() {
    override fun handleIntent(intent: Intent?) {
        intent?.let {
            getUrlFromIntent(it)?.let { uri ->
                Logger.d("Intercept url: ${uri}")
                share(proxy(uri))
            }
        }
    }

    private fun getUrlFromIntent(intent: Intent): Uri? {
        return intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            try {
                Uri.parse(it)
            } catch (e: Exception) {
                Logger.e("failed to parse url: ${it}", e)
                null
            }
        }
    }

    private fun proxy(uri: Uri): Uri {
        if (getString(R.string.irina_hostname).equals(uri.authority)) {
            return Uri.Builder()
                    .scheme(uri.scheme)
                    .authority(getString(R.string.dev_android_hostname))
                    .path(uri.path)
                    .query(uri.query)
                    .fragment(uri.fragment)
                    .build()
        } else {
            return uri
        }
    }

    private fun share(uri: Uri) {
        Logger.d("Share url: ${uri}")
        // ここに入ってくることには getIntent() のnullチェックも、EXTRA_TEXTパラメータがないケースも排除されてるはず。
        val intentParams: Bundle = intent.extras!!.also { bundle ->
            bundle.putString(Intent.EXTRA_TEXT, uri.toString())
        }
        val targetIntent = Intent(Intent.ACTION_SEND).also { intent ->
            intent.setType("text/plain")
            intent.putExtras(intentParams)
        }
        val chosenIntentDetector = PendingIntent.getService(this, 0, ChosenIntentDetectingService.newIntent(this), PendingIntent.FLAG_ONE_SHOT)
        val chooserIntent = Intent.createChooser(targetIntent, "Share", chosenIntentDetector.intentSender)

        startActivity(chooserIntent)
    }
}
