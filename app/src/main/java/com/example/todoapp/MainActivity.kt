package com.example.todoapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity(), InNotesRVAdapter {

    lateinit var viewModel: NoteViewModel
    var done_count : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<FloatingActionButton>(R.id.addButton).setOnClickListener {
            val intent = Intent(this, addactivitylayout::class.java)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.taskcompletedlist).setOnClickListener{
            val intent = Intent(this, CompletedTasks::class.java)
            startActivity(intent)
        }

        var percent: Double = 0.00
        findViewById<RecyclerView>(R.id.recyclerView).layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
       // var i:Int
//        for(i in 0..adapter.itemCount+1){
//            findViewById<TextView>(R.id.text).setBackgroundResource(R.color.blue)
//            findViewById<ImageView>(R.id.deleteButton).setBackgroundResource(R.color.blue)
//        }
        var total_task:Int

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)
        viewModel.completedlist.observe(this, {list -> list?.let {
            done_count = it.size
            this.findViewById<TextView>(R.id.changer).text = "$done_count Tasks Completed"
        }})
        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {

                this.findViewById<TextView>(R.id.headtext).text = adapter.updateList(it)
                total_task = it.size
                val circularProgressBar = findViewById<CircularProgressBar>(R.id.progress_circular)
                circularProgressBar.apply {
                    progressMax = 100f
                    if(done_count>0) {
                        percent = ((done_count).toDouble()/(total_task + done_count).toDouble()*100)
                        val number3digits:Double = Math.round(percent * 1000.0) / 1000.0
                        val number2digits:Double = Math.round(number3digits * 100.0) / 100.0
                        percent = Math.round(number2digits * 10.0) / 10.0

                        setProgressWithAnimation(percent.toFloat(), 1000)
                        progressBarColorStart = Color.WHITE
                        progressBarColor = Color.RED
                        progressBarColorDirection =
                            CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
                        backgroundProgressBarColor = Color.GRAY
                        progressBarWidth = 7f
                        backgroundProgressBarWidth = 3f
                        roundBorder = true
                        startAngle = 180f
                        progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
                    }
                }
                this.findViewById<TextView>(R.id.percent).text="$percent %"
            }
        })


//        findViewById<FloatingActionButton>(R.id.addButton).setOnClickListener {
//
//            if (text.isNotEmpty()) {
//                viewModel.insertNode(Note(text))
//                findViewById<TextView>(R.id.text).setBackgroundResource(R.color.blue)
//                findViewById<ImageView>(R.id.deleteButton).setBackgroundResource(R.color.blue)
//                Toast.makeText(this, "$text inserted!", Toast.LENGTH_LONG).show()
//            }
//        }
        val swipeGesture = object : RVSwipegesture.RVSwipeGestures(this) {

            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val item = adapter.allNotes[position]
                        viewModel.deleteNode(item)
                        Snackbar.make(
                            findViewById(R.id.recyclerView),
                            "${item.text} Task Deleted",
                            Snackbar.LENGTH_SHORT
                        )
                            .setAction("Undo", View.OnClickListener {
                                viewModel.insertNode(item)
                                Toast.makeText(
                                    this@MainActivity, "Reinserted ${item.text} Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }).show()
                    }
                    ItemTouchHelper.RIGHT -> {
//                        done_count += 1
                        val item = adapter.allNotes[position]
//                        viewModel.deleteNode(item)
                        viewModel.completed(item.id)
                        adapter.notifyDataSetChanged()
                        Snackbar.make(
                            findViewById(R.id.recyclerView),
                            "${item.text} Task Completed",
                            Snackbar.LENGTH_SHORT
                        )
                            .setAction("Undo", View.OnClickListener {
                                viewModel.uncompleted(item.id)
//                                done_count -=1
                                Toast.makeText(
                                    this@MainActivity, "Reinserted ${item.text} Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }).show()

                    }
                }
            }
        }

        //attaching swipe gesture to recycler view
        val itemTouchHelper = ItemTouchHelper(swipeGesture)
        itemTouchHelper.attachToRecyclerView(findViewById(R.id.recyclerView))

    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNode(note)
        Toast.makeText(this, "${note.text} Deleted!", Toast.LENGTH_LONG).show()
    }
}