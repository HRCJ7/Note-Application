package com.rajitha.project.noteapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Note::class], version = 1)
abstract class NoteRoomDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao?

    companion object {
        private var noteRoomInstance: NoteRoomDatabase? = null
        fun getDatabase(context: Context): NoteRoomDatabase? {
            if (noteRoomInstance == null) {
                noteRoomInstance  = Room.databaseBuilder(
                        context.applicationContext,
                        NoteRoomDatabase::class.java, "note database"
                ).build()
            }
            return noteRoomInstance
        }
    }
}