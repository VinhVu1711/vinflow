package com.vinh.vinflow.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vinh.vinflow.core.designsystem.theme.ChromeIndigo
import com.vinh.vinflow.core.designsystem.theme.LightPeriwinkle
import com.vinh.vinflow.core.designsystem.theme.PeriwinkleMetallic

@Composable
fun ChromePlate(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(16.dp),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(PeriwinkleMetallic, RoundedCornerShape(8.dp))
            .border(BorderStroke(2.dp, LightPeriwinkle), RoundedCornerShape(8.dp))
            .border(BorderStroke(1.dp, ChromeIndigo), RoundedCornerShape(8.dp))
            .padding(padding)
    ) {
        content()
    }
}

