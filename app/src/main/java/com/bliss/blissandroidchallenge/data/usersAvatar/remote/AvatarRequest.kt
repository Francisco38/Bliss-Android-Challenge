package com.bliss.blissandroidchallenge.data.usersAvatar.remote

import com.bliss.blissandroidchallenge.domain.usersAvatar.model.Avatar
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

const val avatarUrl = "https://api.github.com/users/"

fun avatarGetRequest(username: String): Avatar {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(avatarUrl + username)
        .build()

    val response = client.newCall(request).execute()

    if (!response.isSuccessful) {
        throw Exception("Request failed with code: ${response.code}")
    }

    val responseBody = response.body?.string()

    val avatar = Gson().fromJson(responseBody, Avatar::class.java)

    return avatar
}