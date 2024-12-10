package com.joohhq.taxichallenge.ui.presentation.no_internet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.joohhq.taxichallenge.R
import com.joohhq.taxichallenge.ui.components.PrimaryButton
import com.joohhq.taxichallenge.ui.presentation.travel_request.TravelRequestScreen
import com.joohhq.taxichallenge.ui.theme.Colors
import com.joohhq.taxichallenge.ui.theme.TextStyles
import com.joohhq.taxichallenge.utils.ConnectivityManager

class NoInternetScreen : Screen {
    @Composable override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        fun verifyNetwork() {
            val hasInternet = ConnectivityManager.isInternetAvailable(context)
            if (!hasInternet) return
            navigator.replaceAll(TravelRequestScreen())
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(
                20.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.no_internet_connection),
                style = TextStyles.H2Bold,
                color = Colors.Black
            )
            PrimaryButton(
                text = stringResource(R.string.try_again),
                onClick = ::verifyNetwork
            )
        }
    }
}