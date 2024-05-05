package com.example.realtest

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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


class FindIDActivity:AppCompatActivity() {

    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findid)

        var emailway = 0
        var phoneway = 1


        var backaction : ImageButton = findViewById<ImageButton>(R.id.backaction)
        var gotologin : Button = findViewById<Button>(R.id.gotoLogin)
        var gotofindPW : Button = findViewById<Button>(R.id.gotofindPW)
        var gotoSignin : TextView = findViewById<TextView>(R.id.gotoSignin)

        var findIDPhone : Button = findViewById<Button>(R.id.findIDPhone)
        var findIDEmail : Button = findViewById<Button>(R.id.findIDEmail)

        var inputNAME : EditText = findViewById<EditText>(R.id.inputNAME)
        var inputPHONE : EditText = findViewById<EditText>(R.id.inputPHONE)
        var inputEMAIL : EditText = findViewById<EditText>(R.id.inputEMAIL)
        var searchID : Button = findViewById<Button>(R.id.searchID)
        var resultIDarea : LinearLayout = findViewById<LinearLayout>(R.id.resultIDarea)
        var gotoSigninArea: LinearLayout = findViewById<LinearLayout>(R.id.gotoSigninArea)
        var resultID : TextView = findViewById<TextView>(R.id.resultID)
        inputEMAIL.visibility  = View.GONE
        resultIDarea.visibility=View.GONE
        gotoSigninArea.visibility = View.GONE

        findIDPhone.setOnClickListener{
            findIDEmail.setTextColor(getResources().getColor(R.color.gray))
            findIDEmail.setBackgroundResource(R.drawable.findtypebtnnone)
            findIDPhone.setTextColor(getResources().getColor(R.color.black))
            findIDPhone.setBackgroundResource(R.drawable.findtypebtnclick)
            inputEMAIL.visibility=View.GONE
            inputPHONE.visibility=View.VISIBLE
            //findIDEmail.setBackgroundResource(R.drawable.findtypebtnnone)
            // findIDEmail.setTextColor(Color.GRAY)
            //inputemailarea.visibility = View.GONE
            //inputphonearea.visibility = View.VISIBLE

            emailway = 0
            phoneway=1

        }

        findIDEmail.setOnClickListener{
            findIDPhone.setTextColor(getResources().getColor(R.color.gray))
            findIDPhone.setBackgroundResource(R.drawable.findtypebtnnone)
            findIDEmail.setTextColor(getResources().getColor(R.color.black))
            findIDEmail.setBackgroundResource(R.drawable.findtypebtnclick)
            inputEMAIL.visibility=View.VISIBLE
            inputPHONE.visibility=View.GONE

            //findIDPhone.setBackgroundResource(R.drawable.findtypebtnnone)
            // findIDPhone.setTextColor(Color.GRAY)
            //inputemailarea.visibility = View.VISIBLE
            //inputphonearea.visibility = View.GONE

            emailway = 1
            phoneway=0

        }

        myHelper = myDBHelper(this)

        searchID.setOnClickListener{

            sqlDB = myHelper.readableDatabase
            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM userinfo1;", null)

            var a=0

            if(phoneway==1) {

                while (cursor.moveToNext()) {

                    if (cursor.getString(2).equals(inputNAME.text.toString())) {

                        if (cursor.getString(4).equals(inputPHONE.text.toString())) {

                            resultID.setTextColor(Color.BLACK)
                            resultID.setText("고객님의 정보와 일치하는 아이디 입니다.\n\n" + cursor.getString(0) + "")
                            a = 1

                        }

                    }

                }

                if (a == 0) {

                    resultID.setTextColor(Color.RED)
                    resultID.setText("일치하는 정보가 없습니다!")
                    gotoSigninArea.visibility = View.VISIBLE

                }

            }

            else if (emailway==1){

                while (cursor.moveToNext()) {

                    if (cursor.getString(2).equals(inputNAME.text.toString())) {

                        if (cursor.getString(3).equals(inputEMAIL.text.toString())) {

                            resultID.setTextColor(Color.BLACK)
                            resultID.setText("고객님의 정보와 일치하는 아이디 입니다.\n\n" + cursor.getString(0) + "")
                            a = 1

                        }

                    }

                }

                if (a == 0) {

                    resultID.setTextColor(Color.RED)
                    resultID.setText("일치하는 정보가 없습니다!")
                    gotoSigninArea.visibility = View.VISIBLE

                }

            }

            //if(휴대폰인증형식)
            //if(이메일인증형식)

            /*
            //일치하는 정보가 있을 때
            resultID.setTextColor(Color.BLACK)
            resultID.setText("고객님의 정보와 일치하는 아이디 입니다.\n\nuser***")
            */
            
            /*
            //일치하는 정보가 없을 때
            resultID.setTextColor(Color.RED)
            resultID.setText("일치하는 정보가 없습니다!")
            gotoSigninArea.visibility = View.VISIBLE
            */
            resultIDarea.visibility=View.VISIBLE//결과 화면 보여줌

        }

        backaction.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        gotologin.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        gotoSignin.setOnClickListener{
            val intent = Intent(this,SigninActivity::class.java)
            startActivity(intent)
        }
        gotofindPW.setOnClickListener{
            val intent = Intent(this,FindPWActivity::class.java)
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