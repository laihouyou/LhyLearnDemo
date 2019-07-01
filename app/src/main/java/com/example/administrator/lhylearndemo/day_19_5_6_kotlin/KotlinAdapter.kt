package com.example.administrator.lhylearndemo.day_19_5_6_kotlin

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class KotlinAdapter (val items :List<String>,val context:Context) : RecyclerView.Adapter<KotlinAdapter.MyViewHorlde>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHorlde {
        return MyViewHorlde(TextView(parent.context))
    }

    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: MyViewHorlde, position: Int) {
        holder.textView.text=items[position]
        holder.textView.setOnClickListener {

            var str="肯特林"
            var str1="我喜欢+++++   $str,它的长度为${str.length}"
//            print(str1)

//            var array= arrayOf(str,str1,1,"a","b",3.1415,0.6)
//            for (arr in array){
//                print(arr)
//                print("\t")
//            }

            var arrat2= Array(3,{i -> i*2 })
            for (i in 15 downTo 5){
                print(i)
            }
        }
    }

    class MyViewHorlde(val textView: TextView):RecyclerView.ViewHolder(textView)
}