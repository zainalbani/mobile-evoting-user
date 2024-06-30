package com.zain.e_voting.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.zain.e_voting.data.response.SearhUserResponse
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.data.response.base.ErrorResponse
import com.zain.e_voting.data.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val client: ApiService
) : ViewModel() {

    val searchUserResult: MutableLiveData<BaseResponse<SearhUserResponse>> = MutableLiveData()

    fun searchUser(keyword: String) {
        searchUserResult.value = BaseResponse.Loading()

        client.searchUser(keyword)
            .enqueue(object : Callback<SearhUserResponse> {
                override fun onResponse(
                    call: Call<SearhUserResponse>,
                    response: Response<SearhUserResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        searchUserResult.value = BaseResponse.Success(responseBody)
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse =
                                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorMessage = errorResponse.message
                            searchUserResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            searchUserResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<SearhUserResponse>, t: Throwable) {
                    searchUserResult.value = BaseResponse.Error("Network Error")
                }
            })

    }
}