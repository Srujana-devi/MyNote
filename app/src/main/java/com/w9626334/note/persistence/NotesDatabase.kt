package com.w9626334.note.persistence


import androidx.room.Database
import androidx.room.RoomDatabase
import com.w9626334.note.model.Note

@Database(entities = [
    Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun NotesDao(): NotesDao

}