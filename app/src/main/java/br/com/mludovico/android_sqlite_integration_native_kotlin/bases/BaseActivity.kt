package br.com.mludovico.android_sqlite_integration_native_kotlin.bases

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.mludovico.android_sqlite_integration_native_kotlin.R

open class BaseActivity: AppCompatActivity() {
    protected fun setupToolBar(toolbar: Toolbar, title: String, navigationBack: Boolean) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(navigationBack)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}