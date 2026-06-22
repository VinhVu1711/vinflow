package com.vinh.vinflow.feature.statistics

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vinh.vinflow.core.designsystem.component.VinflowSurface
import com.vinh.vinflow.core.designsystem.component.EmptyState
import com.vinh.vinflow.core.designsystem.component.SummaryCard
import com.vinh.vinflow.core.designsystem.component.VinflowContentFrame
import com.vinh.vinflow.core.designsystem.component.VinflowSectionHeader
import com.vinh.vinflow.core.designsystem.theme.BalanceBlue
import com.vinh.vinflow.core.designsystem.theme.ExpenseRed
import com.vinh.vinflow.core.designsystem.theme.IncomeGreen
import com.vinh.vinflow.core.designsystem.theme.WarningAmber

//mock data để vẽ biểu đồ
private val ChartBars = listOf(36, 64, 44, 82, 52, 70)

@Composable
fun StatisticsScreen(modifier: Modifier = Modifier) {
    VinflowContentFrame(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "header") {
                VinflowSurface(modifier = Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Thống kê tháng",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Biểu đồ thật và truy vấn tổng hợp sẽ được nối ở giai đoạn 6.",
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
                    SummaryCard(
                        title = "Cân bằng",
                        value = "0 đ",
                        accentColor = BalanceBlue,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item(key = "chart-title") {
                VinflowSectionHeader(text = "Xu hướng thu chi")
            }
            item(key = "chart") {
                ChartPlaceholder()
            }
            item(key = "empty") {
                EmptyState(
                    title = "Chưa có dữ liệu thống kê",
                    message = "Khi có giao dịch, biểu đồ chi theo danh mục và top spending sẽ hiển thị tại đây.",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ChartPlaceholder(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
                RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        ChartBars.forEachIndexed { index, height ->
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(height.dp)
                    .background(
                        color = when (index % 3) {
                            0 -> IncomeGreen
                            1 -> BalanceBlue
                            else -> WarningAmber
                        },
                        shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
                    )
            )
        }
    }
}
