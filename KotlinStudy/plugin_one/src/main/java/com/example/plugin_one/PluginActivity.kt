package com.example.plugin_one

import android.os.Bundle
import com.example.kotlin.R
import com.example.plugin_common.abs.AbsPluginActivity

class PluginActivity : AbsPluginActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_plugin)
    }
}
