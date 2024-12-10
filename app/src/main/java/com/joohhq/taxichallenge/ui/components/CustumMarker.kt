package com.joohhq.taxichallenge.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.joohhq.taxichallenge.R
import com.joohhq.taxichallenge.ui.theme.Colors
import com.joohhq.taxichallenge.ui.theme.TextStyles

@SuppressLint("UnrememberedMutableState")
@Composable
fun LocationMarker(
    marker: LatLng,
    i: Int
) {
    MarkerComposable(
        state = MarkerState(
            position = marker
        )
    ) {
        Box(
            modifier = Modifier
                .paint(
                    painter = painterResource(R.drawable.ic_pin),
                    alignment = Alignment.Center,
                    colorFilter = ColorFilter.tint(color = Colors.Primary),
                    contentScale = ContentScale.FillWidth,
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (i == 0) "A" else "B",
                style = TextStyles.BodyBold,
                color = Colors.White,
                modifier = Modifier
                    .aspectRatio(1f / 1f)
                    .background(color = Colors.Primary, shape = CircleShape)
                    .padding(5.dp)
            )
        }
    }
}