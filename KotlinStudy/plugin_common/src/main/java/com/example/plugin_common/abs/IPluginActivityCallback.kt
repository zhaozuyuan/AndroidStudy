package com.example.plugin_common.abs

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent

/**
 * create by zuyuan on 2019/11/21
 * 作为活动会被回调的关键方法
 */
interface IPluginActivityCallback {

    fun onCreate(savedInstanceState: Bundle?)

    fun onRestart() {}

    fun onStart() {}

    fun onResume() {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroy() {}

    /*权限申请结果*/
    fun onRequestPermissionsResult(requestCode: Int,
                                        permissions: Array<out String>,
                                        grantResults: IntArray
    )

    /*活动返回结果*/
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    /*点击事件*/
    fun onKeyDown(keyCode: Int, event: KeyEvent?) : Boolean
}