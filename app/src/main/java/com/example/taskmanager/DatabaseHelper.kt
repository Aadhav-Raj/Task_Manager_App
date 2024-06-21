package com.example.taskmanager

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues



class DatabaseHelper (context: Context):SQLiteOpenHelper(context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION){

    companion object{
        val DATABASE_NAME="task.db"
        val DATABASE_VERSION=1

        const val TABLE_NAME="MY_TASK"
        const val COL_ID="id"
        const val COL_HEAD="head"
        const val COL_DES="desciption"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery="""
            CREATE TABLE $TABLE_NAME(
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_HEAD TEXT,
            $COL_DES TEXT)
        """.trimIndent()

        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(head:String,desc:String):Long{
        val values=ContentValues().apply {
            put(COL_HEAD,head)
            put(COL_DES,desc)
        }
        return  writableDatabase.insert(TABLE_NAME,null,values)

    }

    fun readData():List<DataModel>{
        val dataList=mutableListOf<DataModel>()
        val cursor:Cursor
        cursor=readableDatabase.query(TABLE_NAME,null,null,null,null,null,null)

        with(cursor){
            while (moveToNext()){
                val id=getLong(getColumnIndexOrThrow(COL_ID))
                val head=getString(getColumnIndexOrThrow(COL_HEAD))
                val desc=getString(getColumnIndexOrThrow(COL_DES))
                dataList.add(DataModel(head,desc))
            }
        }

        cursor.close()
        return dataList
    }

}