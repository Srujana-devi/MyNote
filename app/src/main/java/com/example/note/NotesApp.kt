package com.example.note

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.room.Room
import com.example.note.persistence.NotesDao
import com.example.note.persistence.NotesDatabase
//Application Class, which will run as soon as app is created
//and will be used to get instance of context to create our room data base
class NotesApp : Application(){

    private var database : NotesDatabase? = null


    init {
        instance = this
    }

    private fun getDb(): NotesDatabase {
        return if (database != null){
            database!!
        } else {
            database = Room.databaseBuilder(
                instance!!.applicationContext,
                NotesDatabase::class.java, Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration()// remove in prod
                .build()
            database!!
        }
    }


    companion object {
        private var instance: NotesApp? = null

        //for updating, deleting and retrieving notes
        fun getDao(): NotesDao {
            return instance!!.getDb().NotesDao()
        }

        fun getUriPermission(uri: Uri){
            instance!!.applicationContext.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }

    }


}