package com.vinh.vinflow.feature.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vinh.vinflow.core.designsystem.component.VinflowScaffold
import com.vinh.vinflow.core.navigation.VinflowDestination
import com.vinh.vinflow.feature.backup.ImportPreviewScreen
import com.vinh.vinflow.feature.category.CategoryManagementScreen
import com.vinh.vinflow.feature.dashboard.DashboardScreen
import com.vinh.vinflow.feature.settings.SettingsScreen
import com.vinh.vinflow.feature.statistics.StatisticsScreen
import com.vinh.vinflow.feature.transaction.TransactionFormScreen
import com.vinh.vinflow.feature.transaction.TransactionsScreen

@Composable
fun VinflowApp() {
    //Tạo controller để quản lý navigation
    val navController = rememberNavController()
    //Biến route hiện tại thành Compose State. Route đổi -> UI recomposition
    //để update title, bottom bar, ...
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    //Nếu màn hiện tại thuôc Bottom tab thì hiện bottom bar
    //Nếu là màn phụ thì ẩn bar và hiện nút back
    val selectedTabRoute = currentDestination.selectedBottomTabRoute()
    val showBottomBar = selectedTabRoute != null

    VinflowScaffold(
        title = currentDestination.screenTitle(),
        showBack = !showBottomBar,
        selectedTabRoute = selectedTabRoute,
        showBottomBar = showBottomBar,
        onBackClick = { navController.popBackStack() },
        onTabSelected = { destination ->
            navController.navigate(destination.route) {
                //quay về root của graph, tránh stack tab bị phình
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                //Bấm lại tab hiện tại thì không tạo thêm bản sao
                launchSingleTop = true
                //Khi quay lại tab thì khôi phục state
                restoreState = true
            }
        }
    ) { innerPadding ->
        VinflowNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}

@Composable
fun VinflowNavHost(
    navController: androidx.navigation.NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = VinflowDestination.Dashboard.route,
        modifier = modifier
    ) {
        //Khi route là Dashboard thì render DashboardScreen
        composable(VinflowDestination.Dashboard.route) {
            DashboardScreen(
                onAddTransactionClick = {
                    navController.navigate(VinflowDestination.AddTransaction.route)
                }
            )
        }
        composable(VinflowDestination.Transactions.route) {
            TransactionsScreen(
                onAddTransactionClick = {
                    navController.navigate(VinflowDestination.AddTransaction.route)
                },
                onEditTransactionClick = { transactionId ->
                    navController.navigate(VinflowDestination.EditTransaction.createRoute(transactionId))
                }
            )
        }
        composable(VinflowDestination.Statistics.route) {
            StatisticsScreen()
        }
        composable(VinflowDestination.Settings.route) {
            SettingsScreen(
                onCategoryManagementClick = {
                    navController.navigate(VinflowDestination.CategoryManagement.route)
                },
                onImportPreviewClick = {
                    navController.navigate(VinflowDestination.ImportPreview.route)
                }
            )
        }
        composable(VinflowDestination.AddTransaction.route) {
            TransactionFormScreen(isEdit = false)
        }
        composable(
            route = VinflowDestination.EditTransaction.route,
            arguments = listOf(
                navArgument(VinflowDestination.EditTransaction.ARG_TRANSACTION_ID) {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments
                ?.getLong(VinflowDestination.EditTransaction.ARG_TRANSACTION_ID)
            TransactionFormScreen(
                isEdit = true,
                transactionId = transactionId
            )
        }
        composable(VinflowDestination.CategoryManagement.route) {
            CategoryManagementScreen()
        }
        composable(VinflowDestination.ImportPreview.route) {
            ImportPreviewScreen()
        }
    }
}

// Kiểm tra màn hiện tại có thuộc bottom tab không
// Nếu có trả về route tab đó nếu không trả về null
private fun NavDestination?.selectedBottomTabRoute(): String? {
    return VinflowDestination.bottomTabs
        .firstOrNull { destination ->
            this?.hierarchy?.any { it.route == destination.route } == true
        }
        ?.route
}
//map title dựa trên route hiện tại
private fun NavDestination?.screenTitle(): String {
    return when (this?.route) {
        VinflowDestination.Dashboard.route -> VinflowDestination.Dashboard.label
        VinflowDestination.Transactions.route -> VinflowDestination.Transactions.label
        VinflowDestination.Statistics.route -> VinflowDestination.Statistics.label
        VinflowDestination.Settings.route -> VinflowDestination.Settings.label
        VinflowDestination.AddTransaction.route -> VinflowDestination.AddTransaction.label
        VinflowDestination.EditTransaction.route -> VinflowDestination.EditTransaction.label
        VinflowDestination.CategoryManagement.route -> VinflowDestination.CategoryManagement.label
        VinflowDestination.ImportPreview.route -> VinflowDestination.ImportPreview.label
        else -> "Vinflow"
    }
}
