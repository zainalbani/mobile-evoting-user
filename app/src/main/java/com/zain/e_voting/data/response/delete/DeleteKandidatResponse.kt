package com.zain.e_voting.data.response.delete

import com.google.gson.annotations.SerializedName

data class DeleteKandidatResponse(

	@field:SerializedName("data")
	val data: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
