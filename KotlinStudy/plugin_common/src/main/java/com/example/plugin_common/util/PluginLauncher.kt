package com.example.plugin_common.util

import android.content.Context
import android.content.Intent
import com.example.plugin_common.ProxyActivity

/**
 * create by zuyuan on 2019/11/21
 */
fun createPluginIntent(context: Context, apkPath: String, activityPath: String) : Intent {
    val intent = Intent(context, ProxyActivity::class.java)
    intent.putExtra(ProxyActivity.EXTRA_KEY_APK_PATH, apkPath)
    intent.putExtra(ProxyActivity.EXTRA_KEY_ACTIVITY_PATH, activityPath)
    return intent
}

fun simpleLaunchPlugin(context: Context, apkPath: String, activityPath: String) {
    val intent = createPluginIntent(context, apkPath, activityPath)
    context.startActivity(intent)
}