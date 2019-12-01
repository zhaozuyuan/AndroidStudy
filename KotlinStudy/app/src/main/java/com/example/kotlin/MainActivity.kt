package com.example.kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.plugin_common.util.simpleLaunchPlugin
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    val bitmapList: MutableList<Bitmap> = arrayListOf()

    private val mAdapter: BaseAdapter = object : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View =
            convertView ?: LayoutInflater.from(baseContext).inflate(R.layout.item_circle_menu,
                parent, false)

        override fun getItem(position: Int): Any? = null

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getCount(): Int = 6
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        cmlMenu.mAdapter = mAdapter

        println("onCreate")

         Thread{
             while (true) {
                 Thread.sleep(1000)
                 bitmapList.add(BitmapFactory.decodeResource(resources, R.drawable.img_head))
             }
         }.start()
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart")
    }

    override fun onStart() {
        super.onStart()
        println("onStart")
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
    }

    override fun onPause() {
        super.onPause()
        println("onPause")
    }

    override fun onStop() {
        super.onStop()
        println("onStop")
    }

    fun onPluginClick(view: View) {
        val handler = Handler()
        val cachePath = cacheDir.absolutePath + "/plugin_one.apk"
        val assetManager = assets

        Thread{
            val inputStream = assetManager.open("plugin_one.apk")

            val file = File(cachePath)
            val outputStream = BufferedOutputStream(FileOutputStream(file))

            val bytes = ByteArray(1024)
            inputStream.use { inIt ->
                outputStream.use { outIt ->
                    while (inIt.read(bytes) != -1) {
                        outIt.write(bytes)
                        outIt.flush()
                    }
                }
            }

            handler.postDelayed({
                val f = File(cachePath)
                Log.d("TAGG", "finish--->${f.length()}")

                simpleLaunchPlugin(this, cachePath, cachePath)
                Log.d("TAGG", "断点执行后..")
            }, 500)
        }.start()
    }
}
