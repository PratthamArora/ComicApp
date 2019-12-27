package com.pratthamarora.comicapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pratthamarora.comicapp.R
import com.pratthamarora.comicapp.adapter.MyViewPagerAdapter
import com.pratthamarora.comicapp.common.Common
import com.pratthamarora.comicapp.model.Chapter
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer
import kotlinx.android.synthetic.main.activity_view_comic.*

class ViewComicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_comic)

        back.setOnClickListener{
            if (Common.chapterIndex==0){
                Toast.makeText(this,"You Are Reading First Chapter",Toast.LENGTH_SHORT).show()
            }
            else{
                Common.chapterIndex--
                fetchLinks(Common.chapterList[Common.chapterIndex])
            }
        }
        next.setOnClickListener {
            if (Common.chapterIndex==Common.chapterList.size-1){
                Toast.makeText(this,"You Are Reading Last Chapter",Toast.LENGTH_SHORT).show()
            }
            else{
                Common.chapterIndex++
                fetchLinks(Common.chapterList[Common.chapterIndex])
            }
        }
        fetchLinks(Common.selectedChapter)
    }

    private fun fetchLinks(chapter: Chapter) {
        if (chapter.Links!=null){
            if (chapter.Links!!.isNotEmpty()){
                val adapter =MyViewPagerAdapter(baseContext,chapter.Links!!)
                view_pager.adapter=adapter
                chap_name_view.text=Common.formatString(Common.selectedChapter.Name!!)

                //book flip animation
                val bookFlipAnim=BookFlipPageTransformer()
                bookFlipAnim.scaleAmountPercent=10f
                view_pager.setPageTransformer(true,bookFlipAnim)

            }
            else{
                Toast.makeText(this,"No Image Here",Toast.LENGTH_SHORT).show()

            }

        }
        else{
            Toast.makeText(this,"This is the latest chapter",Toast.LENGTH_SHORT).show()

        }

    }
}
