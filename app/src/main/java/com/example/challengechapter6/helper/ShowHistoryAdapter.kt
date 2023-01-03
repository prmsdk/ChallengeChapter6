package com.example.challengechapter6.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.challengechapter6.R
import com.example.challengechapter6.dao.suit.SuitEntity

class ShowHistoryAdapter(val listPlayer: List<SuitEntity>): RecyclerView.Adapter<ShowPlayerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowPlayerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_history, parent, false)
        return ShowPlayerAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowPlayerAdapter.ViewHolder, position: Int) {
        val tvMode: TextView = holder.itemView.findViewById(R.id.tv_content_mode)
        val tvMsg: TextView = holder.itemView.findViewById(R.id.tv_content_msg)

        tvMode.text = listPlayer[position].mode
        tvMsg.text = listPlayer[position].hasil
    }

    override fun getItemCount(): Int {
        return listPlayer.size
    }
}