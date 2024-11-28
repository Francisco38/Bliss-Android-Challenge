package com.bliss.blissandroidchallenge.domain.usersRepo.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Repo(
    @PrimaryKey val id: Int,
    @SerializedName("full_name") val fullName: String,
    val private: Boolean
)