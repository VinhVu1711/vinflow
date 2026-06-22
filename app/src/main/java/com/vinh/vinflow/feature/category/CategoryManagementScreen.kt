package com.vinh.vinflow.feature.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vinh.vinflow.core.designsystem.component.VinflowSurface
import com.vinh.vinflow.core.designsystem.component.EmptyState
import com.vinh.vinflow.core.designsystem.component.VinflowContentFrame
import com.vinh.vinflow.core.designsystem.component.VinflowSectionHeader
import com.vinh.vinflow.core.designsystem.component.VinflowButton

@Composable
fun CategoryManagementScreen(modifier: Modifier = Modifier) {
    VinflowContentFrame(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "header") {
                VinflowSurface(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Danh mục thu/chi sẽ được quản lý tại đây ở giai đoạn 4.",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            item(key = "segments") {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CategorySegment(text = "Thu nhập", selected = true, modifier = Modifier.weight(1f))
                    CategorySegment(text = "Chi tiêu", selected = false, modifier = Modifier.weight(1f))
                }
            }
            item(key = "section") {
                VinflowSectionHeader(text = "Danh sách danh mục")
            }
            item(key = "empty") {
                EmptyState(
                    title = "Chưa hiển thị danh mục",
                    message = "Danh mục mặc định đã được tạo ở tầng dữ liệu; màn hình thêm, sửa, xóa sẽ được nối ở giai đoạn 4.",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item(key = "add") {
                VinflowButton(
                    text = "Thêm danh mục",
                    onClick = {},
                    enabled = false,
                    leadingIcon = Icons.Default.Add,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun CategorySegment(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier
            .background(
                color = if (selected) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold
    )
}
