package io.github.yusukeiwaki.irina_proxy.entry

import android.content.Intent
import android.widget.Toast
import io.github.yusukeiwaki.irina_proxy.base.BaseNoDisplayActivity

class EntryActivity : BaseNoDisplayActivity() {
    override fun handleIntent(intent: Intent?) {
        Toast.makeText(this, "Good bye, irina-redesign.android.com!", Toast.LENGTH_SHORT).show()
    }
}
