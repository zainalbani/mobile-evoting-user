package com.zain.e_voting.data.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class User(

	@field:SerializedName("role")
	val role: String? = null,

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

	@field:SerializedName("otp")
	val otp: String? = null,

	@field:SerializedName("paslon_id")
	val paslonId: Any? = null,

	@field:SerializedName("nipd")
	val nipd: String? = null,

	@field:SerializedName("email")
	val email: Any? = null
)
