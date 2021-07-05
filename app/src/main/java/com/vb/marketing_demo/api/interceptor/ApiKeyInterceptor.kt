package com.vb.marketing_demo.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response


private const val API_KEY_CONST = "secret-key"
private const val API_KEY = "\$2b\$10\$MIJJup0V2dbrbgY4bhg4IeFXGLXoMdDA3Jq4qAhlZWh1dbdxrQnKG"

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        request = request.newBuilder()
            .addHeader(API_KEY_CONST, API_KEY)
            .build()
        return chain.proceed(request)
    }
}