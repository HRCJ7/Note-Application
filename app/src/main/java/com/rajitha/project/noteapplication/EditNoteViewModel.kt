package com.rajitha.project.noteapplication

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class EditNoteViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = this.javaClass.simpleName
    private val noteDao: NoteDao
    private val db: NoteRoomDatabase
    fun getNote(noteId: String?): LiveData<Note?>? {
        return noteDao.getNote(noteId)
    }

    init {
        Log.i(TAG, "Edit ViewModel")
        db = NoteRoomDatabase.getDatabase(application)!!
        noteDao = db.noteDao()!!
    }
}