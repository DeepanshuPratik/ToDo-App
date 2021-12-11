package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider

class addactivitylayout : AppCompatActivity() {
    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addactivitylayout)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)
        var color : String="#AA41F16D"
        findViewById<ImageView>(R.id.green).setOnClickListener{
            color="#AA41F16D"
            findViewById<ImageView>(R.id.green).setImageResource(R.drawable.ic_check_circle_)
            findViewById<ImageView>(R.id.orange).setImageResource(R.drawable.ic_orange)
            findViewById<ImageView>(R.id.blue).setImageResource(R.drawable.ic_blue)
            findViewById<ImageView>(R.id.red).setImageResource(R.drawable.ic_baseline_circle_24)
            findViewById<ImageView>(R.id.yellow).setImageResource(R.drawable.ic_circle_yellow)
        }
        findViewById<ImageView>(R.id.orange).setOnClickListener{
            color="#AAE75A18"
            findViewById<ImageView>(R.id.green).setImageResource(R.drawable.ic_greem)
            findViewById<ImageView>(R.id.orange).setImageResource(R.drawable.ic_check_circle_orange)
            findViewById<ImageView>(R.id.blue).setImageResource(R.drawable.ic_blue)
            findViewById<ImageView>(R.id.red).setImageResource(R.drawable.ic_baseline_circle_24)
            findViewById<ImageView>(R.id.yellow).setImageResource(R.drawable.ic_circle_yellow)
        }
        findViewById<ImageView>(R.id.blue).setOnClickListener{
            color="#AAA4E0E8"
            findViewById<ImageView>(R.id.green).setImageResource(R.drawable.ic_greem)
            findViewById<ImageView>(R.id.orange).setImageResource(R.drawable.ic_orange)
            findViewById<ImageView>(R.id.blue).setImageResource(R.drawable.ic_baseline_check_circle_blue)
            findViewById<ImageView>(R.id.red).setImageResource(R.drawable.ic_baseline_circle_24)
            findViewById<ImageView>(R.id.yellow).setImageResource(R.drawable.ic_circle_yellow)
        }
        findViewById<ImageView>(R.id.red).setOnClickListener{
            color="#AAE32636"
            findViewById<ImageView>(R.id.red).setImageResource(R.drawable.ic_check_circlered)
            findViewById<ImageView>(R.id.green).setImageResource(R.drawable.ic_greem)
            findViewById<ImageView>(R.id.orange).setImageResource(R.drawable.ic_orange)
            findViewById<ImageView>(R.id.blue).setImageResource(R.drawable.ic_blue)
            findViewById<ImageView>(R.id.yellow).setImageResource(R.drawable.ic_circle_yellow)
        }
        findViewById<ImageView>(R.id.yellow).setOnClickListener{
            color="#AAFFBF00"
            findViewById<ImageView>(R.id.green).setImageResource(R.drawable.ic_greem)
            findViewById<ImageView>(R.id.orange).setImageResource(R.drawable.ic_orange)
            findViewById<ImageView>(R.id.blue).setImageResource(R.drawable.ic_blue)
            findViewById<ImageView>(R.id.red).setImageResource(R.drawable.ic_baseline_circle_24)
            findViewById<ImageView>(R.id.yellow).setImageResource(R.drawable.ic_check_circleyellow)
        }
        findViewById<Button>(R.id.submitb).setOnClickListener {
            val text = findViewById<EditText>(R.id.input).text.toString()
            if (text.isNotEmpty()) {
                viewModel.insertNode(Note(text,color,0))
                Toast.makeText(this, "$text inserted!", Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }
}