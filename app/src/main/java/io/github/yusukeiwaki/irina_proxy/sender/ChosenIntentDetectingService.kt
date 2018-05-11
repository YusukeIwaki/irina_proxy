package io.github.yusukeiwaki.irina_proxy.sender

import android.app.IntentService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import io.github.yusukeiwaki.irina_proxy.base.Logger

class ChosenIntentDetectingService : IntentService("ChosenIntentDetectingService") {

    companion object {
        fun newIntent(context: Context) = Intent(context, ChosenIntentDetectingService::class.java)
    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.getParcelableExtra<ComponentName>(Intent.EXTRA_CHOSEN_COMPONENT)?.let { componentName ->
            handle(componentName)
        }
    }

    private fun handle(componentName: ComponentName) {
        Logger.d("Shared to: ${componentName.packageName} -> ${componentName.className}")
    }
}
