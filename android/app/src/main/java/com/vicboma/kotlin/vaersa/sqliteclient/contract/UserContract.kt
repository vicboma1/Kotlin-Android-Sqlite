package com.vicboma.kotlin.vaersa.sqliteclient.contract

import android.provider.BaseColumns

object UserContract {
    class Entry : BaseColumns {
        companion object {
            val TABLE_NAME = "users"
            val C_ID = "id"
            val C_NOMBRE = "nombre"
            val C_EDAD = "edad"
            val C_NIF = "nif"
        }
    }
}