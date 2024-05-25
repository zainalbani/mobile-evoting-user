package com.zain.e_voting.data.request

data class LoginRequest(
    val nipd: String? = null,
    val otp: String? = null
)