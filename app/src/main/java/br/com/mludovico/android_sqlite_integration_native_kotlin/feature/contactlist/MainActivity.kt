package br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mludovico.android_sqlite_integration_native_kotlin.R
import br.com.mludovico.android_sqlite_integration_native_kotlin.bases.BaseActivity
import br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contact.ContactActivity
import br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist.adapter.ContactAdapter
import br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist.adapter.ContactViewHolder
import br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist.model.ContactsVO
import br.com.mludovico.android_sqlite_integration_native_kotlin.singleton.ContactSingleton
import kotlinx.android.synthetic.main.activity_main.*

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
        searchBar.setOnClickListener { onSearchHandler() }
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

    private fun onSearchHandler() {

    }

    private fun generateContactList() {
        ContactSingleton.lista.add(ContactsVO(1, "Fulano", "(19) 963257441"))
        ContactSingleton.lista.add(ContactsVO(2, "Beltrano", "(19) 697786"))
        ContactSingleton.lista.add(ContactsVO(3, "Ciclano", "(19) 6987523"))
    }
}