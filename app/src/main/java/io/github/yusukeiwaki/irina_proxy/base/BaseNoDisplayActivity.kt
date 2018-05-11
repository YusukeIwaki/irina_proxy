package io.github.yusukeiwaki.irina_proxy.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle

abstract class BaseNoDisplayActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
        finish()
    }

    protected abstract fun handleIntent(intent: Intent?)
}
