package com.zain.e_voting.data.request

import com.google.gson.annotations.SerializedName

data class VotingRequest(
    @field:SerializedName("paslon_id")
    val paslonId: String? = null,
)