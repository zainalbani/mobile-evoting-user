package com.zain.e_voting.data.response

import com.google.gson.annotations.SerializedName

data class VotingResponse(

	@field:SerializedName("data")
	val data: List<Int?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
