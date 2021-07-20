package com.example.parabara.data.entities

data class ResponseData<T>(
    val code: String,
    val status: Int,
    val data: T,
    val error: Boolean,
    val message: String
)