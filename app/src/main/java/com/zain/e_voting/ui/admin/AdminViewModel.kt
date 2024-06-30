package com.zain.e_voting.ui.admin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.data.response.base.ErrorResponse
import com.zain.e_voting.data.response.create.CreateKandidatResponse
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
}