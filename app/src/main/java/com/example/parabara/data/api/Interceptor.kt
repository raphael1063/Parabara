package com.example.parabara.data.api

import com.example.parabara.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class Interceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val requestUrl = chain.request().url.toString()

        if (requestUrl.startsWith(BuildConfig.BASE_URL)) {
            request.addHeader(X_TOKEN, BuildConfig.SERVER_TOKEN)
        }
        return chain.proceed(request.build())
    }

    companion object {
        private const val X_TOKEN = "x-token"
    }
}