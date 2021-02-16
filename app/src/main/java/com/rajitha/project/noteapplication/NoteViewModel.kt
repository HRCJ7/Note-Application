package com.rajitha.project.noteapplication

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


@Suppress("DEPRECATION")
class NoteViewModel(application: Application?) : AndroidViewModel(application!!) {
    private val TAG = this.javaClass.simpleName
    private val noteDao: NoteDao?
    private val noteDB: NoteRoomDatabase?
    val mAllNotes: LiveData<List<Note?>?>

    fun insert(note: Note?) {
      InsertAsyncTask(noteDao!!).execute(note)
    }

    fun update(note: Note?) {
      UpdateAsyncTask(noteDao!!).execute(note)
    }

    fun delete(note: Note?) {
      DeleteAsyncTask(noteDao!!).execute(note)
    }

    fun getAllNotes(): LiveData<List<Note?>?> {
        return mAllNotes
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "ViewModel Destroyed")
    }

    private open inner class OperationsAsyncTask internal constructor(var mAsyncTaskDao: NoteDao) :
        AsyncTask<Note?, Void?, Void?>() {

        override fun doInBackground(vararg p0: Note?): Void? {
            return null
        }

    }

    private inner class InsertAsyncTask internal constructor(mNoteDao: NoteDao) :
        OperationsAsyncTask(mNoteDao) {
        override fun doInBackground(vararg notes: Note?): Void? {
            mAsyncTaskDao.insert(notes[0])
            return null
        }
    }

    private inner class UpdateAsyncTask internal constructor(noteDao: NoteDao) :
        OperationsAsyncTask(noteDao) {
        override fun doInBackground(vararg notes: Note?): Void? {
            mAsyncTaskDao.update(notes[0])
            return null
        }
    }

    private inner class DeleteAsyncTask(noteDao: NoteDao) :
        OperationsAsyncTask(noteDao) {
        override fun doInBackground(vararg notes: Note?): Void? {
            mAsyncTaskDao.delete(notes[0])
            return null
        }
    }

    init {
        noteDB = application?.let { NoteRoomDatabase.getDatabase(it) }
        noteDao = noteDB!!.noteDao()
        mAllNotes = noteDao!!.allNotes!!
    }
}