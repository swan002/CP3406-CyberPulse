package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

class CyberNewsRepository {

    suspend fun getRemoteAlerts(): List<CyberAlert> {
        return try {
            val response = RetrofitClient.cyberNewsService.getNews()

            response.hits.take(5).map { article ->
                CyberAlert(
                    title = article.title ?: "Cybersecurity update",
                    category = "Cybersecurity",
                    severity = "Medium",
                    source = article.author ?: "Hacker News"
                )
            }
        } catch (e: Exception) {
            getAlerts()
        }
    }

    fun getAlerts(): List<CyberAlert> {
        return listOf(
            CyberAlert(
                "Critical vulnerability reported in widely used software.",
                "Vulnerabilities",
                "Medium",
                "Cybersecurity News"
            ),
            CyberAlert(
                "New malware campaign targets Android users.",
                "Malware",
                "High",
                "Threat Monitor"
            ),
            CyberAlert(
                "Large data breach exposes customer records.",
                "Data Breach",
                "High",
                "Security Weekly"
            ),
            CyberAlert(
                "Phishing emails increase during exam season.",
                "Malware",
                "Medium",
                "Cyber Awareness Feed"
            ),
            CyberAlert(
                "Security update released for common web framework.",
                "Vulnerabilities",
                "Low",
                "Developer Security News"
            )
        )
    }
}