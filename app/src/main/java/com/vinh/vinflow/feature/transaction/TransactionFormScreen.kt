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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vinh.vinflow.core.designsystem.component.VinflowSurface
import com.vinh.vinflow.core.designsystem.component.VinflowContentFrame
import com.vinh.vinflow.core.designsystem.component.VinflowButton

@Composable
fun TransactionFormScreen(
    isEdit: Boolean,
    transactionId: Long? = null,
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
                            text = if (isEdit) "Shell sửa giao dịch" else "Shell thêm giao dịch",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isEdit) {
                                "Màn hình đã nhận mã giao dịch mẫu: ${transactionId ?: 0}. Biểu mẫu thật sẽ được triển khai ở giai đoạn 4."
                            } else {
                                "Biểu mẫu thật, kiểm tra hợp lệ và lưu dữ liệu sẽ được triển khai ở giai đoạn 4."
                            },
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            item(key = "amount") {
                FormFieldPlaceholder(label = "Số tiền", value = "0 đ")
            }
            item(key = "type") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Loại giao dịch",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SegmentPlaceholder(
                            text = "Thu nhập",
                            selected = true,
                            modifier = Modifier.weight(1f)
                        )
                        SegmentPlaceholder(
                            text = "Chi tiêu",
                            selected = false,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            item(key = "category") {
                FormFieldPlaceholder(label = "Danh mục", value = "Chưa chọn")
            }
            item(key = "date") {
                FormFieldPlaceholder(label = "Ngày", value = "Hôm nay")
            }
            item(key = "note") {
                FormFieldPlaceholder(label = "Ghi chú", value = "Không bắt buộc")
            }
            item(key = "save") {
                VinflowButton(
                    text = "Lưu giao dịch",
                    onClick = {},
                    enabled = false,
                    leadingIcon = Icons.Default.Save,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun FormFieldPlaceholder(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
                RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun SegmentPlaceholder(
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
