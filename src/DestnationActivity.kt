package com.example.realtest

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.app.TabActivity
import android.widget.*
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DestnationActivity : AppCompatActivity() {

    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var sqlDB2: SQLiteDatabase

    var memberList = arrayListOf<Member>(
        //Member(1,"userID1"),
        //Member(1,"userID2"),
        //Member(1,"userID3")
    )

    fun MemberView(){
        val applimember:ListView = findViewById<ListView>(R.id.applimember)
        val memberAdapter = MemberListAdapter(this,memberList)
        applimember.adapter = memberAdapter
    }
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destnation)
        //게시글 번호 받기
        var inIntent = intent
        var wseq = inIntent.getStringExtra("wseq")
        var changewseq: Int = Integer.parseInt(wseq)

        var boardstatus: TextView = findViewById<TextView>(R.id.boardstatus)
        var boardset: ImageView = findViewById<ImageView>(R.id.boardset)
        var start: TextView = findViewById<TextView>(R.id.start)//출발지
        var arrive: TextView = findViewById<TextView>(R.id.arrive)//도착지
        var meetTime: TextView = findViewById<TextView>(R.id.meetTime)//만나는 날짜,시간
        var meetLocation: TextView = findViewById<TextView>(R.id.meetLoaction)//만나는 장소
        var charge: TextView = findViewById<TextView>(R.id.charge)//이용 요금
        var people: TextView = findViewById<TextView>(R.id.people)//모집인원
        var writedate: TextView = findViewById<TextView>(R.id.writedate)//작성날짜
        charge.visibility=View.INVISIBLE
        boardstatus.visibility = View.INVISIBLE
        boardset.visibility = View.INVISIBLE

        myHelper = myDBHelper(this)

        sqlDB2 = myHelper.readableDatabase
        var cursor2: Cursor
        cursor2 = sqlDB2.rawQuery("SELECT * FROM Board1;", null)

        var result:String

        while(cursor2.moveToNext()){

            if(cursor2.getInt(0)==changewseq) {

                result = cursor2.getInt(7).toString()+"/"+cursor2.getInt(16)+"/"+cursor2.getInt(17)+"   "+cursor2.getInt(6)+"시"+cursor2.getInt(15)+"분"

                start.text = cursor2.getString(3).toString()
                arrive.text = cursor2.getString(4).toString()

                meetTime.text=result

                meetLocation.text = cursor2.getString(5).toString()
                //charge.text=cursor.getString()
                people.text=cursor2.getInt(8).toString()+"/"+cursor2.getInt(9).toString()
                // writedate.text=cursor.getString().toString()


                memberList.add(Member(cursor2.getInt(0), cursor2.getString(1)))

            }

        }

        sqlDB = myHelper.readableDatabase
        var cursor: Cursor
        cursor = sqlDB.rawQuery("SELECT * FROM request;", null)

        while(cursor.moveToNext()){

            if(cursor.getInt(0)==changewseq){

            memberList.add(Member(changewseq,cursor.getString(2)))

            }

        }


        MemberView()
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