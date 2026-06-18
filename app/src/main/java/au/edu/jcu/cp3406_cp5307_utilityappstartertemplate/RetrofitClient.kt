package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://hn.algolia.com/api/v1/"

    val cyberNewsService: CyberNewsService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CyberNewsService::class.java)
    }
}