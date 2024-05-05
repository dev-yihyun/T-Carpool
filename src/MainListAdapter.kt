package com.example.realtest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView

class MainListAdapter(val context: Context, val boardList: ArrayList<Board>) : BaseAdapter()  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.boarditem, null)
        val status = view.findViewById<TextView>(R.id.boardstatus)
        val itemarea = view.findViewById<LinearLayout>(R.id.itemarea)
        val startlocation = view.findViewById<TextView>(R.id.start)
        val arrivelocation = view.findViewById<TextView>(R.id.arrive)
        val peoplecnt = view.findViewById<TextView>(R.id.people)
        val board = boardList[position]


        startlocation.text = board.start
        arrivelocation.text = board.arrive
        peoplecnt.text = board.people

        if(board.status == "0"){
            itemarea.setBackgroundResource(R.drawable.end)
            status.setBackgroundResource(R.drawable.endstatus)
            status.text ="마감"
        }
        else if(board.status =="1"){
            itemarea.setBackgroundResource(R.drawable.ing)
            status.setBackgroundResource(R.drawable.ingstatus)
            status.text ="모집중"
        }
        else if (board.status == "2" ){
            itemarea.setBackgroundResource(R.drawable.light)
            status.setBackgroundResource(R.drawable.lightstatus)
            status.text = "모집중"
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return boardList.size
    }
}