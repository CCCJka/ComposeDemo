package com.jsl.Example.viewmodel

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {


    fun init(){

    }

    fun plusNum(vararg num: Int): Int{
        val size: Int
        var count: Int  = 0
        for (size in num){
            count = size + count
        }
        return count
    }

}