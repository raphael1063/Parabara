package com.example.parabara.data.api

import com.example.parabara.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class Interceptor @Inject constructor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val requestUrl = chain.request().url.toString()

        if (requestUrl.startsWith(BuildConfig.BASE_URL)) {
            request.addHeader(X_TOKEN, token)
        }
        return chain.proceed(request.build())
    }

    companion object {
        private const val X_TOKEN = "x-token"
    }
}