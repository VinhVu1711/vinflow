package com.vinh.vinflow.feature.transaction

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vinh.vinflow.core.designsystem.component.VinflowSurface
import com.vinh.vinflow.core.designsystem.component.EmptyState
import com.vinh.vinflow.core.designsystem.component.VinflowContentFrame
import com.vinh.vinflow.core.designsystem.component.VinflowSectionHeader
import com.vinh.vinflow.core.designsystem.component.VinflowButton

private val TransactionFilterLabels = listOf("Tất cả", "Thu nhập", "Chi tiêu", "Tháng này")

@Composable
fun TransactionsScreen(
    onAddTransactionClick: () -> Unit,
    onEditTransactionClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    VinflowContentFrame(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "header") {
                VinflowSurface(modifier = Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Lịch sử giao dịch",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Tìm kiếm, bộ lọc và danh sách thật sẽ được nối ở giai đoạn 5.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            item(key = "search") {
                SearchPlaceholder()
            }
            item(key = "filters") {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(
                        count = TransactionFilterLabels.size,
                        key = { index -> TransactionFilterLabels[index] }
                    ) { index ->
                        FilterChipPlaceholder(
                            text = TransactionFilterLabels[index],
                            selected = index == 0
                        )
                    }
                }
            }
            item(key = "section") {
                VinflowSectionHeader(text = "Danh sách")
            }
            item(key = "empty") {
                EmptyState(
                    title = "Chưa có giao dịch",
                    message = "Danh sách sẽ tự cập nhật sau khi chức năng thêm giao dịch được triển khai.",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item(key = "cta") {
                VinflowButton(
                    text = "Thêm giao dịch",
                    onClick = onAddTransactionClick,
                    leadingIcon = Icons.Default.Add,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun SearchPlaceholder(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Tìm theo ghi chú hoặc danh mục",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun FilterChipPlaceholder(
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
            .padding(horizontal = 12.dp, vertical = 8.dp),
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.labelLarge
    )
}
