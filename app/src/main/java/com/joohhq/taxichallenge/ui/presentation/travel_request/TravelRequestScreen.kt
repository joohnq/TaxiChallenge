package com.joohhq.taxichallenge.ui.presentation.travel_request

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.joohhq.taxichallenge.R
import com.joohhq.taxichallenge.koinActivityViewModel
import com.joohhq.taxichallenge.ui.components.PrimaryButton
import com.joohhq.taxichallenge.ui.components.TextFieldWithLabel
import com.joohhq.taxichallenge.ui.components.VerticalSpacer
import com.joohhq.taxichallenge.ui.presentation.travel_detail.TravelDetailScreen
import com.joohhq.taxichallenge.ui.presentation.travel_request.event.TravelRequestEvent
import com.joohhq.taxichallenge.ui.presentation.travel_request.state.TravelRequestState
import com.joohhq.taxichallenge.ui.state.UiState
import com.joohhq.taxichallenge.ui.state.UiState.Companion.fold
import com.joohhq.taxichallenge.ui.state.UiState.Companion.onLoading
import com.joohhq.taxichallenge.ui.theme.Colors
import com.joohhq.taxichallenge.ui.theme.TextStyles
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TravelRequestScreenUI(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    state: TravelRequestState,
    onEvent: (TravelRequestEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = Colors.BG1
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
        ) {
            Text(
                text = stringResource(R.string.travel_request),
                style = TextStyles.H2Bold,
                color = Colors.Black
            )
            VerticalSpacer(70.dp)
            TextFieldWithLabel(
                text = stringResource(R.string.user_id),
                placeholder = stringResource(R.string.user_id),
                value = state.userId,
                error = state.userIdError,
                onValueChange = { onEvent(TravelRequestEvent.OnUserIdChange(it)) },
            )
            VerticalSpacer(30.dp)
            TextFieldWithLabel(
                text = stringResource(R.string.origin_address),
                placeholder = stringResource(R.string.enter_origin_address),
                value = state.origin,
                error = state.originError,
                onValueChange = { onEvent(TravelRequestEvent.OnOriginChange(it)) },
            )
            VerticalSpacer(30.dp)
            TextFieldWithLabel(
                text = stringResource(R.string.destination_address),
                placeholder = stringResource(R.string.enter_destination_address),
                value = state.destination,
                error = state.destinationError,
                onValueChange = { onEvent(TravelRequestEvent.OnDestinationChange(it)) },
            )
            VerticalSpacer(30.dp)
            PrimaryButton(
                enabled = state.rideEstimate !is UiState.Loading,
                text = stringResource(R.string.estimate_the_price),
                onClick = { onEvent(TravelRequestEvent.OnEstimate) }
            )
            VerticalSpacer(30.dp)
            state.rideEstimate.onLoading {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = Colors.Primary
                )
            }
        }
    }
}

class TravelRequestScreen : Screen {
    @Composable override fun Content() {
        val travelViewModel: TravelViewModel = koinActivityViewModel()
        val scope = rememberCoroutineScope()
        val snackBarHostState = remember { SnackbarHostState() }
        val state by travelViewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            travelViewModel.onEvent(TravelRequestEvent.OnUserIdChange("joao"))
            travelViewModel.onEvent(TravelRequestEvent.OnOriginChange("Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031"))
            travelViewModel.onEvent(TravelRequestEvent.OnDestinationChange("Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200"))
        }

        LaunchedEffect(state.rideEstimate) {
            state.rideEstimate.fold(
                onError = { error ->
                    scope.launch {
                        snackBarHostState.showSnackbar(error)
                    }
                },
                onSuccess = { navigator.push(TravelDetailScreen()) },
            )
        }

        TravelRequestScreenUI(
            snackBarHostState = snackBarHostState,
            state = state,
            onEvent = travelViewModel::onEvent,
        )
    }
}