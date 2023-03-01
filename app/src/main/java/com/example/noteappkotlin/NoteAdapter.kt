package com.example.noteappkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val notes: List<Note>, private val itemClickListener: View.OnClickListener):
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val cardView: CardView = itemView.findViewById(R.id.card_view)
            val titleView: TextView = cardView.findViewById(R.id.title_text)
            val areaView: TextView = cardView.findViewById(R.id.area_text)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position
        holder.titleView.text = note.title
        holder.areaView.text = note.text
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}