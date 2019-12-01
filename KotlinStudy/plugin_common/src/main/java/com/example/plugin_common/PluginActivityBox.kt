package com.example.plugin_common

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.plugin_common.abs.AbsPluginActivity
import com.example.plugin_common.abs.AbsPluginActivityBox
import com.example.plugin_common.util.PluginApkManager

/**
 * create by zuyuan on 2019/11/21
 * AbsPluginActivityBox 类加载 会触发AbsPluginActivity的类加载（SystemClassLoader）
 * 但是AbsPluginActivity的子类需要使用DexClassLoader加载
 */
internal class PluginActivityBox(proxyActivity: ProxyActivity) : AbsPluginActivityBox(proxyActivity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        val apkPath = mProxyActivity.intent.getStringExtra(ProxyActivity.EXTRA_KEY_APK_PATH)
        val activityPath = mProxyActivity.intent
            .getStringExtra(ProxyActivity.EXTRA_KEY_ACTIVITY_PATH)

        try {
            //加载活动
            mApk = PluginApkManager.getInstance(mProxyActivity).getApk(apkPath)
            val apk = mApk!!
            val activityName = "com.example.plugin_one.PluginActivity"
            Log.d("TAGG", "activityName = $activityName")
            loadDexClass(apk.dexClassLoader, "com.example.plugin_one.T1")
            mPluginActivity = loadDexClass(apk.dexClassLoader, activityName) as AbsPluginActivity
            (mPluginActivity as AbsPluginActivity).attach(mProxyActivity, apk)
            (mPluginActivity as AbsPluginActivity).onCreate(savedInstanceState)
        } catch (e: Exception) {
            e.printStackTrace()

            Toast.makeText(mProxyActivity.applicationContext, "插件加载失败",
                Toast.LENGTH_LONG).show()
            mProxyActivity.finish()
        }
    }

    /* 加载Activity实例 */
    private fun loadDexClass(loader: ClassLoader, activityName: String) : Any {
        val clazz = loader.loadClass(activityName)
        val constructor = clazz.getConstructor()
        constructor.isAccessible = true
        return constructor.newInstance()
    }
}