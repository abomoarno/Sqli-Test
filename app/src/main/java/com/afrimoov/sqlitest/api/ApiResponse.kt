package com.afrimoov.sqlitest.api

import com.afrimoov.sqlitest.models.ListItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse(

    @SerializedName("page")
    @Expose
    var page : Int,

    @SerializedName("per_page")
    @Expose
    var perPage : Int,

    @SerializedName("total")
    @Expose
    var total : Int,

    @SerializedName("total_pages")
    @Expose
    var totalPages : Int,

    @SerializedName("data")
    @Expose
    var data : List<ListItem.Employee>

)
