package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import java.util.*
import com.google.firebase.ktx.Firebase

class Daily : AppCompatActivity() {
    private var listItems = ArrayList<String>()
    private var adapter: ArrayAdapter<String>? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily)

        val backButton = findViewById<Button>(R.id.backButton)
        val addButton = findViewById<Button>(R.id.addButton)
        val listView = findViewById<ListView>(R.id.listView)
        val addText = findViewById<EditText>(R.id.addText)
        val dateDisplay = findViewById<TextView>(R.id.dateDisplay)
        val dateButton = findViewById<Button>(R.id.dateButton)
        val bundle = intent.extras
        val datePicked: String? = bundle!!.getString("key", "Default")
        dateDisplay.text = datePicked
        val database = Firebase.firestore
        val date: String = dateDisplay.text.toString()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        dateButton.setOnClickListener {
            val intent = Intent(this, Monthly::class.java)
            startActivity(intent)
        }

        database.collection(date).get().addOnSuccessListener {
            result -> for (document in result) {
                listItems.add(document.data.values.toString())
                listView.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }
        }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        addButton.setOnClickListener {
            if(!addText.text.isNullOrEmpty()) {
                listItems.add(addText.text.toString())
                listView.adapter = adapter
                adapter!!.notifyDataSetChanged()

                val tasks: String = addText.text.toString()
                val task = hashMapOf(
                    "task" to tasks,
                )

                database.collection(date).add(task).addOnSuccessListener {
                        documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                    .addOnFailureListener {
                            e -> Log.w(TAG, "Error adding document", e)
                    }

                database.collection(date)
                    .get().addOnSuccessListener {
                            result -> for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                    }
                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error getting documents.", exception)
                    }
            }
        }
    }
}
