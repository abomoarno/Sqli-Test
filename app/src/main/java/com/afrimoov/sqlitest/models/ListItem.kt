package com.afrimoov.sqlitest.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

sealed class ListItem {
    data class Footer(var loading: Boolean) : ListItem()
    data class Header(val text: String) : ListItem()

    @Parcelize
    data class Employee(
        @SerializedName("id")
        @Expose
        var id : Int,

        @SerializedName("first_name")
        @Expose
        var firstName : String,

        @SerializedName("last_name")
        @Expose
        var lastName : String,

        @SerializedName("email")
        @Expose
        var email : String,

        @SerializedName("avatar")
        @Expose
        var avatar : String) : ListItem(), Parcelable
}
