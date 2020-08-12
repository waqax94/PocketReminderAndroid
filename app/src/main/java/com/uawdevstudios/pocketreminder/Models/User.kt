package com.uawdevstudios.pocketreminder.Models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
        @SerializedName("_id")
        @Expose
        var _id: String? = "",
        @SerializedName("firstName")
        @Expose
        var firstName: String? = "",
        @SerializedName("lastName")
        @Expose
        var lastName: String? = "",
        @SerializedName("userName")
        @Expose
        var userName: String? = "",
        @SerializedName("email")
        @Expose
        var email: String? = "",
        @SerializedName("phone")
        @Expose
        var phone: String? = "",
        @SerializedName("password")
        @Expose
        var password: String? = "",
        @SerializedName("dob")
        @Expose
        var dob: String? = ""
) : Parcelable