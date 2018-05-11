package io.github.yusukeiwaki.irina_proxy.viewer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import io.github.yusukeiwaki.irina_proxy.R
import io.github.yusukeiwaki.irina_proxy.base.BaseNoDisplayActivity
import io.github.yusukeiwaki.irina_proxy.base.Logger

/**
 * 開く インテントに反応する？かも？
 */
class ViewIntentProxyActivity : BaseNoDisplayActivity() {
    companion object {
        private const val EXTRA_FROM = "io.github.yusukeiwaki.irina_proxy.intent.extra.FROM"
        private const val FROM_ME = "ViewIntentProxyActivity"
    }

    private val isFromMe get() = FROM_ME.equals(intent?.getStringExtra(EXTRA_FROM))

    override fun handleIntent(intent: Intent?) {
        intent?.data?.let { uri ->
            Logger.d("Intercept url: ${uri}")
            openUrl(proxy(uri))
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

    private fun openUrl(uri: Uri) {
        Logger.d("Open url: ${uri}")
        val intentParams: Bundle? = intent.extras
        val targetIntent = Intent(intent.action).also { intent ->
            intent.data = uri
            intentParams?.let{ intent.putExtras(it) }
        }
        startActivity(targetIntent)
    }
}
