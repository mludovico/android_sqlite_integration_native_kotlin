package br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
        setupToolBar(toolBar, getString(R.string.home_title), false)
        setupRecyclerView()
        setupOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        onSearchHandler()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupOnClickListeners() {
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
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            var filteredList: List<ContactsVO> = listOf()
            try {
                filteredList =
                    ContactApplication.instance.helperDB?.searchContacts(query) ?: mutableListOf()
            } catch (ex: Exception) {
                Log.e("Helper", ex.message ?: "unknown")
            }
            runOnUiThread {
                adapter = ContactAdapter(this, filteredList) { onClickRecyclerViewItem(it) }
                recyclerView.adapter = adapter
                progress.visibility = View.GONE
                Toast.makeText(this, "Buscando por $query", Toast.LENGTH_SHORT).show()
            }
        }).start()
        return true
    }

}