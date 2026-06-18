package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

data class NewsArticle(
    val title: String
)

data class NewsResponse(
    val articles: List<NewsArticle>
)