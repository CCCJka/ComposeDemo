package com.jsl.Example.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.jsl.Example.application.MyApplication
import com.jsl.Example.db.bean.UserBean
import java.util.ArrayList

class DbHelper private constructor(){

    companion object {
        private var instance: DbHelper? = null
            get() {
                if (field == null) {
                    field = DbHelper()
                }
                return field
            }
        @Synchronized
        fun get(): DbHelper{
            return instance!!
        }
    }

    val dbCommon = DbCommonClass(MyApplication.context, "userTable.db", 1)

    fun init(){
        val db = dbCommon.writableDatabase
        val cursor = db.query("userTable", null, null, null, null, null, null)
        if (cursor.count > 0){
            Log.i("", "数据库已存在")
        } else{
            Log.i("", "数据库创建成功")
        }
    }

    fun saveUser(userBean: UserBean){
        val db =  dbCommon.writableDatabase
        val userList = getUserList()
        for (item in userList){
            if (userBean.name == item.name){
                db.delete("userTable", "name=?", arrayOf(userBean.name))
                //第二个参数String：where选择语句, 选择哪些行要被删除, 如果为null, 就删除所有行;
                //第三个参数String[]： where语句的参数, 逐个替换where语句中的 "?" 占位符;
            }
        }
        val history = ContentValues().apply {
            put("name", userBean.name)
        }
        db.insert("userTable", null, history)
    }

    fun getUserList(): List<UserBean>{
        var userList: ArrayList<UserBean> = arrayListOf()
        val db = dbCommon.writableDatabase
        val cursor = db.query("userTable", null, null, null, null, null, null)
        if (cursor.moveToFirst()){
            do {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val account = cursor.getString(cursor.getColumnIndex("account"))
                val pwd = cursor.getString(cursor.getColumnIndex("pwd"))
                val email = cursor.getString(cursor.getColumnIndex("email"))
                val phone = cursor.getString(cursor.getColumnIndex("phone"))
                val user = UserBean(name, account, pwd, email, phone)
                userList.add(user)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    fun release(){
        dbCommon.close()
    }

    class DbCommonClass(val context: Context, name: String, version: Int): SQLiteOpenHelper(context, name, null, version){
        private val createTable = "create table userTable (" +
                " id integer primary key autoincrement," +
                " name text," +
                " account text," +
                " pwd text," +
                " email text," +
                " phone text)"

        override fun onCreate(p0: SQLiteDatabase?) {
            p0?.execSQL(createTable)
            Log.i("userTableCreate", "Finish");
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0?.execSQL("drop table if exists userTable")
            onCreate(p0)
        }
    }
}