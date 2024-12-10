package com.joohhq.taxichallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.joohhq.taxichallenge.R
import com.joohhq.taxichallenge.ui.theme.Colors

@Composable
fun TopBar(
    onGoBack: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        IconButton(
            onClick = onGoBack,
        ) {
            Icon(
                modifier = Modifier.size(24.dp).rotate(-45f),
                painter = painterResource(R.drawable.ic_arrow),
                contentDescription = "Go Back",
                tint = Colors.Black
            )
        }
    }
}