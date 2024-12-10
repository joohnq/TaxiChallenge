package com.joohhq.taxichallenge.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.joohhq.taxichallenge.R

object FontFamily {
    val openSans = FontFamily(
        Font(R.font.open_sans_bold, FontWeight.Bold),
        Font(R.font.open_sans_semi_bold, FontWeight.SemiBold),
        Font(R.font.open_sans_medium, FontWeight.Medium),
        Font(R.font.open_sans_regular, FontWeight.Normal),
        Font(R.font.open_sans_light, FontWeight.Light),
    )
}