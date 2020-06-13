package com.uawdevstudios.pocketreminder.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ServerResponse (
        @SerializedName("connectionStatus")
        @Expose
        var connectionStatus: Boolean? = false,
        @SerializedName("queryStatus")
        @Expose
        var queryStatus: Boolean? = false,
        @SerializedName("message")
        @Expose
        var message: String? = null
)