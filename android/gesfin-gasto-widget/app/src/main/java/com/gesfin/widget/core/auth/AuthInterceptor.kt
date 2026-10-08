package com.gesfin.widget.core.auth

import com.gesfin.widget.core.datastore.SessionDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sessionDataStore: SessionDataStore) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val sessionData = runBlocking { sessionDataStore.sessionData.first() }
        val originalRequest = chain.request()
        
        if (sessionData.authToken.isEmpty() || !sessionData.isLoggedIn) {
            return chain.proceed(originalRequest)
        }
        
        val requestWithAuth = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${sessionData.authToken}")
            .build()
        
        return chain.proceed(requestWithAuth)
    }
}
