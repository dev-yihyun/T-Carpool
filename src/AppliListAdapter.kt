package com.example.realtest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class AppliListAdapter(val context: Context, val appliList: ArrayList<Board>) : BaseAdapter()  {
    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.boarditem, null)

        val applist = appliList[position]
        return view
    }
    override fun getItem(position: Int): Any {
        return appliList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return appliList.size
    }


}