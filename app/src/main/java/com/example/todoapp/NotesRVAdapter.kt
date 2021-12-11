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


class NotesRVAdapter(private val context: Context, private val listener: InNotesRVAdapter) : RecyclerView.Adapter<NotesRVAdapter.NoteViewHolder>(){

    val allNotes = ArrayList<Note>()
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val linearLayout = itemView.findViewById<ConstraintLayout>(R.id.recyclerlayout)
        val textView = itemView.findViewById<TextView>(R.id.text)!!
        val deleteButton = itemView.findViewById<ImageView>(R.id.deleteButton)!!
        //val completed= itemView.findViewById<ImageView>(R.id.completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note,parent,false))
        viewHolder.deleteButton.setOnClickListener{
            listener.onItemClicked(allNotes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.linearLayout.setBackgroundColor(Color.parseColor(currentNote.color))
        holder.textView.text=currentNote.text
//        holder.textView.setBackgroundColor(Color.parseColor(currentNote.color))
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }
    fun updateList(newList: List<Note>) : String{
        allNotes.clear()
        allNotes.addAll(newList)
        val count= itemCount
        val headtext = "$count Tasks Available"
        notifyDataSetChanged()
        return headtext
    }

//    fun updatetick(newList: List<Note>, count: Int) : String{
//        allNotes.clear()
//        allNotes.addAll(newList)
//        val count1= count
//        val headtext = "$count1 Tasks Done"
//        notifyDataSetChanged()
//        return headtext
//    }

}

interface InNotesRVAdapter{
    fun onItemClicked(note: Note){

    }
}

