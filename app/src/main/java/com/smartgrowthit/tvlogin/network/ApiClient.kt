package com.smartgrowthit.tvlogin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://tv-auth.abe-bamendou.org") // À adapter
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
