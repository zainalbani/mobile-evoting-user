package com.zain.e_voting.data.response.update

import com.google.gson.annotations.SerializedName

data class UpdateKandidatResponse(

	@field:SerializedName("data")
	val data: List<Int?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
