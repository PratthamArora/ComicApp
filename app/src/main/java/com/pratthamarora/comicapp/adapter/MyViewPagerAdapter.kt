package com.pratthamarora.comicapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.github.chrisbanes.photoview.PhotoView
import com.pratthamarora.comicapp.R
import com.squareup.picasso.Picasso

class MyViewPagerAdapter(
    context: Context,
    private  var linkList: List<String>
) :PagerAdapter(){

    private var inflater:LayoutInflater = LayoutInflater.from(context)

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun getCount()=linkList.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageLayout=inflater.inflate(R.layout.view_pager_item,container,false)
        val pageImage= imageLayout.findViewById(R.id.page_image) as PhotoView
        Picasso.get().load(linkList[position]).into(pageImage)

        container.addView(imageLayout)
        return imageLayout
    }
}