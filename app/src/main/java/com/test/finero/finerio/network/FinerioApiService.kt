package com.test.finero.finerio.network

import com.test.finero.finerio.responseObjects.LoginResponse
import com.test.finero.finerio.responseObjects.MeResponse
import com.test.finero.finerio.responseObjects.MovimientosResponse
import retrofit2.Call
import retrofit2.http.*

interface FinerioApiService {

    @POST("login")
    fun loginCall(@Body body: HashMap<String, String>): Call<LoginResponse>

    @Headers("accept: application/json;charset=UTF-8",
            "Accept-Encoding: gzip, deflate, br",
            "Accept-Language: en-US,en;q=0.9,es-MX;q=0.8,es-US;q=0.7,es;q=0.6",
            "Connection: keep-alive",
            "content-type: application/json;charset=UTF-8",
            "Host: api.finerio.mx",
            "Origin: https://app.finerio.mx",
            "Referer: https://app.finerio.mx/login",
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
    @GET("me")
    fun meCall(@Header("authorization") auth: String): Call<MeResponse>

    @Headers("accept: application/json;charset=UTF-8",
            "Accept-Encoding: gzip, deflate, br",
            "Accept-Language: en-US,en;q=0.9,es-MX;q=0.8,es-US;q=0.7,es;q=0.6",
            "Connection: keep-alive",
            "content-type: application/json;charset=UTF-8",
            "Host: api.finerio.mx",
            "Origin: https://app.finerio.mx",
            "Referer: https://app.finerio.mx/app/dashboard",
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
    @GET("users/{id}/movements")
    fun movimientosCall(@Path("id") id: String, @QueryMap options: Map<String, String>,
                        @Header("authorization") auth: String): Call<MovimientosResponse>

}