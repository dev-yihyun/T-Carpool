package com.example.realtest

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.widget.*
import kotlinx.android.synthetic.main.boardlist.view.*
import java.lang.NullPointerException
import android.os.SystemClock
import android.widget.*
import java.time.Month
import java.time.Year
import android.widget.*
import androidx.annotation.RequiresApi




import org.w3c.dom.Text
import java.text.SimpleDateFormat

class WriteActivity : AppCompatActivity() {

    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var sqlDB2: SQLiteDatabase
    lateinit var sqlDB3: SQLiteDatabase
    lateinit var sqlDB4: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        var inIntent = intent
        var ID = inIntent.getStringExtra("ID")//게시글번호

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        var backaction : ImageButton = findViewById<ImageButton>(R.id.backaction)

        var inputStart : EditText = findViewById<EditText>(R.id.inputStart)
        var inputarrive : EditText = findViewById<EditText>(R.id.inputarrive)
        var inputplace : EditText = findViewById<EditText>(R.id.inputplace)
        var inputTime : TimePicker = findViewById<TimePicker>(R.id.inputTime)
        var inputDate : DatePicker = findViewById<DatePicker>(R.id.inputDate)

        var inputPeople : NumberPicker = findViewById<NumberPicker>(R.id.inputPeople)

        var inputbankname : EditText = findViewById<EditText>(R.id. inputbankname)
        var inputaccount : EditText = findViewById<EditText>(R.id. inputaccount)
        var inputcontentt : EditText =findViewById<EditText>(R.id.inputcontentt)

        var writeFinish : Button = findViewById<Button>(R.id.writeFinish)
        var writeCancle : Button = findViewById<Button>(R.id.writeCancle)
        val intent = Intent(this,IndexActivity::class.java)


        var selectYear=0
        var selectMonth=0
        var selectDay =0

        var selecthour =0
        var selectminute =0

        var selectpeople=0
        var changed:String

        inputDate.setOnDateChangedListener{view,year,monthOfYear,dayOfMonth->
            selectYear =inputDate.year
            selectMonth=inputDate.month
            selectDay=inputDate.dayOfMonth
        }


        inputTime.setOnTimeChangedListener{view, hour, min ->
            selecthour = hour
            selectminute = min
        }

        inputPeople.setOnValueChangedListener{picker: NumberPicker?, oldVal: Int, newVal: Int ->

            selectpeople=newVal

        }

        var aaa:String
        aaa=Integer.toString(selectYear)
        changed = Integer.toString(selectYear) +"/"+Integer.toString(selectMonth) +"/" + Integer.toString(selectDay)



       var changeP = inputPeople.value.toString()

        myHelper = myDBHelper(this)
/*
        sqlDB3 = myHelper.readableDatabase
        var cursor3: Cursor
        cursor3 = sqlDB.rawQuery("SELECT datetime('now','localtime');", null)
*/
        sqlDB = myHelper.readableDatabase
        var cursor: Cursor
        cursor = sqlDB.rawQuery("SELECT ifnull(MAX(bwseq),0) FROM Board1;", null)
        cursor.moveToFirst();
        inputPeople.minValue=1
        inputPeople.maxValue=3

        var boardstatus : RadioGroup = findViewById<RadioGroup>(R.id.boardstatus)
        var status = 0
        boardstatus.setOnCheckedChangeListener{ group,checkedId->
            when(checkedId){
                R.id.statusing -> status = 1//모집 일때
                R.id.statuslight-> status=2//번개일때
            }
        }

        writeFinish.setOnClickListener{
            sqlDB4 = myHelper.readableDatabase
            var cursor4: Cursor
            cursor4 = sqlDB4.rawQuery("SELECT datetime('now','localtime');", null)
            cursor4.moveToFirst()

            val currentTime : Long = System.currentTimeMillis()
            val dataFormat5 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

            sqlDB2 = myHelper.writableDatabase
             sqlDB2.execSQL("INSERT INTO Board1 VALUES('" + (cursor.getInt(0)+1) + "','"+ID +"','"+status+"','"+inputStart.text.toString()+"','"+inputarrive.text.toString()+"','"+inputplace.text.toString()+"','"+selecthour+"','"+selectYear+"','"+0+"','"+selectpeople+"','"+inputbankname.text.toString()+"','"+inputaccount.text.toString()+"','"+inputcontentt.text.toString()+"','"+dataFormat5.format(currentTime).toString()+"','"+0+"','"+selectminute+"','"+(selectMonth+1)+"','"+selectDay+"');")
            sqlDB2.close()
            Toast.makeText(applicationContext, "입력됨", Toast.LENGTH_SHORT).show()

            val intent = Intent(this,IndexActivity::class.java)
            intent.putExtra("ID",ID)
            val alerd = AlertDialog.Builder(this)
            alerd.setTitle("작성 완료")
            alerd.setMessage("메인 화면으로 돌아갑니다.")
            alerd.setPositiveButton("확인", DialogInterface.OnClickListener{ dialog, which->
                startActivity(intent)
            })
            alerd.show()
        }
        writeCancle.setOnClickListener{
            startActivity(intent)
        }
        backaction.setOnClickListener{
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