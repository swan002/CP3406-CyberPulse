package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import retrofit2.http.GET

interface CyberNewsService {

    @GET("v2/top-headlines?category=technology&country=us")
    suspend fun getNews(): NewsResponse
}