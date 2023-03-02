package com.example.noteappkotlin.utils

import android.content.Context
import android.text.TextUtils
import com.example.noteappkotlin.Note
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