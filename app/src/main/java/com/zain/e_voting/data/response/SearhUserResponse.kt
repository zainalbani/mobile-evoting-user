package com.zain.e_voting.data.response

import com.google.gson.annotations.SerializedName

data class SearhUserResponse(

	@field:SerializedName("data")
	val data: List<DataSearch?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataSearch(

	@field:SerializedName("is_active")
	val isActive: String? = null,

	@field:SerializedName("nama_siswa")
	val namaSiswa: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("nama_ibu")
	val namaIbu: String? = null,

	@field:SerializedName("kelas")
	val kelas: String? = null,

	@field:SerializedName("daftar")
	val daftar: Int? = null,

	@field:SerializedName("nowa")
	val nowa: Any? = null,

	@field:SerializedName("otp")
	val otp: Any? = null,

	@field:SerializedName("paslon_id")
	val paslonId: Any? = null,

	@field:SerializedName("nipd")
	val nipd: String? = null
)
