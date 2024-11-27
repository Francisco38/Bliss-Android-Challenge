package com.bliss.blissandroidchallenge.data.emojis.remote

import com.bliss.blissandroidchallenge.domain.emojis.model.Emoji
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

val emojiUrl = "https://api.github.com/emojis"

fun emojiGetRequest(): List<Emoji> {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(emojiUrl)
        .build()

    val response = client.newCall(request).execute()

    if (!response.isSuccessful) {
        throw Exception("Request failed with code: ${response.code}")
    }

    val responseBody = response.body?.string()

    val emojiMapType = object : TypeToken<Map<String, String>>() {}.type
    val emojiMap: Map<String, String> = Gson().fromJson(responseBody, emojiMapType)

    return emojiMap.map { (name, url) ->
        Emoji(name = name, url = url)
    }
}