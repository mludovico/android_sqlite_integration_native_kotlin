package br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mludovico.android_sqlite_integration_native_kotlin.R
import br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist.model.ContactsVO
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactAdapter(
    private val context: Context,
    private val list: List<ContactsVO>,
    private val onClick: ((Int) -> Unit)
): RecyclerView.Adapter<ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = list[position]
        with(holder.itemView) {
            nameText.text = contact.name
            phoneText.text = contact.phone
            contactItem.setOnClickListener { onClick(position) }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)