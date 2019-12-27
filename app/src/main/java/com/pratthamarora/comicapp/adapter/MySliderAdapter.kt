package com.pratthamarora.comicapp.adapter

import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder

class MySliderAdapter(private val bannerList:List<String>):SliderAdapter() {
    override fun getItemCount(): Int {
       return bannerList.size
    }

    override fun onBindImageSlide(position: Int, imageSlideViewHolder: ImageSlideViewHolder?) {
        imageSlideViewHolder!!.bindImageSlide(bannerList[position])
    }
}