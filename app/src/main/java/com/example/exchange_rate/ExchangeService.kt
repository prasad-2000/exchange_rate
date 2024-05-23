package com.example.exchange_rate

import retrofit2.http.GET

data class ExchangeModelResponse(
    val base_code:String,
    val conversion_rates:Map<String,Double>
)

interface ExchangeService {
    @GET("USD")
    fun getRate(): retrofit2.Call<ExchangeModelResponse>
}


