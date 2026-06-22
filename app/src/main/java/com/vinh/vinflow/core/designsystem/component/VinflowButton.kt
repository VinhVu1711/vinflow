package com.vinh.vinflow.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class VinflowButtonVariant {
    Primary,
    Secondary
}

@Composable
fun VinflowButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: VinflowButtonVariant = VinflowButtonVariant.Primary,
    leadingIcon: ImageVector? = null
) {
    val containerColor = when (variant) {
        VinflowButtonVariant.Primary -> MaterialTheme.colorScheme.primary
        VinflowButtonVariant.Secondary -> MaterialTheme.colorScheme.surfaceVariant
    }
    val borderColor = when (variant) {
        VinflowButtonVariant.Primary -> MaterialTheme.colorScheme.primary
        VinflowButtonVariant.Secondary -> MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
    }
    val contentColor = when (variant) {
        VinflowButtonVariant.Primary -> MaterialTheme.colorScheme.onPrimary
        VinflowButtonVariant.Secondary -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 48.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = BorderStroke(2.dp, borderColor),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let { icon ->
                Icon(imageVector = icon, contentDescription = null)
            }
            Text(text = text)
        }
    }
}
