package br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contact

import android.os.Bundle
import android.view.View
import br.com.mludovico.android_sqlite_integration_native_kotlin.R
import br.com.mludovico.android_sqlite_integration_native_kotlin.application.ContactApplication
import br.com.mludovico.android_sqlite_integration_native_kotlin.bases.BaseActivity
import br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist.model.ContactsVO
import br.com.mludovico.android_sqlite_integration_native_kotlin.singleton.ContactSingleton
import kotlinx.android.synthetic.main.activity_contato.*

class ContactActivity: BaseActivity() {

    var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)
        setupToolBar(toolBar, getString(R.string.contact_title), true)
        setupContato()
        saveButton.setOnClickListener { onSaveHandler() }
        deleteButton.setOnClickListener { onDeleteHandler() }
    }

    private fun setupContato() {
        contactId = intent.getIntExtra("index", -1)
        if (contactId < 0){
            deleteButton.visibility = View.GONE
            return
        }
        var list = ContactApplication.instance.helperDB?.searchContacts("$contactId", true) ?: return
        var contact = list.firstOrNull() ?: return
        nameEdit.setText(contact.name)
        phoneEdit.setText(contact.phone)
    }

    private fun onSaveHandler() {
        val name: String = nameEdit.text.toString()
        val phone: String = phoneEdit.text.toString()
        val contact = ContactsVO(
            contactId,
            name,
            phone
        )
        if (contactId == -1) {
            ContactApplication.instance.helperDB?.createContact(contact)
        } else {
            ContactApplication.instance.helperDB?.updateContact(contact)
        }
        finish()
    }

    private fun onDeleteHandler() {
        if (contactId >= 0) {
            ContactApplication.instance.helperDB?.removeContact(contactId)
            finish()
        }
    }
}