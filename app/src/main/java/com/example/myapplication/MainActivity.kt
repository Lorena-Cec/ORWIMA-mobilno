package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dailyButton = findViewById<ImageButton>(R.id.DailyButton)
        val monthlyButton = findViewById<ImageButton>(R.id.MonthlyButton)
        val travelButton = findViewById<ImageButton>(R.id.TravelButton)

        dailyButton.setOnClickListener {
            val intent = Intent(this, Daily::class.java)
            val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
            val currentDateAndTime: String = simpleDateFormat.format(Date())
            val bundle = Bundle()
            bundle.putString("key", currentDateAndTime)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        monthlyButton.setOnClickListener {
            val intent = Intent(this, Monthly::class.java)
            startActivity(intent)
        }

        travelButton.setOnClickListener {
            val intent = Intent(this, Travels::class.java)
            startActivity(intent)
        }
    }
}