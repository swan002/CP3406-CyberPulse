package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

data class NewsResponse(
    val hits: List<NewsArticle>
)

data class NewsArticle(
    val title: String?,
    val url: String?,
    val author: String?
)