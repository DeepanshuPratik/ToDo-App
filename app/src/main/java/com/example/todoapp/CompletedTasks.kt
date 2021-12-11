package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CompletedTasks : AppCompatActivity(), DoneNotesRVAdapter {
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_tasks)
        findViewById<Button>(R.id.Back).setOnClickListener{
            finish()
        }
        findViewById<RecyclerView>(R.id.recyclerView).layoutManager = LinearLayoutManager(this)
        val adapter = DoneNotesAdapter(this, this)
        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        viewModel.completedcount.observe(this, Observer { list ->
            list?.let {

            }

        })
    }
}