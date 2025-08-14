package com.jsl.Example.db.bean

import com.google.gson.annotations.SerializedName

data class UserBean(
    @SerializedName("name")
    var name: String,
    @SerializedName("account")
    var account: String,
    @SerializedName("pwd")
    var pwd: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("phone")
    var phone: String,
)