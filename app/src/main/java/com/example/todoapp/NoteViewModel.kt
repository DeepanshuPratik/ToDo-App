package com.example.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    val allNotes: LiveData<List<Note>>
    var completedcount : LiveData<List<Note>>
    private val repository : NoteRepository
    init{
        val dao= NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allnotes
        completedcount = repository.completedsize
    }
    fun deleteNode(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }
    fun insertNode(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
    fun completed(id : Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.markAsCompleted(id)
    }
    fun uncompleted(id : Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.markAsunCompleted(id)
    }
}