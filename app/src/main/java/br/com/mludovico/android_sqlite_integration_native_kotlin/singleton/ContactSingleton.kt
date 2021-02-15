package br.com.mludovico.android_sqlite_integration_native_kotlin.singleton

import br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist.model.ContactsVO

object ContactSingleton {
    var lista: MutableList<ContactsVO> = mutableListOf()
}