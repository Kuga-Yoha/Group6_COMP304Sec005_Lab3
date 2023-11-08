package com.example.group6_comp304sec005_lab3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var activities: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lstView = findViewById<View>(R.id.list) as ListView
        lstView.choiceMode = ListView.CHOICE_MODE_NONE
        lstView.isTextFilterEnabled = true

        activities = resources.getStringArray(R.array.activities)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, activities
        )
        lstView.adapter = adapter

        lstView.setOnItemClickListener { _, _, position, _ ->
            val intent = when (position) {
                0 -> Intent(this, Exercise1::class.java)
                1 -> Intent(this, Exercise2::class.java)
                2 -> Intent(this, Exercise3::class.java)
                else -> null
            }
            intent?.let { startActivity(it) }
        }
    }
}
