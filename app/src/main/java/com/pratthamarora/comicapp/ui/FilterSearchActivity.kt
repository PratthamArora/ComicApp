package com.pratthamarora.comicapp.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.pratthamarora.comicapp.R
import com.pratthamarora.comicapp.adapter.MyComicAdapter
import com.pratthamarora.comicapp.common.Common
import com.pratthamarora.comicapp.model.Comic
import kotlinx.android.synthetic.main.activity_filter_search.*

class FilterSearchActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_search)

        bottom_appbar.inflateMenu(R.menu.main_menu)
        bottom_appbar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_filter->showOptionsDialog()
                R.id.action_search->showSearchDialog()
            }
            true
        }
        recycler_fiter_search.setHasFixedSize(true)
        recycler_fiter_search.layoutManager=GridLayoutManager(this,2)


    }

    private fun showSearchDialog() {

        val alertDialog=AlertDialog.Builder(this)
        alertDialog.setTitle("Search")
        val inflater= this.layoutInflater
        val searchLayout=inflater.inflate(R.layout.dialog_search,null)

        val editSearch=searchLayout.findViewById<View>(R.id.edit_search) as EditText
        alertDialog.apply {
            setView(searchLayout)
            setNegativeButton("CANCEL") { dialog: DialogInterface?, _: Int ->
                dialog!!.dismiss()
            }
            setPositiveButton("SEARCH") { _: DialogInterface?, _: Int ->
                fetchSearchComic(editSearch.text.toString())
            }

            show()
        }
    }

    private fun fetchSearchComic(search: String) {
        val comicSearched = ArrayList<Comic>()
        for (comic in Common.comicList){
            if (comic.Name!=null)
                if (comic.Name!!.contains(search))
                    comicSearched.add(comic)
        }
        if (comicSearched.size>0){
            recycler_fiter_search.adapter=MyComicAdapter(this,comicSearched)
        }
        else{
            Toast.makeText(this,"No Result",Toast.LENGTH_SHORT).show()
        }
    }


    private fun showOptionsDialog() {
        val alertDialog=AlertDialog.Builder(this)
        alertDialog.setTitle("Select Category")
        val inflater= this.layoutInflater
        val filterLayout=inflater.inflate(R.layout.dialog_filter,null)

        val chipGroup =filterLayout.findViewById<View>(R.id.chipGroup) as ChipGroup

        val autoCompleteTextView=filterLayout.findViewById<View>(R.id.edt_category) as AutoCompleteTextView
        autoCompleteTextView.apply {
            threshold=3
            setAdapter(ArrayAdapter(this@FilterSearchActivity,android.R.layout.select_dialog_item,Common.categories))
            onItemClickListener=AdapterView.OnItemClickListener{ _, view, _,_->
                //clear text
                autoCompleteTextView.setText("")

                //add chip
                val chip=inflater.inflate(R.layout.chip_item,null,false) as Chip
                chip.apply {
                    text=(view as TextView).text
                    setOnCloseIconClickListener {
                        chipGroup.removeView(it)
                    }
                }
                chipGroup.addView(chip)


            }
        }

        alertDialog.apply {
            setView(filterLayout)
            setNegativeButton("CANCEL") { dialog: DialogInterface?, _: Int ->
                dialog!!.dismiss()
            }
            setPositiveButton("FILTER") { _: DialogInterface?, _: Int ->
                val filterKey = ArrayList<String>()
                val filterQuery= StringBuilder("")
                for (j in 0 until chipGroup.childCount){
                    val chip = chipGroup.getChildAt(j) as Chip
                    filterKey.add(chip.text.toString())
                }

                //after getting all category, sort it
                filterKey.sort()
                for (key in filterKey)
                    filterQuery.append(key).append(",")
                //remove last ","
                filterQuery.setLength(filterQuery.length-1)
                //get all comics with search
                fetchFilterCategory(filterQuery.toString())
            }
            show()
        }


    }

    private fun fetchFilterCategory(query: String) {
        val comicFiltered=ArrayList<Comic>()
        for (comic in Common.comicList){
            if (comic.Category!= null){
                if (comic.Category!!.contains(query))
                    comicFiltered.add(comic)
            }
        }
        if (comicFiltered.size>0){
            recycler_fiter_search.adapter=MyComicAdapter(this,comicFiltered)
        }
        else{
            Toast.makeText(this,"No Result",Toast.LENGTH_SHORT).show()
        }
    }
}
