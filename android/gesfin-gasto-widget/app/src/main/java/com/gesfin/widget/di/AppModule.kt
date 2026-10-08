package com.gesfin.widget.di

import android.content.Context
import com.gesfin.widget.core.auth.AuthInterceptor
import com.gesfin.widget.core.datastore.SessionDataStore
import com.gesfin.widget.data.remote.api.ApiService
import com.gesfin.widget.data.repository.AuthRepository
import com.gesfin.widget.data.repository.TransactionRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object AppModule {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    fun provideSessionDataStore(context: Context): SessionDataStore {
        return SessionDataStore(context)
    }

    fun provideAuthInterceptor(sessionDataStore: SessionDataStore): AuthInterceptor {
        return AuthInterceptor(sessionDataStore)
    }

    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    fun provideAuthRepository(sessionDataStore: SessionDataStore): AuthRepository {
        return AuthRepository(sessionDataStore)
    }

    fun provideTransactionRepository(apiService: ApiService): TransactionRepository {
        return TransactionRepository(apiService)
    }
}
