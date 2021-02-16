package com.rajitha.project.noteapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class MainActivity : AppCompatActivity(), NoteListAdapter.OnDeleteClickListener {
    private var noteViewModel: NoteViewModel? = null
    private var noteListAdapter: NoteListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        noteListAdapter = NoteListAdapter(this, this)
        recyclerView.adapter = noteListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(this@MainActivity, NewNoteActivity::class.java)
                startActivityForResult(
                        intent,
                        NEW_NOTE_ACTIVITY_REQUEST_CODE
                )
            }
        })
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel!!.getAllNotes()!!.observe(
                this,
                androidx.lifecycle.Observer<List<Note?>?> { noteListAdapter!!.setNotes(it as List<Note>?) })
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            // Code to insert note
            val note_id: String = UUID.randomUUID().toString()
            val note = data!!.getStringExtra(NewNoteActivity.NOTE_ADDED)?.let { Note(note_id, it) }
            noteViewModel?.insert(note)
            Toast.makeText(
                    applicationContext,
                    R.string.saved,
                    Toast.LENGTH_LONG
            ).show()
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            // Code to update the note
            val note = data!!.getStringExtra(EditNoteActivity.NOTE_ID)?.let {
                data.getStringExtra(EditNoteActivity.UPDATED_NOTE)?.let { it1: String ->
                    Note(
                            it,
                            it1
                    )
                }
            }
            noteViewModel?.update(note)
            Toast.makeText(
                    applicationContext,
                    R.string.updated,
                    Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.not_saved,
                    Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        private const val NEW_NOTE_ACTIVITY_REQUEST_CODE = 1
        const val UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2
    }

    override fun OnDeleteClickListener(myNote: Note) {
        noteViewModel?.delete(myNote)
    }

}