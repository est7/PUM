package pl.udu.uwr.pum.foody.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.udu.uwr.pum.foody.util.baseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: FoodApi by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(FoodApi::class.java)
    }
}