package br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mludovico.android_sqlite_integration_native_kotlin.R
import br.com.mludovico.android_sqlite_integration_native_kotlin.application.ContactApplication
import br.com.mludovico.android_sqlite_integration_native_kotlin.bases.BaseActivity
import br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contact.ContactActivity
import br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist.adapter.ContactAdapter
import br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist.model.ContactsVO
import br.com.mludovico.android_sqlite_integration_native_kotlin.singleton.ContactSingleton
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : BaseActivity() {

    var adapter: ContactAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        generateContactList()
        setupToolBar(toolBar, getString(R.string.home_title), false)
        setupRecyclerView()
        setuOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ContactAdapter(this, ContactSingleton.lista) {onClickRecyclerViewItem(it)}
        recyclerView.adapter = adapter
    }

    private fun setuOnClickListeners() {
        addButton.setOnClickListener { onAddHandler() }
        searchEdit.setOnEditorActionListener { _, _, _ -> onSearchHandler() }
        searchButton.setOnClickListener { onSearchHandler() }
    }

    private fun onAddHandler() {
        val intent = Intent(this, ContactActivity().javaClass)
        startActivity(intent)
    }

    private fun onClickRecyclerViewItem(index: Int) {
        val intent = Intent(this, ContactActivity().javaClass)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onSearchHandler(): Boolean {
        val query = searchEdit.text.toString()
        var filteredList: List<ContactsVO> = ContactSingleton.lista
        try {
            filteredList =
                ContactApplication.instance.helperDB?.searchContacts(query) ?: mutableListOf()
        } catch (ex: Exception) {
            Log.e("Helper", ex.message ?: "unknown")
        }
        adapter = ContactAdapter(this, filteredList) { onClickRecyclerViewItem(it)}
        recyclerView.adapter = adapter
        Toast.makeText(this, "Buscando por $query", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun generateContactList() {
        ContactSingleton.lista.add(ContactsVO(1, "Fulano", "(19) 963257441"))
        ContactSingleton.lista.add(ContactsVO(2, "Beltrano", "(19) 697786"))
        ContactSingleton.lista.add(ContactsVO(3, "Ciclano", "(19) 6987523"))
    }
}