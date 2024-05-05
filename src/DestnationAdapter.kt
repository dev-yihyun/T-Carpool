package com.example.realtest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class DestnationAdapter(val context: Context, val memberList: ArrayList<Member>) : BaseAdapter()  {

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.destnationitem, null)
        val userimage = view.findViewById<ImageView>(R.id.userimage)
        var userID = view.findViewById<TextView>(R.id.userID)
        var arrivestatus = view.findViewById<Button>(R.id.arrivestatus)
        var moneyaccount = view.findViewById<Button>(R.id.moneyaccount)
        val member = memberList[position]

        userID.text = member.userID

        /*

        //게시글 작성자 == 대표자 이면

        userimage.setBackgroundResource(R.drawable.circle_yellow_button)
        //대표자가 아닌 신청자이면
        userimage.setBackgroundResource(R.drawable.gray_circle)
        */

        arrivestatus.setOnClickListener{
            /*도착*/
            //대표자만 클릭할 수 있도록
        }
        moneyaccount.setOnClickListener { 
            /*요금*/

            //대표자만 클릭할 수 있도록
        }

        return view
    }
    override fun getItem(position: Int): Any {
        return memberList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return memberList.size
    }

}