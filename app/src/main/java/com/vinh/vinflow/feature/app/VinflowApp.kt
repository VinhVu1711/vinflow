package com.vinh.vinflow.feature.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vinh.vinflow.core.designsystem.component.ChromePlate
import com.vinh.vinflow.core.designsystem.component.EmptyState
import com.vinh.vinflow.core.designsystem.component.Y2kButton
import com.vinh.vinflow.core.designsystem.theme.CarbonNavy
import com.vinh.vinflow.core.navigation.VinflowDestination

@Composable
fun VinflowApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                VinflowDestination.bottomTabs.forEach { destination ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(destination.icon, contentDescription = destination.label) },
                        label = { Text(destination.label) }
                    )
                }
            }
        },
        modifier = Modifier.background(CarbonNavy)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = VinflowDestination.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(VinflowDestination.Dashboard.route) {
                PhaseOnePlaceholderScreen(
                    title = "Vinflow Dashboard",
                    message = "Foundation Compose/Hilt/navigation đã sẵn sàng. Dashboard dữ liệu thật sẽ được triển khai ở Phase 5."
                )
            }
            composable(VinflowDestination.Transactions.route) {
                PhaseOnePlaceholderScreen(
                    title = "Transactions",
                    message = "Transaction CRUD sẽ được triển khai ở Phase 4."
                )
            }
            composable(VinflowDestination.Statistics.route) {
                PhaseOnePlaceholderScreen(
                    title = "Statistics",
                    message = "Thống kê và chart sẽ được triển khai ở Phase 6."
                )
            }
            composable(VinflowDestination.Settings.route) {
                PhaseOnePlaceholderScreen(
                    title = "Settings",
                    message = "DataStore theme/currency sẽ được triển khai ở Phase 6."
                )
            }
        }
    }
}

@Composable
private fun PhaseOnePlaceholderScreen(
    title: String,
    message: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ChromePlate(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = title, style = MaterialTheme.typography.headlineSmall)
                Text(text = "Offline-first expense tracker theo phong cách Nintendo 2001/Y2K.")
            }
        }
        EmptyState(
            title = "Phase 1",
            message = message,
            modifier = Modifier.fillMaxWidth()
        )
        Y2kButton(text = "Placeholder CTA", onClick = {})
    }
}

