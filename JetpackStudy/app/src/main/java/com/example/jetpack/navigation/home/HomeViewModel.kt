package com.example.jetpack.navigation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    val mTextData = MutableLiveData<String>()

    fun showText() {
        mTextData.value = "ParentFragment"
    }
}
