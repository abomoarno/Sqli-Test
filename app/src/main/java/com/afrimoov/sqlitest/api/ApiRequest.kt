package com.afrimoov.sqlitest.api

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class ApiRequest (

    @SerializedName("page")
    @Expose
    var page : Int = 1
){

    fun getHash(): Map<String, String> {
        val gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
        val type = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(gson.toJson(this), type)
    }

}