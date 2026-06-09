package com.vinh.vinflow.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class VinflowDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Dashboard : VinflowDestination("dashboard", "Dashboard", Icons.Default.Home)
    data object Transactions : VinflowDestination("transactions", "Transactions", Icons.Default.List)
    data object Statistics : VinflowDestination("statistics", "Statistics", Icons.Default.BarChart)
    data object Settings : VinflowDestination("settings", "Settings", Icons.Default.Settings)

    companion object {
        val bottomTabs = listOf(Dashboard, Transactions, Statistics, Settings)
    }
}

