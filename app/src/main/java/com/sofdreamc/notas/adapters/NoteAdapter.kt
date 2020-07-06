package com.sofdreamc.notas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sofdreamc.notas.R
import com.sofdreamc.notas.data.local.models.Note
import kotlinx.android.synthetic.main.item_note_preview.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(), Filterable {
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var fullList: List<Note>

    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note_preview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = differ.currentList[position]
        holder.itemView.apply {
            val drawableImportant = when (note.important) {
                true -> R.drawable.ic_star_yellow
                false -> R.drawable.ic_star_border_black
            }
            val drawableDone = when (note.done) {
                true -> R.drawable.ic_check_box
                false -> R.drawable.ic_check_box_outline
            }
            holder.itemView.image_important.setImageResource(drawableImportant)
            image_is_done.setImageResource(drawableDone)

            txt_title.text = note.title
            txt_content.text = note.content

            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            val date = simpleDateFormat.format(note.created)
            txt_created_at.text = date
            setOnClickListener { onItemClickListener?.let { it(note) } }
        }
    }

    private var onItemClickListener: ((Note) -> Unit)? = null

    fun setOnItemClickListener(listener: (Note) -> Unit) {
        onItemClickListener = listener
    }

    fun submitList(notes: List<Note>) {
        differ.submitList(notes)
        fullList = ArrayList(notes)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<Note>()
                if (constraint.toString().isEmpty())
                    filteredList.addAll(fullList)
                else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                    for (note in fullList) {
                        if (note.title.toLowerCase(Locale.ROOT).contains(filterPattern))
                            filteredList.add(note)
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                differ.submitList(results?.values as MutableList<Note>?)
            }

        }
    }


}