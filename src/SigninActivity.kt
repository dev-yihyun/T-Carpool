package com.example.realtest

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


import java.util.regex.Pattern

class SigninActivity:AppCompatActivity() {

    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase

    fun gotoLogin(){
        val alerd = AlertDialog.Builder(this)
        val intent = Intent(this,LoginActivity::class.java)
        alerd.setTitle("회원가입 완료")
        alerd.setMessage("로그인 화면으로 돌아갑니다.")
        alerd.setPositiveButton("확인", DialogInterface.OnClickListener{ dialog, which->
            startActivity(intent)
        })
        alerd.show()
    }
    fun failsignin(){
        Toast.makeText(this,"입력란에 정확히 입력해 주세요.",Toast.LENGTH_LONG)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        var inputIDerror:TextView = findViewById<TextView>(R.id.inputIDerror)
        var inputPWerror:TextView = findViewById<TextView>(R.id.inputPWerror)
        var inputNAMEerror:TextView = findViewById<TextView>(R.id.inputNAMEerror)
        var inputEMAILerror:TextView = findViewById<TextView>(R.id.inputEMAILerror)
        var inputPHONEerror:TextView = findViewById<TextView>(R.id.inputPHONEerror)

        var inputID:EditText = findViewById<EditText>(R.id.inputID)
        var inputPW:EditText = findViewById<EditText>(R.id.inputPW)
        var inputNAME:EditText=findViewById<EditText>(R.id.inputNAME)
        var inputEMAIL:EditText=findViewById<EditText>(R.id.inputEMAIL)
        var inputPHONE:EditText = findViewById<EditText>(R.id.inputPHONE)

        var signinbtn:Button = findViewById<Button>(R.id.signinbtn)

        myHelper = myDBHelper(this)


        inputIDerror.visibility=View.GONE
        inputPWerror.visibility=View.GONE
        inputNAMEerror.visibility=View.GONE
        inputEMAILerror.visibility=View.GONE
        inputPHONEerror.visibility=View.GONE

        inputID.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {//입력이 끝날때 작동
                if(inputID.equals("")){
                    inputIDerror.visibility=View.VISIBLE
                    inputIDerror.setText("아이디를 입력해 주세요")
                    inputIDerror.setTextColor(Color.RED)
                }
                //아이디 문자열길이(5~10글자) 체크
                else if(inputID.length()>10||inputID.length()<5){
                    inputIDerror.visibility=View.VISIBLE
                    inputIDerror.setText("5~10자의 영문 소문자,숫자만 사용 가능합니다.")
                    inputIDerror.setTextColor(Color.RED)
                }
                /*
               //DB 아이디 중복
               else if(){
                   inputIDerror.visibility=View.VISIBLE
                   inputIDerror.setText("사용할 수 없는 아이디 입니다.")
                   inputIDerror.setTextColor(Color.RED)
               }
                */
                else{
                    inputIDerror.setText("사용가능한 아이디 입니다.")
                    inputIDerror.setTextColor(Color.GREEN)
                }
                //특수문자,대문자 x
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 타이핑 되는 텍스트에 변화가 있으면 작동
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {//입력 하기 전 작동
            }
        })

        inputPW.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) { //입력이 끝날 때 작동
                //공백 체크
                if(inputPW.equals("")){
                    inputPWerror.visibility=View.VISIBLE
                    inputPWerror.setText("비밀번호를 입력해 주세요")
                    inputPWerror.setTextColor(Color.RED)
                }
                //비밀번호 문자열 길이 (5~10글자 체크)
                else if(inputPW.length()>10||inputPW.length()<5){
                    inputPWerror.visibility=View.VISIBLE
                    inputPWerror.setText("5~10자의 영문 소문자,숫자만 사용 가능합니다.")
                    inputPWerror.setTextColor(Color.RED)
                }
                //이상 없음
                else{
                    inputPWerror.setText("사용가능한 비밀번호 입니다.")
                    inputPWerror.setTextColor(Color.GREEN)
                }

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {//입력하기 전  작동
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {// 타이핑 되는 텍스트에 변화가 있으면 작동
            }
        })

        inputNAME.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) { //입력이 끝날 때 작동
                //공백 체크
                if(inputNAME.equals("")){
                    inputNAMEerror.visibility=View.VISIBLE
                    inputNAMEerror.setText("이름을 입력해 주세요")
                    inputNAMEerror.setTextColor(Color.RED)
                }
                else{
                    inputNAMEerror.visibility=View.GONE
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {//입력하기 전  작동
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {// 타이핑 되는 텍스트에 변화가 있으면 작동
            }
        })

        inputEMAIL.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) { //입력이 끝날 때 작동
                //공백 체크
                if(inputEMAIL.equals("")){
                    inputEMAILerror.visibility=View.VISIBLE
                    inputEMAILerror.setText("이메일을 입력해 주세요")
                    inputEMAILerror.setTextColor(Color.RED)
                }
                else{
                    inputEMAILerror.visibility=View.GONE
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {//입력하기 전  작동
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {// 타이핑 되는 텍스트에 변화가 있으면 작동
            }
        })

        inputPHONE.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) { //입력이 끝날 때 작동
                //공백 체크
                if(inputPHONE.equals("")){
                    inputPHONEerror.visibility=View.VISIBLE
                    inputPHONEerror.setText("전화번호을 입력해 주세요. ('-' 제외)")
                    inputPHONEerror.setTextColor(Color.RED)
                }
                else{
                    inputPHONEerror.visibility = View.GONE
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {//입력하기 전  작동
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {// 타이핑 되는 텍스트에 변화가 있으면 작동
            }
        })

        signinbtn.setOnClickListener{
            //아이디 입력 형식 올바른지 확인
            //아이디 중복 체크 확인

            //비밀번호 입력 형식 올바른지 확인

            //이름 입력 형식 올바른지 확인

            //이메일 입력 형식 올바른지 확인

            //휴대전화 입력 형식 올바른지 확인

            //모두가 확인 되었으면 가입 완료
            sqlDB = myHelper.writableDatabase
            sqlDB.execSQL("INSERT INTO userinfo1 VALUES('" + inputID.text.toString() + "'," +
                    "'" + inputPW.text.toString() + "'," +
                    "'"+inputNAME.text.toString()+"'," +
                    "'"+inputEMAIL.text.toString()+"'," +
                    "'"+inputPHONE.text.toString()+"');")



            Toast.makeText(applicationContext, "입력됨", Toast.LENGTH_SHORT).show()

            failsignin()//회원가입 실패

            gotoLogin()//회원가입 완료

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