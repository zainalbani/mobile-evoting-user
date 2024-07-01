package com.zain.e_voting.data.service

import com.zain.e_voting.data.request.LoginRequest
import com.zain.e_voting.data.request.UpdateRequest
import com.zain.e_voting.data.request.VotingRequest
import com.zain.e_voting.data.response.GetAllCalonResponse
import com.zain.e_voting.data.response.GetCalonByIdResponse
import com.zain.e_voting.data.response.SearhUserResponse
import com.zain.e_voting.data.response.VotingResponse
import com.zain.e_voting.data.response.create.CreateKandidatResponse
import com.zain.e_voting.data.response.delete.DeleteKandidatResponse
import com.zain.e_voting.data.response.login.LoginResponse
import com.zain.e_voting.data.response.update.UpdateKandidatResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


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

    @GET("/searchuser")
    fun searchUser(@Query("keyword") keyword:String) : Call<SearhUserResponse>

    @Multipart
    @POST("/createcalon")
    fun createKandidat(
        @Part("nama_ketua") namaKetua: RequestBody,
        @Part("nama_wakil_ketua") namaWakil: RequestBody,
        @Part("visi") visi: RequestBody,
        @Part("misi") misi: RequestBody,
        @Part("youtube_link") link: RequestBody,
        @Part("paslon_id") paslonId: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<CreateKandidatResponse>

    @Multipart
    @PUT("/editcalon/{id}")
    fun updateKandidat(
        @Path("id") id: String,
        @Part("nama_ketua") namaKetua: RequestBody,
        @Part("nama_wakil_ketua") namaWakil: RequestBody,
        @Part("visi") visi: RequestBody,
        @Part("misi") misi: RequestBody,
        @Part("youtube_link") link: RequestBody,
        @Part("paslon_id") paslonId: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<UpdateKandidatResponse>
    @PUT("/updatecalon/{id}")
    fun updateKandidatWithoutImage(
        @Path("id") id: String,
        @Body request: UpdateRequest
    ): Call<UpdateKandidatResponse>

    @DELETE("/deletecalon/{id}")
    fun deleteKandidat(
        @Path("id") id: String
    ): Call<DeleteKandidatResponse>

}