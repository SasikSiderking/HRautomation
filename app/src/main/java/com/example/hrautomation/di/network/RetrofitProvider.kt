package com.example.hrautomation.di.network

import com.example.hrautomation.BuildConfig
import com.example.hrautomation.data.api.*
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.utils.retrofit_adapter.CustomAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitProvider @Inject constructor(private val tokenRepository: TokenRepository, private val tokenApi: TokenApi) {

    private val authorizedHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(tokenRepository.getAccessToken() ?: "FakeToken"))
//                TODO(Remove fake token)
            .authenticator(TokenAuthenticator(tokenApi, tokenRepository))
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(logging)
                }
            }
            .build()
    }

    private val logging: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    //    private val contentType = "application/json".toMediaType()
    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create()

    //    @OptIn(ExperimentalSerializationApi::class)
    private val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CustomAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    val userApi: UserApi by lazy {
        retrofitBuilder
            .client(authorizedHttpClient)
            .build()
            .create()
    }

    val employeesApi: EmployeesApi by lazy {
        retrofitBuilder
            .client(authorizedHttpClient)
            .build()
            .create()
    }

    val faqApi: FaqApi by lazy {
        retrofitBuilder
            .client(authorizedHttpClient)
            .build()
            .create()
    }

    val productApi: ProductApi by lazy {
        retrofitBuilder
            .client(authorizedHttpClient)
            .build()
            .create()
    }

    val restaurantsApi: RestaurantsApi by lazy {
        retrofitBuilder
            .client(authorizedHttpClient)
            .build()
            .create()
    }
}