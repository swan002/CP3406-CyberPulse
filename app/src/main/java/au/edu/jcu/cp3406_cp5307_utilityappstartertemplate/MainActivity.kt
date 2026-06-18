
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

@Composable
fun UtilityScreen(alertCount: Int, category: String) {
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

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Threat Level: Medium", style = MaterialTheme.typography.titleLarge)
                Text("Latest Alert", style = MaterialTheme.typography.titleMedium)
                Text("Critical vulnerability reported in widely used software.")
                Text("Category: Vulnerability")
                Text("Source: Cybersecurity News")
                Text("Alerts shown: $alertCount")
                Text("Selected category: $category")
            }
        }

        Button(onClick = { }) {
            Text("Refresh Alert")
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