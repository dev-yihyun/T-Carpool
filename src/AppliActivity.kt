package com.example.realtest

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import android.graphics.Color
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


class AppliActivity : AppCompatActivity() {


    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var sqlDB2: SQLiteDatabase
    lateinit var sqlDB3: SQLiteDatabase

    lateinit var boardset: ImageView

    var memberList = arrayListOf<Member>(
        //  Member(1,"userID1"),
        //Member(1,"userID2")
    )

    fun MemberView() {
        val applimember: ListView = findViewById<ListView>(R.id.applimember)
        val memberadapter = MemberListAdapter(this, memberList)
        applimember.adapter = memberadapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application)
        //게시글 번호 받기
        var inIntent = intent
        var wseq = inIntent.getStringExtra("wseq")

        val intent = Intent(this, IndexActivity::class.java)
        var changewseq: Int = Integer.parseInt(wseq)

        var ID = inIntent.getStringExtra("ID")//게시글번호
        var applicationfinish: Button = findViewById<Button>(R.id.applicationfinish)
        var applicationcancle: Button = findViewById<Button>(R.id.applicationcancle)

        var backaction: ImageButton = findViewById<ImageButton>(R.id.backaction)
        var boardsection: LinearLayout =
            findViewById<LinearLayout>(R.id.boardsection) //단순모집 배경 or 번개모집 배경
        var boardstatus: TextView = findViewById<TextView>(R.id.boardstatus)//단순모집 or 번개모집

        var start: TextView = findViewById<TextView>(R.id.start)//출발지
        var arrive: TextView = findViewById<TextView>(R.id.arrive)//도착지
        var meetTime: TextView = findViewById<TextView>(R.id.meetTime)//만나는 날짜,시간
        var meetLocation: TextView = findViewById<TextView>(R.id.meetLoaction)//만나는 장소
        var charge: TextView = findViewById<TextView>(R.id.charge)//이용 요금
        var people: TextView = findViewById<TextView>(R.id.people)//모집인원
        var writedate: TextView = findViewById<TextView>(R.id.writedate)//작성날짜
        var guswodkdlel : String
        charge.visibility=View.INVISIBLE
        boardset = findViewById<ImageView>(R.id.boardset)//삭제,수정

        myHelper = myDBHelper(this)

        sqlDB = myHelper.readableDatabase
        var cursor: Cursor
        cursor = sqlDB.rawQuery("SELECT * FROM Board1;", null)

        var result:String
        while (cursor.moveToNext()) {

            if (cursor.getInt(0) == changewseq) {

                result = cursor.getInt(7).toString()+"/"+cursor.getInt(16)+"/"+cursor.getInt(17)+"   "+cursor.getInt(6)+"시"+cursor.getInt(15)+"분"

                start.text = cursor.getString(3).toString()
                arrive.text = cursor.getString(4).toString()

                meetTime.text=result

                meetLocation.text = cursor.getString(5).toString()
                //charge.text=cursor.getString()
                people.text=cursor.getInt(8).toString()+"/"+cursor.getInt(9).toString()
                // writedate.text=cursor.getString().toString()

                memberList.add(Member(cursor.getInt(0), cursor.getString(1)))

                if(cursor.getString(1).equals(ID)){

                    boardset.visibility = View.VISIBLE

                    applicationcancle.visibility = View.INVISIBLE
                    applicationfinish.visibility=View.VISIBLE



                }

                else{

                    boardset.visibility = View.INVISIBLE
                    applicationfinish.visibility = View.INVISIBLE
                    applicationcancle.visibility=View.VISIBLE

                }

                if(cursor.getInt(14)==1){

                    boardstatus.visibility=View.INVISIBLE

                }
                break

            }

        }
            sqlDB2 = myHelper.readableDatabase
            var cursor2: Cursor
            cursor2 = sqlDB2.rawQuery("SELECT * FROM request;", null)
            while (cursor2.moveToNext()) {

                if (cursor2.getInt(0) == changewseq) {

                    memberList.add(Member(cursor2.getInt(0), cursor2.getString(2)))


                }

            }

          //  applicationcancle.visibility = View.GONE
          //  applicationfinish.visibility = View.VISIBLE
            boardset = findViewById<ImageView>(R.id.boardset)//삭제,수정

            MemberView()

            /*
        게시글 작성자일경우
        boardset.visibility = View.VISIBLE

        applicationcancle.visibility = View.GONE
        applicationfinish.visibility=View.VISIBLE

        게시글 작성자가 아닐 경우
        boardset.visibility = View.hidden
        applicationcancle.visibility = View.GONE
        applicationfinish.visibility=View.VISIBLE

        */

        applicationcancle.setOnClickListener {
            /* DB 삭제 */
            val alerd = AlertDialog.Builder(this)
            alerd.setTitle("신청취소")
            alerd.setMessage("메인 화면으로 돌아갑니다.")
            alerd.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                startActivity(intent)
            })
            alerd.show()
        }


        applicationfinish.setOnClickListener {
            /* DB 수정 */


            sqlDB3 = myHelper.writableDatabase

            sqlDB3.execSQL("UPDATE Board1 SET bakrka='1' WHERE bwseq='"+changewseq+"';")

            val alerd = AlertDialog.Builder(this)
            alerd.setTitle("모집 마감")
            alerd.setMessage("메인 화면으로 돌아갑니다.")
            alerd.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->

                val intent = Intent(this, IndexActivity::class.java)
                intent.putExtra("ID", ID)

                startActivity(intent)
            })
            alerd.show()
        }

            boardset.setOnClickListener {
                //메뉴
                var popupMenu = PopupMenu(applicationContext, it)
                menuInflater?.inflate(R.menu.option_menu, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.update -> {
                            Toast.makeText(applicationContext, "수정하기", Toast.LENGTH_SHORT).show()
                            /*DB 게시글 수정*/
                            return@setOnMenuItemClickListener true
                        }
                        R.id.delete -> {
                            Toast.makeText(applicationContext, "삭제하기", Toast.LENGTH_SHORT).show()
                            /*DB 게시글 삭제*/
                            return@setOnMenuItemClickListener true
                        }
                        else -> {
                            return@setOnMenuItemClickListener false
                        }
                    }

                }
            }

            backaction.setOnClickListener {
                //이전화면
                val intent = Intent(this, IndexActivity::class.java)
                intent.putExtra("ID", ID)
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

