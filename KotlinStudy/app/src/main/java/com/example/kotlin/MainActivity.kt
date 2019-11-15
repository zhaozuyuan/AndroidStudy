package com.example.kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val value = "你好"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvName.text = value

        val str = "我不好"
        aVoid(str)
    }

    private fun aVoid(str: String) {
        Log.d("TAG", str)
    }
}
