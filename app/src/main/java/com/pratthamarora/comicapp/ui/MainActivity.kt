package com.pratthamarora.comicapp.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pratthamarora.comicapp.R
import com.pratthamarora.comicapp.adapter.MyComicAdapter
import com.pratthamarora.comicapp.adapter.MySliderAdapter
import com.pratthamarora.comicapp.common.Common
import com.pratthamarora.comicapp.model.Comic
import com.pratthamarora.comicapp.utils.BannerLoadDoneListener
import com.pratthamarora.comicapp.utils.ComicLoadListener
import com.pratthamarora.comicapp.utils.PicassoImageLoader
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*
import ss.com.bannerslider.Slider

class MainActivity : AppCompatActivity(), BannerLoadDoneListener, ComicLoadListener {

    //Database
    private val bannerRef by lazy {
        FirebaseDatabase.getInstance().getReference("Banners")
    }
    private val comicRef by lazy {
        FirebaseDatabase.getInstance().getReference("Comic")
    }

    lateinit var bannerLoadDoneListener: BannerLoadDoneListener
    lateinit var comicLoadListener: ComicLoadListener

    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init
        bannerLoadDoneListener=this
        comicLoadListener=this

        alertDialog=SpotsDialog.Builder().setContext(this)
                .setCancelable(false)
                .setMessage("Please Wait")
                .build()


        swipe_layout.apply {
            setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark)
            setOnRefreshListener {
                loadBanners()
                loadComics()
            }
            post {
                loadBanners()
                loadComics()
            }
        }

        Slider.init(PicassoImageLoader())

        recycler_comic.setHasFixedSize(true)
        recycler_comic.layoutManager=GridLayoutManager(this,2)

        btn_show_filter_show.setOnClickListener {
            startActivity(Intent(this,FilterSearchActivity::class.java))
        }

    }

    private fun loadComics() {
        alertDialog.show()

        comicRef.addListenerForSingleValueEvent(object :ValueEventListener{
            var comicLoad:MutableList<Comic> = ArrayList()

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MainActivity,""+p0.message,Toast.LENGTH_SHORT).show()


            }

            override fun onDataChange(p0: DataSnapshot) {
                for (comicSnapshot in p0.children){
                    val comic=comicSnapshot.getValue(Comic::class.java)
                    comicLoad.add(comic!!)
                }
                comicLoadListener.onComicLoadDone(comicLoad)

            }

        })

    }

    private fun loadBanners() {
        bannerRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MainActivity,""+p0.message,Toast.LENGTH_SHORT).show()

            }

            override fun onDataChange(p0: DataSnapshot) {
                val bannerList = ArrayList<String>()
                for (banner in p0.children){
                    val image = banner.getValue(String::class.java)
                    image?.let { bannerList.add(it) }
                }

                bannerLoadDoneListener.onBannerLoadDone(bannerList)
            }

        })

    }
    override fun onBannerLoadDone(banners: List<String>) {
        slider.setAdapter(MySliderAdapter(banners))

    }

    override fun onComicLoadDone(comicList: List<Comic>) {
        alertDialog.dismiss()
        Common.comicList=comicList
        recycler_comic.adapter=MyComicAdapter(this,comicList)
        txt_comic.text=StringBuilder("NEW COMIC (")
                .append(comicList.size)
                .append(")")

        if (swipe_layout.isRefreshing)
            swipe_layout.isRefreshing=false

    }


}
