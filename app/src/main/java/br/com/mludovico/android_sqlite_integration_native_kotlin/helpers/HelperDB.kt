package br.com.mludovico.android_sqlite_integration_native_kotlin.helpers

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
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_ID INTEGER NOT NULL," +
            "$COLUMN_NAME TEXT NOT NULL," +
            "$COLUMN_PHONE TEXT NOT NULL" +
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

    fun searchContacts(query: String): List<ContactsVO> {
        val db = readableDatabase?: return mutableListOf()
        var list: MutableList<ContactsVO> = mutableListOf<ContactsVO>()
        val sql = "SELECT * FROM $TABLE_NAME"
        var cursor = db.rawQuery(sql, arrayOf()) ?: return mutableListOf()
        while (cursor.moveToNext()) {
            var contact = ContactsVO(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
            )
            list.add(contact)
        }
        return list
    }
}