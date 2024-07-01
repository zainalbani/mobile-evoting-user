package com.zain.e_voting.data.response.hasil

import com.google.gson.annotations.SerializedName

data class GetAllHasilResponse(

	@field:SerializedName("data")
	val data: List<DataHasil?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataHasil(

	@field:SerializedName("jumlah_suara")
	val jumlahSuara: Int? = null,

	@field:SerializedName("paslon_id")
	val paslonId: Int? = null
)
