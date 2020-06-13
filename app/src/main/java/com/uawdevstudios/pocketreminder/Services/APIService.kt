package com.uawdevstudios.pocketreminder.Services

import retrofit2.Call
import com.uawdevstudios.pocketreminder.Models.ServerResponse
import retrofit2.http.*

interface APIService {

    // Path parameter in get request
    // @GET("destination/{id}")
    // fun getDestination(@Path("id")id: Int): Call<Destination>

    // Query parameter in get request
    // @GET("destination/{id}")
    // fun getDestination(@Query("id")id: Int): Call<Destination>

    // Multiple Query parameters in get request
    // @GET("destination")
    // fun getDestination(@Query("param1") param1: String?, @Query("param2") param2: String?): Call<Destination>

    // Query Map
    // @GET("destination")
    // fun getDestination(@QueryMap filter: HashMap<String, String>): Call<Destination>
    // Defining hash map
    //val filter = HashMap<String, String>()
    //filter["param1"] = "value1"
    //filter["param2"] = "value2"


    //fun addDestination(@Body newDestination: Destination): Call<Destination>

    @FormUrlEncoded
    @POST("validateusername")
    fun checkUsername(@Field("userName") userName: String?): Call<ServerResponse>

    @FormUrlEncoded
    @POST("validateemail")
    fun checkEmail(@Field("email") email: String?): Call<ServerResponse>

}
