package com.zain.e_voting.data.response.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
