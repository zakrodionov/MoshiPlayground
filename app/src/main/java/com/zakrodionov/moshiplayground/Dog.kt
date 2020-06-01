package com.zakrodionov.moshiplayground

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dog(
    val name: String?,
    @Json(name = "age_old") //for custom name
    val age: Int?
)