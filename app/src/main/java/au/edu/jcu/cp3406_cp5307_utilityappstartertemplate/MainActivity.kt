
package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Row

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CP3406_CP5603UtilityAppStarterTemplateTheme {
                UtilityApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    CP3406_CP5603UtilityAppStarterTemplateTheme {
        UtilityApp()
    }
}

@Composable
fun UtilityApp() {
    var selectedTab by remember { mutableStateOf("Utility") }
    var alertCount by remember { mutableIntStateOf(1) }
    var category by remember { mutableStateOf("All") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Utility") },
                    label = { Text("Utility") },
                    selected = selectedTab == "Utility",
                    onClick = { selectedTab = "Utility" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedTab == "Settings",
                    onClick = { selectedTab = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "Utility" -> UtilityScreen(alertCount, category)
                "Settings" -> SettingsScreen(
                    alertCount = alertCount,
                    onAlertCountChange = { alertCount = it },
                    category = category,
                    onCategoryChange = { category = it }
                )
            }
        }
    }
}

data class CyberAlert(
    val title: String,
    val category: String,
    val severity: String,
    val source: String
)

@Composable
fun UtilityScreen(alertCount: Int, category: String) {
    val repository = CyberNewsRepository()
    val alerts = repository.getAlerts()

    val filteredAlerts = if (category == "All") {
        alerts
    } else {
        alerts.filter { it.category == category }
    }.take(alertCount)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Cyber Pulse",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "At-a-glance cybersecurity threat alerts",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Selected category: $category",
            style = MaterialTheme.typography.bodyMedium
        )

        filteredAlerts.forEach { alert ->

            val severityColor = when (alert.severity) {
                "High" -> Color.Red
                "Medium" -> Color(0xFFFF9800)
                else -> Color(0xFF4CAF50)
            }

            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = "● ${alert.severity.uppercase()}",
                        color = severityColor,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = alert.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text("Category: ${alert.category}")
                    Text("Source: ${alert.source}")
                }
            }
        }

        Button(onClick = { }) {
            Text("Refresh Alerts")
        }
    }
}

@Composable
fun SettingsScreen(
    alertCount: Int,
    onAlertCountChange: (Int) -> Unit,
    category: String,
    onCategoryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        Text("Number of Alerts", style = MaterialTheme.typography.titleMedium)

        Button(onClick = { onAlertCountChange(1) }) {
            Text("1 Alert")
        }

        Button(onClick = { onAlertCountChange(3) }) {
            Text("3 Alerts")
        }

        Button(onClick = { onAlertCountChange(5) }) {
            Text("5 Alerts")
        }

        Text("Category", style = MaterialTheme.typography.titleMedium)

        Button(onClick = { onCategoryChange("All") }) {
            Text("All")
        }

        Button(onClick = { onCategoryChange("Malware") }) {
            Text("Malware")
        }

        Button(onClick = { onCategoryChange("Data Breach") }) {
            Text("Data Breach")
        }

        Button(onClick = { onCategoryChange("Vulnerabilities") }) {
            Text("Vulnerabilities")
        }

        Text("Current Settings: $alertCount alert(s), $category")
    }
}