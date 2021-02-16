package com.rajitha.project.noteapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity


class NewNoteActivity : AppCompatActivity() {
    private var etNewNote: EditText? = null
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        etNewNote = findViewById(R.id.etNewNote)
        val button: Button = findViewById(R.id.bAdd)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val resultIntent = Intent()
                if (TextUtils.isEmpty(etNewNote!!.text)) {
                    setResult(Activity.RESULT_CANCELED, resultIntent)
                } else {
                    val note = etNewNote!!.getText().toString()
                    resultIntent.putExtra(NOTE_ADDED, note)
                    setResult(Activity.RESULT_OK, resultIntent)
                }
                finish()
            }
        })
    }

    companion object {
        const val NOTE_ADDED = "new_note"
    }
}