package com.test.finero.finerio.utility

import com.test.finero.finerio.network.FinerioApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FinerioNetwork {

    private val loggingInterceptor = HttpLoggingInterceptor()

    private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)

    private val retrofit = Retrofit.Builder()
            .baseUrl(StringUtility.BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(FinerioApiService::class.java)

    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }

}