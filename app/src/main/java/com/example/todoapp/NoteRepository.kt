package com.example.todoapp

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {
    val allnotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }
    suspend fun delete(note : Note){
        noteDao.delete(note)
    }
    suspend fun markAsCompleted(noteId:Int) = noteDao.markAsCompleted(noteId)
    suspend fun markAsunCompleted(noteId:Int) = noteDao.markAsunCompleted(noteId)
    suspend fun reset() = noteDao.reset()
    var completedlist : LiveData<List<Note>> = noteDao.getcompletedlist()

}