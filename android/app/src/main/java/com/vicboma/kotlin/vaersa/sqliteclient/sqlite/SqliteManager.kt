package com.vicboma.kotlin.vaersa.sqliteclient.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.vicboma.kotlin.vaersa.sqliteclient.contract.UserContract
import com.vicboma.kotlin.vaersa.sqliteclient.ddl.DataDefinitionLanguage
import com.vicboma.kotlin.vaersa.sqliteclient.dml.DataManipulationLanguage
import com.vicboma.kotlin.vaersa.sqliteclient.model.UserModel

class SqliteManager(context: Context, val ddl: DataManipulationLanguage, val dml: DataDefinitionLanguage) : SQLiteOpenHelper(context, DataDefinitionLanguage.NAME, null, DataDefinitionLanguage.VERSION) {

    override fun onCreate(p0: SQLiteDatabase) = dml.onCreate(p0)

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int)  = dml.onUpgrade(p0)

    fun insert(user: UserModel) = ddl.insert(writableDatabase,user)

    @Throws(SQLiteConstraintException::class)
    fun delete(id: String) = ddl.delete(writableDatabase,id)

    fun selectOne(id: String) : UserModel? = ddl.selectOne(writableDatabase,id)

    fun selectAll(): List<UserModel>  = ddl.selectAll(writableDatabase)

}