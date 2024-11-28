package com.bliss.blissandroidchallenge.data.usersRepo.remote

import com.bliss.blissandroidchallenge.domain.usersRepo.model.Repo
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

const val avatarUrl = "https://api.github.com/users/google/repos?page="

fun repoGetRequest(page: Int): List<Repo> {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(avatarUrl + page)
        .build()

    val response = client.newCall(request).execute()

    if (!response.isSuccessful) {
        throw Exception("Request failed with code: ${response.code}")
    }

    val responseBody = response.body?.string()

    val repo = Gson().fromJson(responseBody, Array<Repo>::class.java).toList()

    return repo
}