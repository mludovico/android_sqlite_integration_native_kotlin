package br.com.mludovico.android_sqlite_integration_native_kotlin.application

import android.app.Application
import br.com.mludovico.android_sqlite_integration_native_kotlin.helpers.HelperDB

class ContactApplication: Application() {

    var helperDB: HelperDB? = null
    private set

    companion object {
        lateinit var instance: ContactApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)
    }
}