package com.vicboma.kotlin.vaersa.sqliteclient.dml

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.vicboma.kotlin.vaersa.sqliteclient.contract.UserContract
import com.vicboma.kotlin.vaersa.sqliteclient.ddl.DataDefinitionLanguage
import com.vicboma.kotlin.vaersa.sqliteclient.model.UserModel

class DataManipulationLanguage(){


    init {

    }

    @Throws(SQLiteConstraintException::class)
    fun insert(db: SQLiteDatabase,user: UserModel) =
            db.insert(UserContract.Entry.TABLE_NAME, null, ContentValues().apply {
                put(UserContract.Entry.C_ID, user.id)
                put(UserContract.Entry.C_NOMBRE, user.nombre)
                put(UserContract.Entry.C_EDAD, user.edad)
                put(UserContract.Entry.C_NIF, user.nif)
            }
        )

    @Throws(SQLiteConstraintException::class)
    fun delete(db: SQLiteDatabase,id: String) =
            db.delete(UserContract.Entry.TABLE_NAME, " ${UserContract.Entry.C_ID} LIKE ?", arrayOf(id))


    fun selectOne(db: SQLiteDatabase, id: String) : UserModel? {
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM ${UserContract.Entry.TABLE_NAME} WHERE ${UserContract.Entry.C_ID}='${id}'", null)
        } catch (e: SQLiteException) {
            db.execSQL(DataDefinitionLanguage.CREATE)
            return null
        }

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                return UserModel(id,
                        cursor.getString(cursor.getColumnIndex(UserContract.Entry.C_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(UserContract.Entry.C_EDAD)),
                        cursor.getString(cursor.getColumnIndex(UserContract.Entry.C_NIF))
                )
            }
        }
        return null
    }

    fun selectAll(db: SQLiteDatabase): List<UserModel> {
        val users = ArrayList<UserModel>()
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("SELECT * FROM ${UserContract.Entry.TABLE_NAME}", null)
        } catch (e: SQLiteException) {
            db.execSQL(DataDefinitionLanguage.CREATE)
            return users
        }

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                users.add(
                        UserModel(
                            cursor.getString(cursor.getColumnIndex(UserContract.Entry.C_ID)),
                            cursor.getString(cursor.getColumnIndex(UserContract.Entry.C_NOMBRE)),
                            cursor.getString(cursor.getColumnIndex(UserContract.Entry.C_EDAD)),
                            cursor.getString(cursor.getColumnIndex(UserContract.Entry.C_NIF))
                    )
                )

                cursor.moveToNext()
            }
        }

        return users
    }
}