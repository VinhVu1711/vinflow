package com.vinh.vinflow.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.vinh.vinflow.core.designsystem.component.SummaryCard
import com.vinh.vinflow.core.designsystem.component.VinflowContentFrame
import com.vinh.vinflow.core.designsystem.component.VinflowSectionHeader
import com.vinh.vinflow.core.designsystem.component.VinflowButton
import com.vinh.vinflow.core.designsystem.theme.BalanceBlue
import com.vinh.vinflow.core.designsystem.theme.ExpenseRed
import com.vinh.vinflow.core.designsystem.theme.IncomeGreen

@Composable
fun DashboardScreen(
    onAddTransactionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    VinflowContentFrame(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item(key = "balance") {
                VinflowSurface(modifier = Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Số dư hiện tại",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "0 đ",
                            color = BalanceBlue,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Dữ liệu thật sẽ được nối ở giai đoạn 5.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            item(key = "summary") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SummaryCard(
                        title = "Số dư",
                        value = "0 đ",
                        accentColor = BalanceBlue,
                        modifier = Modifier.weight(1f)
                    )
                    SummaryCard(
                        title = "Thu",
                        value = "0 đ",
                        accentColor = IncomeGreen,
                        modifier = Modifier.weight(1f)
                    )
                    SummaryCard(
                        title = "Chi",
                        value = "0 đ",
                        accentColor = ExpenseRed,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item(key = "recent-title") {
                VinflowSectionHeader(text = "Giao dịch gần đây")
            }
            item(key = "recent-empty") {
                EmptyState(
                    title = "Chưa có giao dịch",
                    message = "Hãy thêm giao dịch đầu tiên để màn tổng quan hiển thị số liệu.",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item(key = "cta") {
                VinflowButton(
                    text = "Thêm giao dịch",
                    onClick = onAddTransactionClick,
                    leadingIcon = Icons.Default.Add,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }
        }
    }
}
