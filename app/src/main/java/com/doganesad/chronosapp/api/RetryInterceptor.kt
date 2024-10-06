package com.matlubapps.videoai.api

import okhttp3.Interceptor
import okhttp3.Response

class RetryInterceptor(private val maxRetry: Int = 7) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())
        var tryCount = 0

        while (response.code >= 500 && tryCount < maxRetry) {
//        while (!response.isSuccessful && tryCount < maxRetry) {
            tryCount++
            Thread.sleep(2000) // Wait for 2 seconds before retrying
            response.close() // Important to avoid leaking resources
            response = chain.proceed(chain.request())
        }

        return response
    }

}