package com.example.dicecup_compulsory.gui

import android.os.Parcelable
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dicecup_compulsory.R
import com.example.dicecup_compulsory.models.BEDiceRoll
import kotlinx.android.synthetic.main.cell.view.*
import java.util.*


class RecycleAdapter(private val rolls: ArrayList<BEDiceRoll>) : RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = rolls[position]

        holder.tvDate.text = currentItem.date.toString()
        val array = currentItem.result
        if (array != null) {
            holder.tvResult.text = array.contentToString()
        }
    }

    override fun getItemCount() = rolls.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.tvDate
        val tvResult: TextView = itemView.tvResult

    }
}