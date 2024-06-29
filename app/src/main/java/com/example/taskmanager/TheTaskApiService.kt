package com.example.taskmanager

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface TheTaskApiService {
    @GET("api/list")
    fun ListTask(
        @Query("limit") limit :Int
        //@Query("size") format:String
        ):Call<List<DataModel>>

    @FormUrlEncoded
    @POST("api/add/")
    fun AddTask(
        @Field("head") head:String,
        @Field("desc") desc:String
    ):Call<Map<String,Int>>

    @DELETE("api/delete/{id}")
    fun DeleteTask(
        @Path("id") id :Int
    ):Call<String>

}