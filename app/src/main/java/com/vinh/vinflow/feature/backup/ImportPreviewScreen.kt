package com.vinh.vinflow.feature.backup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vinh.vinflow.core.designsystem.component.VinflowSurface
import com.vinh.vinflow.core.designsystem.component.SummaryCard
import com.vinh.vinflow.core.designsystem.component.VinflowContentFrame
import com.vinh.vinflow.core.designsystem.component.VinflowSectionHeader
import com.vinh.vinflow.core.designsystem.component.VinflowButton
import com.vinh.vinflow.core.designsystem.theme.IncomeGreen
import com.vinh.vinflow.core.designsystem.theme.WarningAmber

@Composable
fun ImportPreviewScreen(modifier: Modifier = Modifier) {
    VinflowContentFrame(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "summary-title") {
                VinflowSectionHeader(text = "Tóm tắt file import")
            }
            item(key = "summary") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SummaryCard(
                        title = "Danh mục",
                        value = "0",
                        accentColor = IncomeGreen,
                        modifier = Modifier.weight(1f)
                    )
                    SummaryCard(
                        title = "Giao dịch",
                        value = "0",
                        accentColor = WarningAmber,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item(key = "warning") {
                VinflowSurface(modifier = Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Cảnh báo",
                            color = WarningAmber,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Nhập bản sao lưu sẽ thay thế toàn bộ dữ liệu cục bộ hiện tại. Hành động này không thể hoàn tác.",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Giai đoạn 3 chỉ hiển thị khung xem trước, chưa mở file JSON và chưa ghi dữ liệu.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            item(key = "confirm") {
                VinflowButton(
                    text = "Xác nhận import",
                    onClick = {},
                    enabled = false,
                    leadingIcon = Icons.Default.CheckCircle,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
