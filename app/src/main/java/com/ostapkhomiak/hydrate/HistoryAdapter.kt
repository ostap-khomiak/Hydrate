package com.ostapkhomiak.hydrate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val historyList: List<WaterIntakeEntry>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconView: ImageView = itemView.findViewById(R.id.iconView)
        val amountView: TextView = itemView.findViewById(R.id.amountView)
        val dateView: TextView = itemView.findViewById(R.id.dateView)
        val timeView: TextView = itemView.findViewById(R.id.timeView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val entry = historyList[position]
        holder.iconView.setImageResource(entry.iconId)
        holder.amountView.text = "${entry.amount}ml"
        holder.dateView.text = entry.date
        holder.timeView.text = entry.time
    }

    override fun getItemCount(): Int = historyList.size
}