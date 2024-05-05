package com.example.realtest

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.DialogInterface
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import android.database.Cursor

import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {

    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var backaction : ImageButton = findViewById<ImageButton>(R.id.backaction)
        var indexlogin : Button = findViewById<Button>(R.id.indexLogin)
        var gotofindID : TextView = findViewById<TextView>(R.id.gotofindID)
        var gotofindPW : TextView = findViewById<TextView>(R.id.gotofindPW)
        var gotoSignin : TextView = findViewById<TextView>(R.id.gotoSignin)
        var inputerror : TextView = findViewById<TextView>(R.id.inputerror)
        var inputID : EditText = findViewById<EditText>(R.id.inputID)
        var inputPW : EditText = findViewById<EditText>(R.id.inputPW)

        myHelper = myDBHelper(this)

        inputerror.visibility= View.GONE

        backaction.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        indexlogin.setOnClickListener{

            /*
            if(inputID.equals("")||inputPW.equals("")||TextUtils.isEmpty(inputID.getText())||TextUtils.isEmpty(inputPW.getText())){
            //공백,null
            // 공백 check너무 길거나 error시 : ||TextUtils.isEmpty(inputID.getText())||TextUtils.isEmpty(inputPW.getText()) 삭제
                inputerror.visibility= View.VISIBLE
            }
            //else if{//DB에 없을 경우
            // inputerror.visibility= View.VISIBLE
            // }
            else{//DB에 사용자 정보가 일치하는 경우
                FINISH()
            }*/
            sqlDB = myHelper.readableDatabase
            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM userinfo1;", null)

            while (cursor.moveToNext()) {
                if(cursor.getString(0).equals(inputID.text.toString())){

                    if(cursor.getString(1).equals(inputPW.text.toString())){
                        val intent = Intent(this,IndexActivity::class.java)
                        intent.putExtra("ID",inputID.text.toString())

                        startActivity(intent)
                        finish()
                    }

                }
            }




        }
        gotofindID.setOnClickListener{
            val intent = Intent(this,FindIDActivity::class.java)
            startActivity(intent)
        }
        gotofindPW.setOnClickListener{
            val intent = Intent(this,FindPWActivity::class.java)
            startActivity(intent)
        }

        gotoSignin.setOnClickListener{
            val intent = Intent(this,SigninActivity::class.java)
            startActivity(intent)
        }

    }
    inner class myDBHelper(context:Context):SQLiteOpenHelper(context,"CC",null,1) {
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS userinfo1")
            db!!.execSQL("DROP TABLE IF EXISTS Board1")
            db!!.execSQL("DROP TABLE IF EXISTS request")
            onCreate(db)
        }

        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL("CREATE TABLE userinfo1(ID CHAR(20) PRIMARY KEY,PW CHAR(20), NAME CHAR(20), EMAIL CHAR(20), PHONE CHAR(20))")
            p0!!.execSQL("CREATE TABLE Board1(bwseq INTEGER PRIMARY KEY, bid CHAR(20), baction INTEGER ,bstart CHAR(20), barrive CHAR(20), bplace CHAR(20), bhour INTEGER, byear INTEGER, bppeople INTEGER, bpeople INTEGER, bbankname CHAR(20), baccount CHAR(20),bcontent CHAR(200),bpdate CHAR(50),bakrka INTEGER,bminute INTEGER,bmonth INTEGER,bday INTEGER)")
            p0!!.execSQL("CREATE TABLE request(bwseq INTEGER, bid CHAR(20), aid CHAR(20))")
        }
    }
}