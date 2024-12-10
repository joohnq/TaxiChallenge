package com.joohhq.taxichallenge.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joohhq.taxichallenge.ui.theme.TextStyles

@Composable
fun SnackBar(
    text: String,
    message: String,
    onClick: () -> Unit,
) {
    Snackbar(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 15.dp),
        action = {
            TextButton(
                onClick = onClick,
            ) {
                Text(text = text, style = TextStyles.BodyBold)
            }
        }
    ) {
        Text(text = message, style = TextStyles.BodyMedium)
    }
}