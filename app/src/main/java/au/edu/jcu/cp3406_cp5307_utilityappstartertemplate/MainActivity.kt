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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

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
fun UtilityApp(viewModel: CyberPulseViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf("Dashboard") }

    val alerts by viewModel.alerts.collectAsState()
    val alertCount by viewModel.alertCount.collectAsState()
    val category by viewModel.category.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                    label = { Text("Dashboard") },
                    selected = selectedTab == "Dashboard",
                    onClick = { selectedTab = "Dashboard" }
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
                "Dashboard" -> UtilityScreen(
                    alertCount = alertCount,
                    category = category,
                    alerts = alerts,
                    isLoading = isLoading,
                    onRefresh = { viewModel.refreshAlerts() }
                )

                "Settings" -> SettingsScreen(
                    alertCount = alertCount,
                    onAlertCountChange = { viewModel.updateAlertCount(it) },
                    category = category,
                    onCategoryChange = { viewModel.updateCategory(it) }
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
fun UtilityScreen(
    alertCount: Int,
    category: String,
    alerts: List<CyberAlert>,
    isLoading: Boolean,
    onRefresh: () -> Unit
) {

    val filteredAlerts = if (category == "All") {
        alerts
    } else {
        alerts.filter { it.category == category }
    }.take(alertCount)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "🛡 Cyber Pulse",
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

        if (isLoading) {
            Text(
                text = "Loading latest cyber alerts...",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "🛡 Threat Overview",
                    style = MaterialTheme.typography.titleLarge
                )

                Text("Alerts Loaded: ${alerts.size}")
                Text("Category: $category")
                Text("Status: Live Feed Connected")
                Text("Source: Cyber News API")
            }
        }

        if (filteredAlerts.isEmpty()) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("No alerts found for selected category.")
                }
            }
        }

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

        Button(onClick = onRefresh) {
            Text(if (isLoading) "Loading..." else "Refresh Alerts")
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

        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    "Number of Alerts",
                    style = MaterialTheme.typography.titleMedium
                )

                Button(
                    onClick = { onAlertCountChange(1) }
                ) {
                    Text(if (alertCount == 1) "✓ 1 Alert" else "1 Alert")
                }

                Button(
                    onClick = { onAlertCountChange(3) }
                ) {
                    Text(if (alertCount == 3) "✓ 3 Alerts" else "3 Alerts")
                }

                Button(
                    onClick = { onAlertCountChange(5) }
                ) {
                    Text(if (alertCount == 5) "✓ 5 Alerts" else "5 Alerts")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    "Category",
                    style = MaterialTheme.typography.titleMedium
                )

                Button(
                    onClick = { onCategoryChange("All") }
                ) {
                    Text(if (category == "All") "✓ All" else "All")
                }

                Button(
                    onClick = { onCategoryChange("Malware") }
                ) {
                    Text(if (category == "Malware") "✓ Malware" else "Malware")
                }

                Button(
                    onClick = { onCategoryChange("Data Breach") }
                ) {
                    Text(if (category == "Data Breach") "✓ Data Breach" else "Data Breach")
                }

                Button(
                    onClick = { onCategoryChange("Vulnerabilities") }
                ) {
                    Text(if (category == "Vulnerabilities") "✓ Vulnerabilities" else "Vulnerabilities")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Current Settings",
                    style = MaterialTheme.typography.titleMedium
                )

                Text("Alerts: $alertCount")
                Text("Category: $category")
            }
        }
    }
}