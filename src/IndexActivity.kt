package com.example.realtest

import android.app.TabActivity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import android.content.DialogInterface
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

class IndexActivity :  TabActivity() {

    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var sqlDB2: SQLiteDatabase
    lateinit var sqlDB3: SQLiteDatabase
    lateinit var sqlDB4: SQLiteDatabase


    private fun tabspe() {
        var tabHost = this.tabHost
        var tabHome = tabHost.newTabSpec("홈으로").setIndicator("홈으로")
        tabHome.setContent(R.id.tabHome)
        tabHost.addTab(tabHome)

        var tabSpecApplication = tabHost.newTabSpec("신청현황").setIndicator("신청현황")
        tabSpecApplication.setContent(R.id.tabApplication)
        tabHost.addTab(tabSpecApplication)
        
        var tabSpecDestination = tabHost.newTabSpec("나의 목적지").setIndicator("나의 목적지")
        tabSpecDestination.setContent(R.id.tabDestination)
        tabHost.addTab(tabSpecDestination)
        tabHost.currentTab = 0

    }



    private fun boardview() {

        var inIntent = intent
        var ID = inIntent.getStringExtra("ID")//게시글번호

        val mainListView: ListView = findViewById<ListView>(R.id.mainListView)
        val boardAdapter = MainListAdapter(this, boardList)
        mainListView.adapter = boardAdapter
        mainListView.setOnItemClickListener{ parent,view,position,id->
            val intent = Intent(this,BoardActivity::class.java)

            intent.putExtra("ID",ID)
            /*wseq에는 본인 주위의 게시글번호*/
            intent.putExtra("wseq",boardList[position].wseq.toString())
            startActivity(intent)
        }

    }

    private fun applicationlistView(){
        var inIntent = intent
        var ID = inIntent.getStringExtra("ID")//게시글번호
        val applicationListView:ListView = findViewById<ListView>(R.id.applicationListView)
        val applicationAdapter = MainListAdapter(this,applcationList)
        applicationListView.adapter = applicationAdapter
        applicationListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,AppliActivity::class.java)
            /* wseq에는 본인이 신청한 게시글 번호*/
            intent.putExtra("wseq",applcationList[position].wseq.toString())
            intent.putExtra("ID",ID)
            startActivity(intent)
        }

    }

    fun destinationListView(){
        var inIntent = intent
        var ID = inIntent.getStringExtra("ID")//게시글번호
        val DestinationListView:ListView = findViewById<ListView>(R.id.DestinationListView)
        val DestinationAdapter = MainListAdapter(this,destnationList)
        DestinationListView.adapter = DestinationAdapter
        DestinationListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,DestnationActivity::class.java)
            /* wseq에는 본인이 신청한 게시글 번호*/
            intent.putExtra("wseq",destnationList[position].wseq.toString())
            intent.putExtra("ID",ID)
            startActivity(intent)
        }
    }

    var destnationList = arrayListOf<Board>(
        //Board(2,"강서구", "부산진구", "2/3", "1"),
        //Board(2,"금정구", "강서구", "2/3", "1"),
        //Board(3,"하단역", "서면역", "2/3", "2")
    )

    var applcationList = arrayListOf<Board>(
        //Board(2,"강서구", "부산진구", "2/3", "1"),
        //Board(2,"금정구", "강서구", "2/3", "1")
    
    )


    var boardList = arrayListOf<Board>(
     //   Board(1,"서울", "부산", "3/3", "0"),
       )

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)

        var inIntent = intent
        var ID = inIntent.getStringExtra("ID")//게시글번호
        myHelper = myDBHelper(this)

        sqlDB = myHelper.readableDatabase
        var cursor: Cursor
        cursor = sqlDB.rawQuery("SELECT * FROM Board1;", null)

        while(cursor.moveToNext()){

            boardList.add(Board(cursor.getInt(0).toString(),cursor.getString(3),cursor.getString(4),cursor.getInt(8).toString()+"/"+cursor.getInt(9).toString(),cursor.getInt(2  ).toString()))

            if(cursor.getString(1).equals(ID)){

                applcationList.add(Board(cursor.getInt(0).toString(),cursor.getString(3),cursor.getString(4),cursor.getInt(8).toString()+"/"+cursor.getInt(9).toString(),cursor.getInt(2  ).toString()))

                if(cursor.getInt(14)==1){

                    destnationList.add(Board(cursor.getInt(0).toString(),cursor.getString(3),cursor.getString(4),cursor.getInt(8).toString()+"/"+cursor.getInt(9).toString(),cursor.getInt(2  ).toString()))

                }

            }

        }

        sqlDB2 = myHelper.readableDatabase
        var cursor2: Cursor
        cursor2 = sqlDB2.rawQuery("SELECT * FROM request;", null)

        while(cursor2.moveToNext()){

           if(cursor2.getString(2).equals(ID)){

               sqlDB3 = myHelper.readableDatabase
               var cursor3: Cursor
               cursor3 = sqlDB3.rawQuery("SELECT * FROM Board1;", null)

               while(cursor3.moveToNext()){

                   if(cursor3.getInt(0)==cursor2.getInt(0)){

                       applcationList.add(Board(cursor3.getInt(0).toString(),cursor3.getString(3),cursor3.getString(4),cursor3.getInt(8).toString()+"/"+cursor3.getInt(9).toString(),cursor3.getInt(2  ).toString()))

                       if(cursor3.getInt(14)==1){

                           destnationList.add(Board(cursor3.getInt(0).toString(),cursor3.getString(3),cursor3.getString(4),cursor3.getInt(8).toString()+"/"+cursor3.getInt(9).toString(),cursor3.getInt(2  ).toString()))

                       }

                   }



               }

            }

        }

        sqlDB4 = myHelper.readableDatabase
        var cursor4: Cursor
        cursor4 = sqlDB4.rawQuery("SELECT * FROM request;", null)


        //DB에서 내 현재 위치의 게시글들 화면에 나타나도록
            //list뷰 갱신
            //상태 : 모집마감(0),모집중(1),번개(2)
            //boardList.add(출발지,도착지,인원수,상태)
            //리스트 뷰 새로고침
            //boardList.notifyDataSetChanged();


        var gotowrite :Button = findViewById<Button>(R.id.gotowrite)
        gotowrite.setOnClickListener{

            val intent = Intent(this,WriteActivity::class.java)
            intent.putExtra("ID",ID)


            startActivity(intent)
        }

        boardview()
        applicationlistView()
        destinationListView()
        tabspe()
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
