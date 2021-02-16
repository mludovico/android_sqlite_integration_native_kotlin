package br.com.mludovico.android_sqlite_integration_native_kotlin.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.mludovico.android_sqlite_integration_native_kotlin.feature.contactlist.model.ContactsVO

class HelperDB(
    context: Context?
): SQLiteOpenHelper(context, DATABASE_NAME, null, CURRENT_VERSION) {
    companion object {
        private val DATABASE_NAME = "contato.db"
        private val CURRENT_VERSION = 2
    }

    val TABLE_NAME = "contato"
    val COLUMN_ID = "id"
    val COLUMN_NAME = "name"
    val COLUMN_PHONE = "phone"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "$COLUMN_ID INTEGER NOT NULL," +
            "$COLUMN_NAME TEXT NOT NULL," +
            "$COLUMN_PHONE TEXT NOT NULL," +
            " " +
            "PRIMARY KEY ($COLUMN_ID AUTOINCREMENT)" +
            ");"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }

    fun searchContacts(query: String, isSearchById: Boolean = false): List<ContactsVO> {
        val db = readableDatabase?: return mutableListOf()
        val list: MutableList<ContactsVO> = mutableListOf()
        val where: String?
        var queryClause: Array<String> = arrayOf()
        if (isSearchById){
            where = "$COLUMN_ID = ?"
            queryClause = arrayOf(query)
        } else {
            where = "$COLUMN_NAME LIKE ?"
            queryClause = arrayOf("%$query%")
        }
        val cursor = db.query(TABLE_NAME, null, where, queryClause, null, null, null) ?: return mutableListOf()
        if(cursor == null) {
            db.close()
            return list
        }
        while (cursor.moveToNext()) {
            val contact = ContactsVO(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
            )
            list.add(contact)
        }
        db.close()
        return list
    }

    fun createContact(contact: ContactsVO) {
        val db = writableDatabase?: return
        val content = ContentValues()
        content.put(COLUMN_NAME, contact.name)
        content.put(COLUMN_PHONE, contact.phone)
        db.insert(TABLE_NAME, null, content)
        db.close()
    }

    fun removeContact(id: Int) {
        val db = writableDatabase ?: return
        val sql = "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val queryClause = arrayOf(id.toString())
        db.execSQL(sql, queryClause)
        db.close()
    }

    fun updateContact(contact: ContactsVO) {
        val db = writableDatabase ?: return
        val sql = "UPDATE $TABLE_NAME SET $COLUMN_NAME = ?, $COLUMN_PHONE =  ? WHERE $COLUMN_ID = ?"
        val queryClause = arrayOf(contact.name, contact.phone, contact.id)
        db.execSQL(sql, queryClause)
        db.close()
    }
}