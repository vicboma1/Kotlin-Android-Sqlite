package com.vicboma.kotlin.vaersa.sqliteclient

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.vicboma.kotlin.vaersa.sqliteclient.ddl.DataDefinitionLanguage
import com.vicboma.kotlin.vaersa.sqliteclient.dml.DataManipulationLanguage
import com.vicboma.kotlin.vaersa.sqliteclient.model.UserModel
import com.vicboma.kotlin.vaersa.sqliteclient.sqlite.SqliteManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    val ddl by lazy {  DataDefinitionLanguage() }
    val dml by lazy {  DataManipulationLanguage() }
    val sqliteManager by lazy { SqliteManager(this,dml,ddl) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun insert(v: View) =
        sqliteManager.insert(
                UserModel(
                        this.txt_id.text.toString(),
                        this.txt_nombre.text.toString(),
                        this.txt_edad.text.toString(),
                        this.txt_nif.text.toString()
                )
        ).let {
            clearInputs()
            this.textview_result.text = "Added user : $it"
            this.ll_entries.removeAllViews()
        }

    private fun clearInputs() {
        this.txt_id.setText("")
        this.txt_edad.setText("")
        this.txt_nif.setText("")
        this.txt_nombre.setText("")
    }

    fun delete(v:View) =
            sqliteManager.delete(this.txt_id.text.toString())
                          .let {
                              this.textview_result.text = "Deleted user : $it"
                              this.ll_entries.removeAllViews()
            }

    fun selectAll(v:View) =
        sqliteManager.selectAll()
                     .let {
                         this.ll_entries.removeAllViews()
                         it.forEach {
                             var tv_user = TextView(this)
                             tv_user.apply {
                                 textSize = 30F
                                 text = it.toString()
                             }
                             this.ll_entries.addView(tv_user)
                         }
                         this.textview_result.text = "Fetched " + it.size + " users"
                     }


    fun selectOne(v:View) =
            this.ll_entries
                .apply {
                    removeAllViews()
                    addView(
                            TextView(this@MainActivity)
                                    .apply {
                                        textSize = 25F
                                        text = sqliteManager.selectOne(this@MainActivity.txt_id.text.toString()).toString()
                                    }
                    )
                }.let { this.textview_result.text = "Fetched One user" }
}
