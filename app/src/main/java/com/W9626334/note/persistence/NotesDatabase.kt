package com.W9626334.note.persistence


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.W9626334.note.model.Note

@Database(entities = [
    Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun NotesDao(): NotesDao

}