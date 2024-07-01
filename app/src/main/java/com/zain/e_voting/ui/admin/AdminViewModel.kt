package com.zain.e_voting.ui.admin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.zain.e_voting.data.request.UpdateRequest
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.data.response.base.ErrorResponse
import com.zain.e_voting.data.response.create.CreateKandidatResponse
import com.zain.e_voting.data.response.update.UpdateKandidatResponse
import com.zain.e_voting.data.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val client: ApiService
) : ViewModel() {

    val createResult: MutableLiveData<BaseResponse<CreateKandidatResponse>> = MutableLiveData()
    val updateResult: MutableLiveData<BaseResponse<UpdateKandidatResponse>> = MutableLiveData()

    fun createKandidat(
        namaKetua: RequestBody,
        namaWakil: RequestBody,
        visi: RequestBody,
        misi: RequestBody,
        link: RequestBody,
        paslonId: RequestBody,
        image: MultipartBody.Part
    ) {
        createResult.value = BaseResponse.Loading()
        client.createKandidat(namaKetua, namaWakil, visi, misi, link, paslonId, image)
            .enqueue(object : Callback<CreateKandidatResponse> {
                override fun onResponse(
                    call: Call<CreateKandidatResponse>,
                    response: Response<CreateKandidatResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        createResult.value = BaseResponse.Success(responseBody)
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse =
                                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorMessage = errorResponse.message
                            createResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            createResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<CreateKandidatResponse>, t: Throwable) {
                    createResult.value = BaseResponse.Error("Network Error")
                }
            })
    }

    fun updateKandidat(
        id: String,
        namaKetua: RequestBody,
        namaWakil: RequestBody,
        visi: RequestBody,
        misi: RequestBody,
        link: RequestBody,
        paslonId: RequestBody,
        image: MultipartBody.Part
    ) {
        updateResult.value = BaseResponse.Loading()
        client.updateKandidat(id, namaKetua, namaWakil, visi, misi, link, paslonId, image)
            .enqueue(object : Callback<UpdateKandidatResponse> {
                override fun onResponse(
                    call: Call<UpdateKandidatResponse>,
                    response: Response<UpdateKandidatResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        updateResult.value = BaseResponse.Success(responseBody)
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse =
                                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorMessage = errorResponse.message
                            updateResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            updateResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateKandidatResponse>, t: Throwable) {
                    updateResult.value = BaseResponse.Error("Network Error")
                }
            })
    }

    fun updateKandidatWithoutImage(
        id: String,
        namaKetua: String,
        namaWakil: String,
        visi: String,
        misi: String,
        link: String,
        paslonId: String,
    ) {
        updateResult.value = BaseResponse.Loading()
        client.updateKandidatWithoutImage(id, UpdateRequest(namaKetua, namaWakil, visi, misi, link, paslonId))
            .enqueue(object : Callback<UpdateKandidatResponse> {
                override fun onResponse(
                    call: Call<UpdateKandidatResponse>,
                    response: Response<UpdateKandidatResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        updateResult.value = BaseResponse.Success(responseBody)
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse =
                                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorMessage = errorResponse.message
                            updateResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            updateResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateKandidatResponse>, t: Throwable) {
                    updateResult.value = BaseResponse.Error("Network Error")
                }
            })
    }
}