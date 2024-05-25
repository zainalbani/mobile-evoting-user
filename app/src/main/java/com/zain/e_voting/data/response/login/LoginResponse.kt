package com.zain.e_voting.data.response.login

data class LoginResponse(
	val data: Data? = null,
	val message: String? = null,
	val status: Boolean? = null,
	val token: String? = null
)

data class Data(
	val namaSiswa: String? = null,
	val nipd: String? = null
)

