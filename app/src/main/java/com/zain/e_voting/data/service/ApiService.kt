package com.zain.e_voting.data.service

import com.zain.e_voting.data.request.LoginRequest
import com.zain.e_voting.data.response.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>


}