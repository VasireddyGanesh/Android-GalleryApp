package com.example.galleryapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView?= null

    private var progressBar: ProgressBar?=null

    private var allImages:ArrayList<Image>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.recyclerView)
        progressBar=findViewById(R.id.progressBar)

        recyclerView?.layoutManager=GridLayoutManager(this,3)

        recyclerView?.setHasFixedSize(true)
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),101)
        }

        allImages=ArrayList<Image>()

        if(allImages!!.isEmpty()){
            progressBar?.visibility=View.VISIBLE
            allImages=getAllImages()
            recyclerView?.adapter=ImageAdapter(this, allImages!!)
            progressBar?.visibility=View.GONE

        }
    }

    private fun getAllImages(): ArrayList<Image>? {
        val images:ArrayList<Image> = ArrayList<Image>()

        val allImagesURI= MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.Images.Media.DATA,MediaStore.Images.Media.DISPLAY_NAME)

        val cursor=this.contentResolver.query(
            allImagesURI,
            projection,
            null,
            null,
            null
        )
        try {
            cursor!!.moveToFirst()
            do {
                val image=Image()
                image.imageName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                images.add(image)
            }while(cursor.moveToNext())
            cursor.close()
        }catch(e:Exception){
            e.printStackTrace()
        }
        return images
    }
}