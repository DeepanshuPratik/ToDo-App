package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CompletedTasks : AppCompatActivity(), DoneNotesRVAdapter {
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_tasks)
        findViewById<Button>(R.id.Back).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        findViewById<RecyclerView>(R.id.recyclerView).layoutManager = LinearLayoutManager(this)
        val adapter = DoneNotesAdapter(this, this)
        this.findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
        viewModel.completedlist.observe(this,{list ->
        list?.let {
            adapter.completed = it
            findViewById<Button>(R.id.reset).setOnClickListener {
                viewModel.reset()
            }
        }
        })


    }
    override fun onItemClicked(note: Note) {
        viewModel.deleteNode(note)
        Toast.makeText(this, "${note.text} Deleted!", Toast.LENGTH_LONG).show()
    }
}