package com.example.exchange_rate

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    val baseUrl = "https://v6.exchangerate-api.com/v6/9e97d3e16534544613c61749/latest/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val exchangeService = retrofit.create(ExchangeService::class.java)
}