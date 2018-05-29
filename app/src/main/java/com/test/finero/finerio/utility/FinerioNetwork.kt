package com.test.finero.finerio.utility

import com.test.finero.finerio.network.FinerioApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FinerioNetwork{

    val retrofit = Retrofit.Builder()
            .baseUrl(StringUtility.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(FinerioApiService::class.java)

}