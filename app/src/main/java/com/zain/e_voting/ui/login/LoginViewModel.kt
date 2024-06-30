package com.zain.e_voting.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.zain.e_voting.data.request.LoginRequest
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.data.response.base.ErrorResponse
import com.zain.e_voting.data.response.login.LoginResponse
import com.zain.e_voting.data.service.ApiService
import com.zain.e_voting.utils.DatastoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val client: ApiService,
    private val pref: DatastoreManager
) : ViewModel() {

    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()

    fun loginUser(nipd: String, otp: String) {
        loginResult.value = BaseResponse.Loading()
        client.loginUser(LoginRequest(nipd, otp))
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        loginResult.value = BaseResponse.Success(responseBody)
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse =
                                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorMessage = errorResponse.message
                            loginResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            loginResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginResult.value = BaseResponse.Error("Network Error")
                }
            })
    }

    fun saveIsLoginStatus(status: Boolean) {
        viewModelScope.launch {
            pref.saveIsLoginStatus(status)
        }
    }


    fun saveNipd(nipd: String) {
        viewModelScope.launch {
            pref.saveNipd(nipd)
        }
    }
    fun saveRole(role: String) {
        viewModelScope.launch {
            pref.saveRole(role)
        }
    }
    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }
    fun getNipd(): LiveData<String> {
        return pref.getNipd.asLiveData()
    }
    fun getRole(): LiveData<String> {
        return pref.getRole.asLiveData()
    }
    fun removeNipd() {
        viewModelScope.launch {
            pref.removeNipd()
        }
    }
    fun removeRole() {
        viewModelScope.launch {
            pref.removeRole()
        }
    }
    fun removeIsLoginStatus() {
        viewModelScope.launch {
            pref.removeIsLoginStatus()
        }
    }

}