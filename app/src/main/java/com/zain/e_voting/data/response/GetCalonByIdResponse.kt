package com.zain.e_voting.data.response

import com.google.gson.annotations.SerializedName

data class GetCalonByIdResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Data(

	@field:SerializedName("youtube_link")
	val youtubeLink: String? = null,

	@field:SerializedName("misi")
	val misi: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("nama_ketua")
	val namaKetua: String? = null,

	@field:SerializedName("nama_wakil_ketua")
	val namaWakilKetua: String? = null,

	@field:SerializedName("paslon_id")
	val paslonId: Int? = null,

	@field:SerializedName("visi")
	val visi: String? = null
)
