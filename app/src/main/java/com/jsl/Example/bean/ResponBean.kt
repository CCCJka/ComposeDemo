package com.jsl.Example.bean

data class ResponBean(var data: String = "使用数据类时不使用参数"){

}
fun copy(data: String) = ResponBean(data)

data class Pair<out A, out B>(val first: A, val Second: B){
    override fun toString(): String = "($first,$Second)"    //重载toString函数，默认无参数则输出参数A和B
}

