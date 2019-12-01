package com.example.plugin_common.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.util.Log
import dalvik.system.DexClassLoader

/**
 * create by zuyuan on 2019/11/21
 */

/* 插件APK对象 */
data class PluginApk(val packageInfo: PackageInfo,
                     val dexClassLoader: DexClassLoader,
                     val resources: Resources)

/* 插件Apk管理器 主要负责生成PluginApk对象*/
internal class PluginApkManager private constructor(context: Context) {
    companion object {
        private val lock: Any = Any()

        @SuppressLint("StaticFieldLeak")
        private var instance: PluginApkManager? = null

        fun getInstance(context: Context) : PluginApkManager = instance
            ?: synchronized(lock) {
            instance = instance
                ?: PluginApkManager(context)
                instance
        }!!
    }

    private val mContext: Context = context.applicationContext

    //key: packageName  value: apk obj
    private val mApkMap: MutableMap<String, PluginApk> = mutableMapOf()

    fun getApk(apkPath: String) : PluginApk = mApkMap[apkPath] ?: loadApk(apkPath)

    private fun loadApk(apkPath: String): PluginApk {
        Log.d("TAGG", "start generate --> $apkPath")
        val packageInfo: PackageInfo = queryPackageInfo(apkPath)
        val packageName: String = packageInfo.packageName

        //创建apk对象
        Log.d("TAGG", "packageName --> $packageName")
        val resources = createResourcesObj(packageName)
        Log.d("TAGG", "resource --> $resources")
        val dexClassLoader = createDexClassLoader(apkPath)
        val result = PluginApk(packageInfo, dexClassLoader, resources)
        mApkMap[packageName] = result

        return result
    }

    /* kotlin在返回值为空时会报出异常 */
    private fun queryPackageInfo(apkPath: String) : PackageInfo {
        val packageManager = mContext.packageManager
        Log.d("TAGG", "query data")
        return packageManager.getPackageArchiveInfo(apkPath, 0)
    }

    /*生成插件的Resources对象*/
    private fun createResourcesObj(apkPath: String): Resources {
        val addAssetPathMethod = "addAssetPath"

        //获取Assets资源
        val assetManager: AssetManager = AssetManager::class.java.newInstance()
        val assetMethod = assetManager.javaClass
            .getMethod(addAssetPathMethod, String::class.java)
        assetMethod.invoke(assetManager, apkPath)

        //创建资源对象 warn: 开发者不应该创建资源文件
        return Resources(assetManager, mContext.resources.displayMetrics,
            mContext.resources.configuration)
    }

    /*生成DexClassLoader*/
    private fun createDexClassLoader(apkPath: String) =
        //dex文件路径 缓存路径（私有） native库路径 父加载器
        DexClassLoader(apkPath, mContext.getDir("dex", Context.MODE_PRIVATE)
            .absoluteFile.absolutePath, null, mContext.classLoader)

}