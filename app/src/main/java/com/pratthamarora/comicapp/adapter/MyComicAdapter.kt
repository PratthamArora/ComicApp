package com.pratthamarora.comicapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pratthamarora.comicapp.R
import com.pratthamarora.comicapp.common.Common
import com.pratthamarora.comicapp.model.Comic
import com.pratthamarora.comicapp.ui.ChapterActivity
import com.pratthamarora.comicapp.utils.ItemClickListener
import com.squareup.picasso.Picasso

class MyComicAdapter(var context: Context,
                     private var comicList:List<Comic>):RecyclerView.Adapter<MyComicAdapter.MyViewHolder>() {

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView), View.OnClickListener {

        lateinit var itemClick:ItemClickListener

        fun setClick(itemClick:ItemClickListener){
            this.itemClick=itemClick
        }

        var imageView:ImageView = itemView.findViewById(R.id.comic_image) as ImageView
        var textView:TextView = itemView.findViewById(R.id.comic_name) as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemClick.onClick(v!!,adapterPosition)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val li=LayoutInflater.from(context).inflate(R.layout.comic_item,parent,false)
        return MyViewHolder(li)


    }

    override fun getItemCount()=comicList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(comicList[position].Image).into(holder.imageView)
        holder.textView.text=comicList[position].Name

        holder.setClick(object :ItemClickListener{
            override fun onClick(view: View, position: Int) {
                context.startActivity(Intent(context,ChapterActivity::class.java))
                Common.selectedComic=comicList[position]
            }

        })
    }
}