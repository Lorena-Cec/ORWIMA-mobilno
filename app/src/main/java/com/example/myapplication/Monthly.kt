package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import java.util.*

class Monthly : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val backButton = findViewById<Button>(R.id.backbutton)
        val goToDaily = findViewById<Button>(R.id.gotodaily)
        val calendar = Calendar.getInstance()
        val selectedDate = findViewById<TextView>(R.id. selectedDate)
        val travelButton = findViewById<Button>(R.id.travelbutton)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year,month,dayOfMonth)

            val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMANY)
            val formattedDate = dateFormatter.format(calendar.time)

            selectedDate.text = formattedDate
            goToDaily.setOnClickListener {
                val intent = Intent(this, Daily::class.java)

                val bundle = Bundle()
                bundle.putString("key", formattedDate)
                intent.putExtras(bundle)

                startActivity(intent)
            }
        }
        travelButton.setOnClickListener {
            val intent = Intent(this, Travels::class.java)
            startActivity(intent)
        }


    }
}