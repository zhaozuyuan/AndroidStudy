package com.example.plugin_common

import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.plugin_common.abs.AbsPluginActivityBox

/**
 * create by zuyuan on 2019/11/21
 * 代理活动，已在manifests文件中注册
 */
class ProxyActivity : AppCompatActivity() {

    private val mBox: AbsPluginActivityBox = PluginActivityBox(this)

    companion object {
        //intent apk路径的键
        const val EXTRA_KEY_APK_PATH = "plugin.apk_path.key"

        //intent 活动的键
        const val EXTRA_KEY_ACTIVITY_PATH = "plugin.activity.key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBox.onCreate(savedInstanceState)
    }

    override fun onRestart() {
        super.onRestart()
        mBox.onRestart()
    }

    override fun onStart() {
        super.onStart()
        mBox.onStart()
    }

    override fun onPause() {
        super.onPause()
        mBox.onPause()
    }

    override fun onStop() {
        super.onStop()
        mBox.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBox.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mBox.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBox.onActivityResult(requestCode, resultCode, data)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean
            = if (!mBox.onKeyDown(keyCode, event)) true else super.onKeyDown(keyCode, event)

    override fun getResources(): Resources = mBox.getResourcesCallback()?.getResources()
        ?: super.getResources()

    override fun getAssets(): AssetManager = mBox.getResourcesCallback()?.getAssets()
        ?: super.getAssets()

    override fun getTheme(): Resources.Theme = mBox.getResourcesCallback()?.getTheme()
        ?: super.getTheme()
}