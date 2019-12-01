package com.example.plugin_common.abs

import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.view.KeyEvent
import com.example.plugin_common.util.PluginApk
import com.example.plugin_common.ProxyActivity
import kotlin.properties.Delegates

/**
 * create by zuyuan on 2019/11/21
 */

internal interface ResourcesCallback {

    fun getResources(): Resources

    fun getAssets(): AssetManager

    fun getTheme(): Resources.Theme
}

/*插件活动的容器*/
internal abstract class AbsPluginActivityBox(protected val mProxyActivity: ProxyActivity) {

    /*使AbsPluginActivity脱离类预验证*/
    protected var mPluginActivity: Any? = null

    protected var mApk: PluginApk? by Delegates.observable(null as? PluginApk, {
            _, _, _ ->
        mResourcesCallback = if (mApk != null) {
             object : ResourcesCallback {
                override fun getResources(): Resources = mApk!!.resources

                override fun getAssets(): AssetManager = mApk!!.resources.assets

                override fun getTheme(): Resources.Theme = mApk!!.resources.newTheme()
            }
        } else null
    })

    protected var mResourcesCallback: ResourcesCallback? = null

    /*应该由子类去实现*/
    open fun onCreate(savedInstanceState: Bundle?) {}

    open fun onRestart() {
        (mPluginActivity as? AbsPluginActivity) ?.onRestart()
    }

    open fun onStart() {
        (mPluginActivity as? AbsPluginActivity) ?.onStart()
    }

    open fun onResume() {
        (mPluginActivity as? AbsPluginActivity) ?.onResume()
    }

    open fun onPause() {
        (mPluginActivity as? AbsPluginActivity) ?.onPause()
    }

    open fun onStop() {
        (mPluginActivity as? AbsPluginActivity) ?.onStop()
    }

    open fun onDestroy() {
        (mPluginActivity as? AbsPluginActivity) ?.onDestroy()
    }

    open fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        (mPluginActivity as? AbsPluginActivity) ?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        (mPluginActivity as? AbsPluginActivity) ?.onActivityResult(requestCode, resultCode, data)
    }

    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean = false

    open fun getResourcesCallback() = mResourcesCallback
}