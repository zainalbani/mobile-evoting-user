package com.zain.e_voting.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.zain.e_voting.data.request.RegisterRequest
import com.zain.e_voting.data.response.SearhUserResponse
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.data.response.base.ErrorResponse
import com.zain.e_voting.data.response.login.LoginResponse
import com.zain.e_voting.data.response.register.RegisterResponse
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

    val registerResult: MutableLiveData<BaseResponse<RegisterResponse>> = MutableLiveData()

    fun registerUser(nipd: String, email: String) {
        registerResult.value = BaseResponse.Loading()

        client.registerUser(RegisterRequest(nipd, email))
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        registerResult.value = BaseResponse.Success(responseBody)
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse =
                                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorMessage = errorResponse.message
                            registerResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            registerResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    registerResult.value = BaseResponse.Error("Network Error")
                }
            })

    }
}