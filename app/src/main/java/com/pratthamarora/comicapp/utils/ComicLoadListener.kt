package com.pratthamarora.comicapp.utils

import com.pratthamarora.comicapp.model.Comic

interface ComicLoadListener {
    fun onComicLoadDone(comicList:List<Comic>)
}