package com.example.noteappkotlin.utils

import android.content.Context
import android.text.TextUtils
import com.example.noteappkotlin.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

private val TAG = "storage"

fun persistNote(context: Context, note: Note) {
    if(TextUtils.isEmpty(note.fileName)) {
        note.fileName = UUID.randomUUID().toString() + ".note"
    }

    val fileOutput = context.openFileOutput(note.fileName, Context.MODE_PRIVATE)
    val outputStream = ObjectOutputStream(fileOutput)
    outputStream.writeObject(note)
    outputStream.close()
}

fun loadNote(context: Context, fileName: String): Note {
    val fileInput = context.openFileInput(fileName)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note
    inputStream.close()
    return note
}

fun loadNotes(context: Context): MutableList<Note> {
    val notes = mutableListOf<Note>()
    val notesDir = context.filesDir
    for (fileName in notesDir.list()!!) {
        val note = loadNote(context, fileName)
        notes.add(note)
    }
    return notes
}

fun deleteNote(context: Context, note: Note) {
    context.deleteFile(note.fileName)
}


