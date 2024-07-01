package com.zain.e_voting.data.request

import com.google.gson.annotations.SerializedName

data class UpdateRequest(
    @SerializedName("nama_ketua")
    val namaKetua: String,
    @SerializedName("nama_wakil")
    val namaWakil: String,
    @SerializedName("visi")
    val visi: String,
    @SerializedName("misi")
    val misi: String,
    @SerializedName("youtube_link")
    val link: String,
    @SerializedName("paslon_id")
    val paslonId: String,

)