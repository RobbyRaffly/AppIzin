package com.adl.appizin.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    fun getInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return okHttpClient


    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(" https://25f3-125-164-3-60.ap.ngrok.io/cicool/")//("http://192.168.1.68/cicool/")//("http://www.omdbapi.com/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getIzin() = getRetrofit().create(InterIzin::class.java)
}