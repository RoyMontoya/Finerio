package com.test.finero.finerio.network

import com.test.finero.finerio.responses.LoginResponse
import com.test.finero.finerio.responses.MeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FinerioApiService {

    @POST("login")
    fun LoginCall(@Body body: HashMap<String, String>): Call<LoginResponse>

    @GET("me")
    fun MeCall(@Query("authorization") authorization: String): Call<MeResponse>

}