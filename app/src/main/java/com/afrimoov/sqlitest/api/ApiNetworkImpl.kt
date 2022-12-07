package com.afrimoov.sqlitest.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiNetworkImpl @Inject constructor() : ApiDataSources {

    init {
        buildHttpClient()
    }

    private lateinit var retrofit : Retrofit
    private lateinit var apiService : ApiService
    private lateinit var client: OkHttpClient

    override suspend fun getListEmployees(
        request: ApiRequest
    ): ApiResponse {
        return apiService.getListEmployees(requestMap = request.getHash())
    }

    private fun addDebugInterceptors(builder: Builder) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addNetworkInterceptor(StethoInterceptor())
        builder.addInterceptor(interceptor)

        // Timeout
        builder.readTimeout((5 * 60).toLong(), TimeUnit.SECONDS)
        builder.writeTimeout((5 * 60).toLong(), TimeUnit.SECONDS)
    }

    private fun setupRetrofitAPI(client: OkHttpClient) {
        val gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setLenient()
            .create()
        retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    private fun buildHttpClient() {
        val builder = Builder()
        addDebugInterceptors(builder)
        client = builder.build();
        setupRetrofitAPI(client)
    }
}