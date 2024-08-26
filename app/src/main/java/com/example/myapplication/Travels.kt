package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class Travels : AppCompatActivity() {

    private var listItems = ArrayList<String>()
    private var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travels)

        val backButton = findViewById<Button>(R.id.backB)
        val dateButton = findViewById<Button>(R.id.dateB)
        val addButton = findViewById<Button>(R.id.addButton)
        val listView = findViewById<ListView>(R.id. listView)
        val addText = findViewById<EditText>(R.id. addText)
        val database = Firebase.firestore

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        dateButton.setOnClickListener {
            val intent = Intent(this, Monthly::class.java)
            startActivity(intent)
        }

        database.collection("Travel").get().addOnSuccessListener {
                result -> for (document in result) {
            listItems.add(document.data.values.toString())
            listView.adapter = adapter
            adapter!!.notifyDataSetChanged()
            }
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        addButton.setOnClickListener {
            if(!addText.text.isNullOrEmpty()) {
                listItems.add(addText.text.toString())
                listView.adapter = adapter
                adapter!!.notifyDataSetChanged()

                val trips: String = addText.text.toString()
                val travels = hashMapOf(
                    "travel" to trips,
                )
                val db = Firebase.firestore

                db.collection("Travel").add(travels).addOnSuccessListener {
                        documentReference -> Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                    .addOnFailureListener {
                            e -> Log.w(ContentValues.TAG, "Error adding document", e)
                    }

                db.collection("Travel")
                    .get().addOnSuccessListener {
                            result -> for (document in result) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    } }
                    .addOnFailureListener { exception ->
                        Log.w(ContentValues.TAG, "Error getting documents.", exception)
                    }
            }
        }
    }
}