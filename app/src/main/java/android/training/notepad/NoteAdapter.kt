package android.training.notepad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.threeten.bp.format.DateTimeFormatter

class NoteAdapter(val notes : List<Note>, val itemClickListener: View.OnClickListener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val cardView = itemView.findViewById<CardView>(R.id.card_view)
        val titleView = cardView.findViewById<TextView>(R.id.title)
        val excerptView = cardView.findViewById<TextView>(R.id.excerpt)
        val dateView = cardView.findViewById<TextView>(R.id.dateNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)

        return ViewHolder(viewItem)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notes[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position
        holder.titleView.text = note.name
        holder.excerptView.text = note.text

        val date = note.dateNote
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formatted = date!!.format(formatter)

        holder.dateView.text = formatted

    }

    override fun getItemCount(): Int {
        return notes.size
    }
}