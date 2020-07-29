package id.buhankita.catatanramadhan.data.api

import id.buhankita.catatanramadhan.utils.Constant.BASE_URL_DOA
import id.buhankita.catatanramadhan.utils.Constant.BASE_URL_SALAT
import id.buhankita.catatanramadhan.utils.Constant.SECRET_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private fun getServiceSalat(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL_SALAT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun getServiceDoa(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            addInterceptor(Interceptor{ chain ->
                val builder = chain.request().newBuilder()
                builder.header("secret-key", SECRET_KEY)
                return@Interceptor chain.proceed(builder.build())
            })
        }.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL_DOA)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val apiSalat: ApiService = getServiceSalat().create(ApiService::class.java)

    val apiDoa: ApiService = getServiceDoa().create(ApiService::class.java)
}