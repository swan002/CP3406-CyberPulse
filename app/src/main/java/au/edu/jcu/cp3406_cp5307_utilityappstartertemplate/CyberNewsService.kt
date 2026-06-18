package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import retrofit2.http.GET

interface CyberNewsService {

    @GET("search?query=cybersecurity")
    suspend fun getNews(): NewsResponse
}