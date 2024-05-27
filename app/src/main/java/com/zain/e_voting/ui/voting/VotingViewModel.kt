package com.zain.e_voting.ui.voting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.zain.e_voting.data.request.VotingRequest
import com.zain.e_voting.data.response.GetAllCalonResponse
import com.zain.e_voting.data.response.VotingResponse
import com.zain.e_voting.data.response.base.BaseResponse
import com.zain.e_voting.data.response.base.ErrorResponse
import com.zain.e_voting.data.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class VotingViewModel @Inject constructor(
    private val client: ApiService
) : ViewModel() {

    val votingResult: MutableLiveData<BaseResponse<GetAllCalonResponse>> = MutableLiveData()
    val updateResult: MutableLiveData<BaseResponse<VotingResponse>> = MutableLiveData()

    fun getAllCalon() {
        votingResult.value = BaseResponse.Loading()
        client.getAllCalon()
            .enqueue(object : Callback<GetAllCalonResponse> {
                override fun onResponse(
                    call: Call<GetAllCalonResponse>,
                    response: Response<GetAllCalonResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        votingResult.value = BaseResponse.Success(responseBody)
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse =
                                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorMessage = errorResponse.message
                            votingResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            votingResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<GetAllCalonResponse>, t: Throwable) {
                    votingResult.value = BaseResponse.Error("Network Error")
                }
            })
    }
    fun updateVoting(nipd: String,paslonId: String) {
        updateResult.value = BaseResponse.Loading()
        client.updateVoting(nipd, VotingRequest(paslonId))
            .enqueue(object : Callback<VotingResponse> {
                override fun onResponse(
                    call: Call<VotingResponse>,
                    response: Response<VotingResponse>
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

                override fun onFailure(call: Call<VotingResponse>, t: Throwable) {
                    updateResult.value = BaseResponse.Error("Network Error")
                }
            })
    }

}