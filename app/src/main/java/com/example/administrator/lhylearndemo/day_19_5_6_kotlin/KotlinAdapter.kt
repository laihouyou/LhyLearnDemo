package com.example.administrator.lhylearndemo.day_19_5_6_kotlin

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KotlinAdapter (val items :List<String>) : RecyclerView.Adapter<KotlinAdapter.MyViewHorlde>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHorlde {
        return MyViewHorlde(TextView(parent.context))
    }

    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: MyViewHorlde, position: Int) {
        holder.textView.text=items[position]
    }

    class MyViewHorlde(val textView: TextView):RecyclerView.ViewHolder(textView)
}