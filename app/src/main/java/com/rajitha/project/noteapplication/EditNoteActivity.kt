package com.rajitha.project.noteapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class EditNoteActivity : AppCompatActivity() {
    private var etNote: EditText? = null
    private var bundle: Bundle? = null
    private var noteId: String? = null
    private var note: LiveData<Note?>? = null
    var noteModel: EditNoteViewModel? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        etNote = findViewById(R.id.etNote)
        bundle = intent.extras
        if (bundle != null) {
            noteId = bundle!!.getString("note_id")
        }
        noteModel = ViewModelProvider(this).get(EditNoteViewModel::class.java)
        note = noteModel!!.getNote(noteId)
        note!!.observe(this, object : Observer<Note?> {
            override fun onChanged(t: Note?) {
                etNote!!.text = Editable.Factory.getInstance().newEditable(t?.note)
            }
        })
    }

    fun updateNote(view: View?) {
        val updatedNote = etNote!!.text.toString()
        val resultIntent = Intent()
        resultIntent.putExtra(NOTE_ID, noteId)
        resultIntent.putExtra(UPDATED_NOTE, updatedNote)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    fun cancelUpdate(view: View?) {
        finish()
    }

    companion object {
        const val NOTE_ID = "note_id"
        const val UPDATED_NOTE = "note_text"
    }
}