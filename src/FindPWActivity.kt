package com.example.realtest

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.*
import android.widget.*
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import android.text.Editable
import android.text.TextWatcher
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.*
import kotlinx.android.synthetic.main.boardlist.view.*
import java.lang.NullPointerException
import android.os.SystemClock
import android.widget.*
import java.time.Month
import java.time.Year

import kotlin.math.log

@Suppress("deprecation")
class FindPWActivity:AppCompatActivity(){

    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var sqlDB2: SQLiteDatabase

    fun failreset(){
        Toast.makeText(this,"비밀번호를 정확히 입력해 주세요.",Toast.LENGTH_LONG)
    }

    fun gotoLogin(){
        val alerd = AlertDialog.Builder(this)
        val intent = Intent(this,LoginActivity::class.java)
        alerd.setTitle("비밀번호 재설정 완료")
        alerd.setMessage("로그인 화면으로 돌아갑니다.")
        alerd.setPositiveButton("확인", DialogInterface.OnClickListener{ dialog, which->
            startActivity(intent)
        })
        alerd.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findpw)

        var emailway = 0
        var phoneway = 1

        var backaction : ImageButton = findViewById<ImageButton>(R.id.backaction)
        var findPWPhone : Button = findViewById<Button>(R.id.findPWPhone)
        var findPWEmail : Button = findViewById<Button>(R.id.findPWEmail)
        var inputNAME : EditText = findViewById<EditText>(R.id.inputNAME)

        var inputID : EditText = findViewById<EditText>(R.id.inputID)
        var inputPHONE : EditText = findViewById<EditText>(R.id.inputPHONE)
        var inputEMAIL : EditText = findViewById<EditText>(R.id.inputEMAIL)
        var nonePW : TextView = findViewById<TextView>(R.id.nonePW)
        var resultPWArea : LinearLayout = findViewById<LinearLayout>(R.id.resultPWArea)
        var searchPW : Button = findViewById<Button>(R.id.searchPW)
        var resetPW : Button = findViewById<Button>(R.id.resetPW)
        var inputPW : EditText = findViewById<EditText>(R.id.inputPW)
        var inputPWch : EditText = findViewById<EditText>(R.id.inputPWch)

        var iID=inputID.text.toString()
        var iPW=inputPW.text.toString()
        var iPWc=inputPWch.text.toString()

        inputEMAIL.visibility = View.GONE
        nonePW.visibility = View.GONE
        resultPWArea.visibility = View.GONE


        findPWPhone.setOnClickListener{
            findPWEmail.setTextColor(getResources().getColor(R.color.gray))
            findPWEmail.setBackgroundResource(R.drawable.findtypebtnnone)
            findPWPhone.setTextColor(getResources().getColor(R.color.black))
            findPWPhone.setBackgroundResource(R.drawable.findtypebtnclick)

            nonePW.visibility = View.GONE
            resultPWArea.visibility = View.GONE

            inputEMAIL.visibility = View.GONE
            inputPHONE.visibility = View.VISIBLE

            emailway = 0
            phoneway=1

        }
        findPWEmail.setOnClickListener{
            findPWPhone.setTextColor(getResources().getColor(R.color.gray))
            findPWPhone.setBackgroundResource(R.drawable.findtypebtnnone)
            findPWEmail.setTextColor(getResources().getColor(R.color.black))
            findPWEmail.setBackgroundResource(R.drawable.findtypebtnclick)

            nonePW.visibility = View.GONE
            resultPWArea.visibility = View.GONE

            inputEMAIL.visibility = View.VISIBLE
            inputPHONE.visibility = View.GONE

            emailway = 1
            phoneway=0

        }

        myHelper = myDBHelper(this)

        searchPW.setOnClickListener{

            sqlDB = myHelper.readableDatabase
            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM userinfo1;", null)

            var a=0

            if(phoneway==1) {

                while (cursor.moveToNext()) {

                    if (cursor.getString(2).equals(inputNAME.text.toString())) {

                        if (cursor.getString(0).equals(inputID.text.toString())) {

                            if(cursor.getString(4).equals(inputPHONE.text.toString())){

                                nonePW.visibility = View.VISIBLE
                                nonePW.setText("고객님의 정보와 일치하는 비밀번호 입니다.\n\n" + cursor.getString(1) + "")
a=1
                            }

                        }

                    }

                }

                if (a == 0) {

                    nonePW.visibility = View.VISIBLE
                    inputPW.visibility = View.GONE
                    inputPWch.visibility = View.GONE

                }

            }

            else if (emailway==1){

                while (cursor.moveToNext()) {

                    if (cursor.getString(2).equals(inputNAME.text.toString())) {

                        if (cursor.getString(0).equals(inputID.text.toString())) {

                            if(cursor.getString(3).equals(inputEMAIL.text.toString())) {

                                nonePW.visibility = View.VISIBLE
                                nonePW.setText("고객님의 정보와 일치하는 비밀번호 가있습다.\n\n")
                                a = 1

                            }

                        }

                    }

                }

                if (a == 0) {

                    nonePW.visibility = View.VISIBLE
                    inputPW.visibility = View.GONE
                    inputPWch.visibility = View.GONE

                }

            }

            resultPWArea.visibility = View.VISIBLE
            /*
            //일치하는 정보가 없을 때
                //일치하는 정보가 없으니 비밀번호 재설정 불가능
                nonePW.visibility = View.VISIBLE
                ipnutPW.visibility = View.GONE
                inputPWch.visibility = View.GONE
            */
            /*
            //일치하는 정보가 있을 때
            nonePW.visibility = View.VISIBLE
                //일치하는 정보가 있으니 비밀번호 재설정 가능
            */
        }

        resetPW.setOnClickListener{

            //gotoLogin()
            if(inputPW.text.toString()==inputPWch.text.toString()){

                sqlDB2 = myHelper.writableDatabase

                sqlDB2.execSQL("UPDATE userinfo1 SET PW='"+inputPW.text.toString()+"' WHERE ID='"+inputID.text.toString()+"';")

                //비밀번호 재설정
                //재설정 완료후 로그인화면으로
                gotoLogin()
            }
            else{
                //비밀번호 재설정 불가능
                failreset()
            }
        }
        backaction.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
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