package com.example.noteappkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NoteListActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

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

    override fun onClick(view: View?) {
        if(view?.tag != null) {
            Log.i("NoteActivity", "Note de ma list")
            showNoteDetail(view.tag as Int)
        }
    }

    private fun showNoteDetail(noteIndex: Int) {
        val note = notes[noteIndex]
        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
        startActivity(intent)
    }
}