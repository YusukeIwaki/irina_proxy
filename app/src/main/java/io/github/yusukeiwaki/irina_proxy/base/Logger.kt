package io.github.yusukeiwaki.irina_proxy.base

import android.util.Log

object Logger {
    private const val TAG = "IrinaProxy"

    fun d(log: String, exception: Exception? = null) {
        if (exception == null) {
            Log.d(TAG, log)
        } else {
            Log.d(TAG, log, exception)
        }
    }

    fun e(log: String, exception: Exception) {
        Log.e(TAG, log, exception)
    }
}
