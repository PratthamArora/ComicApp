package com.pratthamarora.comicapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratthamarora.comicapp.R
import com.pratthamarora.comicapp.adapter.MyChapterAdapter
import com.pratthamarora.comicapp.common.Common
import com.pratthamarora.comicapp.model.Comic
import kotlinx.android.synthetic.main.activity_chapter.*
import java.lang.StringBuilder

class ChapterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)

        toolbar.title= Common.selectedComic!!.Name
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp)
        toolbar.setNavigationOnClickListener {
            finish()//go back
        }
        recycler_chapter.setHasFixedSize(true)
        val layoutManager= LinearLayoutManager(this)
        recycler_chapter.layoutManager=layoutManager
        recycler_chapter.addItemDecoration(DividerItemDecoration(this,layoutManager.orientation))

        fetchChapter(Common.selectedComic!!)
    }

    private fun fetchChapter(comic: Comic) {
        Common.chapterList=comic.Chapters!!
        chapter_number.text=StringBuilder("CHAPTERS (")
            .append(comic.Chapters!!.size)
            .append(")")
        recycler_chapter.adapter=MyChapterAdapter(this,Common.chapterList)
    }
}
