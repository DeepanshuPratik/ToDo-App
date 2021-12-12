package com.example.todoapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class DoneNotesAdapter (private val context: Context, private val listener: DoneNotesRVAdapter) : RecyclerView.Adapter<DoneNotesAdapter.NoteViewHolder>(){
    var completed = listOf<Note>()
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val linearLayout = itemView.findViewById<ConstraintLayout>(R.id.recyclerlayout)
        val textView = itemView.findViewById<TextView>(R.id.text)!!
        val deleteButton = itemView.findViewById<ImageView>(R.id.deleteButton)!!
        //val completed= itemView.findViewById<ImageView>(R.id.completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note,parent,false))
        viewHolder.deleteButton.setOnClickListener{
            listener.onItemClicked(completed[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = completed[position]
        holder.linearLayout.setBackgroundColor(Color.parseColor(currentNote.color))
        holder.textView.text=currentNote.text
        holder.deleteButton.setImageResource(R.drawable.ic_check_circle_)
//        holder.textView.setBackgroundColor(Color.parseColor(currentNote.color))
    }

    override fun getItemCount(): Int {
        return completed.size
    }

}

interface DoneNotesRVAdapter{
    fun onItemClicked(note: Note){

    }
}
