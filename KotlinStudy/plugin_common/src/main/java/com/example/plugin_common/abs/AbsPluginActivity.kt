package com.example.plugin_common.abs

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.plugin_common.util.PluginApk

/**
 * create by zuyuan on 2019/11/21
 */
abstract class AbsPluginActivity : IPluginActivityCallback {

    protected lateinit var mProxyActivity : AppCompatActivity

    protected lateinit var mApk : PluginApk

    /*Activity的参数，快捷访问*/
    val resources = mApk.resources
    val classLoader = mApk.dexClassLoader
    val packageInfo = mApk.packageInfo
    val windowManager = mProxyActivity.windowManager
    val applicationContext = mProxyActivity.applicationContext
    val intent = mProxyActivity.intent
    val layoutInflater = mProxyActivity.layoutInflater
    val packageName = mApk.packageInfo.packageName
    val window = mProxyActivity.window

    /*onCreate()方法必须继承*/
    //override fun onCreate(savedInstanceState: Bundle?) {}

    fun attach(proxyActivity: AppCompatActivity, apk: PluginApk) {
        mProxyActivity = proxyActivity
        mApk = apk
    }

    override fun onRestart() {}

    override fun onStart() {}

    override fun onResume() {}

    override fun onPause() {}

    override fun onStop() {}

    override fun onDestroy() {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean = false

    fun <T : View> findViewById(id: Int) = mProxyActivity.findViewById<T>(id)

    fun setContentView(layoutId: Int) = mProxyActivity.setContentView(layoutId)

    fun setContentView(view: View) = mProxyActivity.setContentView(view)
}