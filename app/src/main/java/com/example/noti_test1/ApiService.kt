package com.example.noti_test1

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// 接口，定義與伺服器交流的方法
interface ApiService {
    @POST("submit")
    fun submitText(@Body data: RequestBody): Call<ResponseData>
}

data class RequestBody(val text: String)
data class ResponseData(val status: String, val message: String)
