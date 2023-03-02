package com.example.noteappkotlin

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteListActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        findViewById<FloatingActionButton>(R.id.create_note).setOnClickListener(this)

        notes = mutableListOf<Note>()
        notes.add(Note("Note 1", "C'est quoi un recycle view ?"))
        notes.add(Note("Mémo A", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin non fermentum nunc, id vehicula erat. Ut in pharetra lectus. Aliquam consectetur interdum eros, a fermentum justo sagittis et. Sed et erat vitae ex tempor condimentum."))
        notes.add(Note("Mémo B", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin non fermentum nunc, id vehicula erat. Ut in pharetra lectus. Aliquam consectetur interdum eros, a fermentum justo sagittis et. Sed et erat vitae ex tempor condimentum."))
        notes.add(Note("Dernier mémo", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin non fermentum nunc, id vehicula erat. Ut in pharetra lectus. Aliquam consectetur interdum eros, a fermentum justo sagittis et. Sed et erat vitae ex tempor condimentum."))

        adapter = NoteAdapter(notes, this)

        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK || data == null) {
            return
        }

        when(requestCode) {
            NoteDetailActivity.REQUEST_EDIT_NOTE -> processEditNoteResult(data)
        }
    }

    private fun processEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, -1)
        val note = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        data.getParcelableExtra(NoteDetailActivity.EXTRA_NOTE, Note::class.java)!!
                    } else {
                        data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)!!
                    }
        saveNote(note, noteIndex)
    }

    private fun saveNote(note: Note, noteIndex: Int) {
        if(noteIndex < 0) {
            notes.add(0, note)
        } else {
            notes[noteIndex] = note
        }
        adapter.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {
        if(view?.tag != null) {
            showNoteDetail(view.tag as Int)
        } else {
            when(view?.id) {
                R.id.create_note -> createNewNote()
            }
        }
    }

    private fun createNewNote() {
        showNoteDetail(-1)
    }

    private fun showNoteDetail(noteIndex: Int) {
        val note = if(noteIndex < 0) {
            Note()
        } else {
            notes[noteIndex]
        }

        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
        startActivityForResult(intent, NoteDetailActivity.REQUEST_EDIT_NOTE)
    }
}