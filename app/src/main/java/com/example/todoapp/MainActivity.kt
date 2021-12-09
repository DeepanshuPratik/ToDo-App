package com.example.todoapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import org.w3c.dom.Text
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), InNotesRVAdapter {

    lateinit var viewModel: NoteViewModel
    var total_fixed_task = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<FloatingActionButton>(R.id.addButton).setOnClickListener {
            val intent = Intent(this, addactivitylayout::class.java)
            intent.putExtra("total_fixed",total_fixed_task)
            startActivity(intent)
            finish()
        }

        var percent = 0f
        findViewById<RecyclerView>(R.id.recyclerView).layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
       // var i:Int
//        for(i in 0..adapter.itemCount+1){
//            findViewById<TextView>(R.id.text).setBackgroundResource(R.color.blue)
//            findViewById<ImageView>(R.id.deleteButton).setBackgroundResource(R.color.blue)
//        }
        var total_task:Int
        var done_count = 0
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)
        total_fixed_task= intent.getIntExtra("num",0)
        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                this.findViewById<TextView>(R.id.headtext).text = adapter.updateList(it)
                this.findViewById<TextView>(R.id.changer).text = adapter.updatetick( it, done_count)
                total_task = adapter.update_total_count()
                val circularProgressBar = findViewById<CircularProgressBar>(R.id.progress_circular)
                circularProgressBar.apply {
                    progressMax = 100f
                    if(total_fixed_task>0) {
                        percent = ((done_count / total_fixed_task)*100).toFloat()
                        setProgressWithAnimation(percent, 1000)
                        //progressBarColorStart = Color.WHITE
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
                        done_count += 1
                        val item = adapter.allNotes[position]
                        viewModel.deleteNode(item)
                        Snackbar.make(
                            findViewById(R.id.recyclerView),
                            "${item.text} Task Completed",
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