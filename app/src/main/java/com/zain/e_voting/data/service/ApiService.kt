package com.zain.e_voting.data.service

import com.zain.e_voting.data.request.LoginRequest
import com.zain.e_voting.data.request.VotingRequest
import com.zain.e_voting.data.response.GetAllCalonResponse
import com.zain.e_voting.data.response.GetCalonByIdResponse
import com.zain.e_voting.data.response.VotingResponse
import com.zain.e_voting.data.response.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {

    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("/getallcalon")
    fun getAllCalon(): Call<GetAllCalonResponse>

    @PUT("/voting/{nipd}")
    fun updateVoting(
        @Path("nipd") nipd: String,
        @Body paslonId: VotingRequest
    ): Call<VotingResponse>

    @GET("/getcalonbyid/{paslon_id}")
    fun getCalonById(
        @Path("paslon_id") paslon_id: String
    ):Call<GetCalonByIdResponse>

}