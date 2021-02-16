package com.rajitha.project.noteapplication

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note?)

    @get:Query("SELECT * FROM notes")
    val allNotes: LiveData<List<Note?>?>?

    @Query("SELECT * FROM notes WHERE id=:noteId")
    fun getNote(noteId: String?): LiveData<Note?>?

    @Update
    fun update(note: Note?)

    @Delete
    fun delete(note: Note?): Int
}