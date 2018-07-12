package com.vicboma.kotlin.vaersa.sqliteclient.ddl

import android.database.sqlite.SQLiteDatabase
import com.vicboma.kotlin.vaersa.sqliteclient.contract.UserContract

class DataDefinitionLanguage() {

    companion object {
        val VERSION = 1
        val NAME = "DATABASE.db"

        val CREATE = """
            CREATE TABLE ${UserContract.Entry.TABLE_NAME} (
                                ${UserContract.Entry.C_ID} TEXT PRIMARY KEY,
                                ${UserContract.Entry.C_EDAD}  TEXT,
                                ${UserContract.Entry.C_NIF} TEXT,
                                ${UserContract.Entry.C_NOMBRE} TEXT
                          )"""

        private val DELETE = "DROP TABLE IF EXISTS  ${UserContract.Entry.TABLE_NAME} "
    }


    fun onCreate(db: SQLiteDatabase) =
            db.execSQL(CREATE)

    fun onUpgrade(db: SQLiteDatabase) {
        db.execSQL(DELETE)
        onCreate(db)
    }

}