package com.pratthamarora.comicapp.common

import com.pratthamarora.comicapp.model.Chapter
import com.pratthamarora.comicapp.model.Comic
import java.lang.StringBuilder

object Common {
    fun formatString(name: String): String {
        val finalResult = StringBuilder(
            if (name.length>15)
                name.substring(0,15)+"..."
            else
                name
        )
        return finalResult.toString()

    }

    var chapterIndex: Int = -1
    lateinit var selectedChapter: Chapter
    lateinit var chapterList: List<Chapter>
    var selectedComic: Comic? =null
    var comicList: List<Comic> =  ArrayList()
    var categories = arrayOf("Action", "Adult", "Adventure", "Comedy", "Completed", "Cooking", "Doujinshi", "Drama", "Drop", "Ecchi", "Fantasy", "Gender bender", "Harem", "Historical", "Horror", "Jose", "Latest", "Manhua", "Manhwa", "Material arts", "Mature", "Mecha", "Medical", "Mystery", "Newest", "One shot", "Ongoing", "Psychological", "Romance", "School life", "Sci fi", "Seinen", "Shoujo", "Shoujo a", "Shounen", "Shounen ai", "Slice of life", "Smut", "Sports", "Superhero", "Supernatural", "Top Read", "Tragedy", "Webtoons", "Yaoi", "Yuri")
}