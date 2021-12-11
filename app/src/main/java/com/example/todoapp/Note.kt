package com.example.todoapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
class Note(
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "completed") var completed: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}