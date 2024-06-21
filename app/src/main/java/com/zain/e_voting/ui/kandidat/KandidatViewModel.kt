package com.zain.e_voting.ui.kandidat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.zain.e_voting.data.response.GetCalonByIdResponse
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.data.response.base.ErrorResponse
import com.zain.e_voting.data.service.ApiService
import com.zain.e_voting.utils.DatastoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class KandidatViewModel @Inject constructor(
    private val client: ApiService,
    private val pref: DatastoreManager
) : ViewModel() {

    val kandidatResult: MutableLiveData<BaseResponse<GetCalonByIdResponse>> = MutableLiveData()

    fun getCalonById(paslon_id: String) {
        kandidatResult.value = BaseResponse.Loading()
        client.getCalonById(paslon_id)
            .enqueue(object : Callback<GetCalonByIdResponse> {
                override fun onResponse(
                    call: Call<GetCalonByIdResponse>,
                    response: Response<GetCalonByIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        kandidatResult.value = BaseResponse.Success(responseBody)
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse =
                                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorMessage = errorResponse.message
                            kandidatResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            kandidatResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<GetCalonByIdResponse>, t: Throwable) {
                    kandidatResult.value = BaseResponse.Error("Network Error")
                }
            })
    }

}