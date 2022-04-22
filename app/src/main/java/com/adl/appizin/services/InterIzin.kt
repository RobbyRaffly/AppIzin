package com.adl.appizin.services

import com.adl.appizin.model.GetIzinResponse
import com.adl.appizin.model.ResponsePostData
import com.adl.appizin.model.ResponseTracking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import javax.annotation.Generated

interface InterIzin {

    @Headers("x-api-key:A0867B853E8A6911C38827D19D3247FD")
    @GET("api/izin/all")
    fun getAllIzin(): Call<GetIzinResponse>

    @FormUrlEncoded
    @Headers("x-api-key:A0867B853E8A6911C38827D19D3247FD")
    @POST("api/izin/add")
    fun addDataForm(@Field("kategori")kategori:String, @Field("dari")dari:String,
                    @Field("sampai")sampai:String,@Field("perihal")perihal:String,
                    @Field("keterangan")keterangan:String):Call<ResponsePostData>

    @Multipart
    @Headers("X-Api-Key:6D83551EAC167A26DC10BB7609EA9AEF")
    @POST("api/ijin/add")
    fun addDataAndImage(@Part("kategori") kategori: RequestBody, @Part("dari") dari:RequestBody,
                        @Part("sampai") sampai:RequestBody, @Part("perihal") perihal:RequestBody,
                        @Part("keterangan") keterangan:RequestBody,
                        @Part file: MultipartBody.Part):Call<ResponsePostData>

    @FormUrlEncoded
    @Headers("x-api-key:A0867B853E8A6911C38827D19D3247FD")
    @POST("api/tracking/add")
    fun addDataTracking(@Field("id_user")id_user:String, @Field("latitude")latitude:String,
                    @Field("longitude")longitude:String,@Field("timestamp")timestamp:String):Call<ResponseTracking>
}