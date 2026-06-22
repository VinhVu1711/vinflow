package com.vinh.vinflow.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.vector.ImageVector

sealed class VinflowDestination(
    val route: String,
    val label: String,
    val icon: ImageVector? = null
) {
    //Sử dụng object vì mỗi màn hình cần duy nhất 1 instance. Ví dụ không cần tạo nhiều Dashboard khác nhau
    //Sử dụng data object là vì Kotlin sẽ tạo toString, equals, hashCode cho object
    data object Dashboard : VinflowDestination("dashboard", "Tổng quan", Icons.Default.Home)
    data object Transactions : VinflowDestination("transactions", "Giao dịch", Icons.Default.List)
    data object Statistics : VinflowDestination("statistics", "Thống kê", Icons.Default.BarChart)
    data object Settings : VinflowDestination("settings", "Cài đặt", Icons.Default.Settings)
    data object AddTransaction : VinflowDestination("add_transaction", "Thêm giao dịch", Icons.Default.AddCircle)
    data object EditTransaction : VinflowDestination(
        route = "edit_transaction/{transactionId}",
        label = "Sửa giao dịch",
        icon = Icons.Default.Edit
    ) {
        const val ARG_TRANSACTION_ID = "transactionId"

        fun createRoute(transactionId: Long): String = "edit_transaction/$transactionId"
    }

    data object CategoryManagement : VinflowDestination(
        "category_management",
        "Quản lý danh mục",
        Icons.Default.Category
    )

    data object ImportPreview : VinflowDestination(
        "import_preview",
        "Xem trước nhập dữ liệu",
        Icons.Default.FileUpload
    )

    //Sử dụng companion object như thế này là để gọi thẳng thuộc tính luôn bằng tên Class
    //Không cần phải khởi tạo instance
    companion object {
        //BottomTabs chỉ nên chứa 4 màn hình chính --> Gom nhóm lại
        val bottomTabs = listOf(Dashboard, Transactions, Statistics, Settings)
        val secondaryRoutes = listOf(AddTransaction, EditTransaction, CategoryManagement, ImportPreview)
    }
}
