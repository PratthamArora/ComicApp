package com.pratthamarora.comicapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pratthamarora.comicapp.R
import com.pratthamarora.comicapp.common.Common
import com.pratthamarora.comicapp.model.Chapter
import com.pratthamarora.comicapp.ui.ViewComicActivity
import com.pratthamarora.comicapp.utils.ItemClickListener
import java.lang.StringBuilder

class MyChapterAdapter(
    private var context: Context,
    private var chapterList: List<Chapter>):RecyclerView.Adapter<MyChapterAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var chapNum:TextView = itemView.findViewById(R.id.chapter_number) as TextView
        private lateinit var itemClickListener: ItemClickListener
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemClickListener.onClick(v!!,adapterPosition)
        }

        fun setClick(itemClickListener: ItemClickListener){
            this.itemClickListener=itemClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val li= LayoutInflater.from(context).inflate(R.layout.chapter_item,parent,false)
        return MyViewHolder(li)

    }

    override fun getItemCount() = chapterList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.chapNum.text= chapterList[position].Name?.let { StringBuilder(it) }

        holder.setClick(object :ItemClickListener{
            override fun onClick(view: View, position: Int) {
                Common.selectedChapter=chapterList[position]
                Common.chapterIndex=position
                context.startActivity(Intent(context,ViewComicActivity::class.java))

            }

        })
    }
}

