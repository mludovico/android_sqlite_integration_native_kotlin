package br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contact

import android.os.Bundle
import android.view.View
import br.com.mludovico.android_sqlite_integration_native_kotlin.R
import br.com.mludovico.android_sqlite_integration_native_kotlin.bases.BaseActivity
import br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist.model.ContactsVO
import br.com.mludovico.android_sqlite_integration_native_kotlin.singleton.ContactSingleton
import kotlinx.android.synthetic.main.activity_contato.*

class ContactActivity: BaseActivity() {

    var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)
        setupToolBar(toolBar, getString(R.string.contact_title), true)
        setupContato()
        saveButton.setOnClickListener { onSaveHandler() }
        deleteButton.setOnClickListener { onDeleteHandler() }
    }

    private fun setupContato() {
        index = intent.getIntExtra("index", -1)
        if (index < 0){
            deleteButton.visibility = View.GONE
            return
        }
        nameEdit.setText(ContactSingleton.lista[index].name)
        phoneEdit.setText(ContactSingleton.lista[index].phone)
    }

    private fun onSaveHandler() {
        val name: String = nameEdit.text.toString()
        val phone: String = phoneEdit.text.toString()
        val contact = ContactsVO(
            0,
            name,
            phone
        )
        if (index == -1) {
            ContactSingleton.lista.add(contact)
        } else {
            ContactSingleton.lista.set(index, contact)
        }
        finish()
    }

    private fun onDeleteHandler() {
        if (index >= 0) {
            ContactSingleton.lista.removeAt(index)
            finish()
        }
    }
}