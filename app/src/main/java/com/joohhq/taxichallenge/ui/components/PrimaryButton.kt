package com.joohhq.taxichallenge.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joohhq.taxichallenge.ui.theme.Colors
import com.joohhq.taxichallenge.ui.theme.TextStyles

@Composable
fun PrimaryButton(enabled: Boolean = true, text: String, onClick: () -> Unit) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = CircleShape,
        colors = ButtonColors(
            containerColor = Colors.Primary,
            contentColor = Colors.White,
            disabledContainerColor = Colors.ButtonBg,
            disabledContentColor = Colors.Gray1
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = text, style = TextStyles.Button)
        }
    }
}
