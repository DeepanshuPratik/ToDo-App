package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("Select * from notes_table where completed = 0 order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("Update notes_table Set completed = 1 where id = :noteId")
    fun markAsCompleted(noteId:Int)

    @Query("Update notes_table Set completed = 0 where id = :noteId")
    fun markAsunCompleted( noteId:Int)

    @Query("Select * from notes_table where completed = 1 order by id ASC")
    fun getcompletedlist(): LiveData<List<Note>>

    @Query("Delete from notes_table where completed = 1")
    fun reset()

}