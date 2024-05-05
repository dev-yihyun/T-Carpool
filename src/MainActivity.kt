package com.example.realtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var gotologin : ImageButton = findViewById<ImageButton>(R.id.gotoLogin)

        gotologin.setOnClickListener({
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        })
    }


}