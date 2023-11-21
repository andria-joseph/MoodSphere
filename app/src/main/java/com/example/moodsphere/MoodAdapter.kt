package com.example.moodsphere

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class MoodAdapter(private val moodsList: MutableList<MoodItem>) :
    RecyclerView.Adapter<MoodAdapter.EntryViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mood_entry, parent, false)
        return EntryViewHolder(view)
    }

    inner class EntryViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: MoodItem? = null
        val mEntryDate: TextView = mView.findViewById(R.id.dateEntryTV) as TextView
        val mEntryEmoji: ImageView = mView.findViewById(R.id.facesIV) as ImageView
        val mExtraDetails: TextView = mView.findViewById(R.id.additionalTV) as TextView

        override fun toString(): String {
            return mEntryDate.toString()
        }
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = moodsList[position]
        holder.mItem = entry
        holder.mEntryDate.text = entry.date
        holder.mExtraDetails.text = entry.details

        when(entry.select)
        {
            0 -> holder.mEntryEmoji.setImageResource(R.drawable.depressed_emoji)
            1 -> holder.mEntryEmoji.setImageResource(R.drawable.sad_emoji)
            2 -> holder.mEntryEmoji.setImageResource(R.drawable.angry_emoji)
            3 -> holder.mEntryEmoji.setImageResource(R.drawable.tired_emoji)
            4 -> holder.mEntryEmoji.setImageResource(R.drawable.neutral_emoji)
            5 -> holder.mEntryEmoji.setImageResource(R.drawable.happy_emoji)
            6 -> holder.mEntryEmoji.setImageResource(R.drawable.ecstatic_emoji)
        }
    }

    override fun getItemCount(): Int {
        return moodsList.size
    }

}
