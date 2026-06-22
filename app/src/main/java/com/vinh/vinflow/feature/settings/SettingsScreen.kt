package com.vinh.vinflow.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.vinh.vinflow.core.designsystem.component.VinflowSurface
import com.vinh.vinflow.core.designsystem.component.VinflowContentFrame
import com.vinh.vinflow.core.designsystem.component.VinflowListRow
import com.vinh.vinflow.core.designsystem.component.VinflowSectionHeader

@Composable
fun SettingsScreen(
    onCategoryManagementClick: () -> Unit,
    onImportPreviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    VinflowContentFrame(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "appearance-title") {
                VinflowSectionHeader(text = "Giao diện")
            }
            item(key = "appearance") {
                VinflowSurface(modifier = Modifier.fillMaxWidth()) {
                    SettingGroup {
                        SettingHeader(icon = Icons.Default.Palette, title = "Giao diện")
                        VinflowListRow(
                            title = "Chế độ hiển thị",
                            supportingText = "Sáng / Tối / Theo hệ thống sẽ dùng DataStore ở giai đoạn 6.",
                            trailingText = "Hệ thống"
                        )
                    }
                }
            }
            item(key = "data-title") {
                VinflowSectionHeader(text = "Dữ liệu")
            }
            item(key = "data") {
                VinflowSurface(modifier = Modifier.fillMaxWidth()) {
                    SettingGroup {
                        VinflowListRow(
                            title = "Quản lý danh mục",
                            supportingText = "Chuẩn bị màn hình thêm, sửa, xóa danh mục cho giai đoạn 4.",
                            trailingText = "Mở",
                            onClick = onCategoryManagementClick
                        )
                        VinflowListRow(
                            title = "Xem trước nhập dữ liệu",
                            supportingText = "Bản xem trước nhập JSON sẽ được nối với trình chọn file ở giai đoạn 7.",
                            trailingText = "Mở",
                            onClick = onImportPreviewClick
                        )
                        VinflowListRow(
                            title = "Xuất bản sao lưu / CSV",
                            supportingText = "Chỉ là khung hiển thị, chưa mở trình chọn file ở giai đoạn 3.",
                            trailingText = "Sau"
                        )
                    }
                }
            }
            item(key = "about-title") {
                VinflowSectionHeader(text = "Ứng dụng")
            }
            item(key = "about") {
                VinflowSurface(modifier = Modifier.fillMaxWidth()) {
                    SettingGroup {
                        SettingHeader(icon = Icons.Default.Info, title = "Vinflow")
                        Text(
                            text = "Ứng dụng quản lý thu chi offline-first, lưu dữ liệu cục bộ trên thiết bị.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}


//Ý nghĩa của cách viết này là ví dụ mình truyền vào
//Hàm này n thành phần UI thì n thành phần đó sẽ được nhét vào
//1 cái Column với cấu hình Column đã thiết lập
@Composable
private fun SettingGroup(content: @Composable ColumnScope.() -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), content = content)
}

@Composable
private fun SettingHeader(
    icon: ImageVector,
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
