package br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist.model

data class ContactsVO(
    var id: Int,
    var name: String = "",
    var phone: String = ""
)
