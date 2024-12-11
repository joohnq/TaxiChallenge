package com.joohhq.taxichallenge.ui.presentation.travel_history

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.joohhq.taxichallenge.R
import com.joohhq.taxichallenge.koinActivityViewModel
import com.joohhq.taxichallenge.ui.components.DropDownWithLabel
import com.joohhq.taxichallenge.ui.components.EmptyBox
import com.joohhq.taxichallenge.ui.components.ErrorBox
import com.joohhq.taxichallenge.ui.components.LoadingBox
import com.joohhq.taxichallenge.ui.components.PrimaryButton
import com.joohhq.taxichallenge.ui.components.TextFieldWithLabel
import com.joohhq.taxichallenge.ui.components.TopBar
import com.joohhq.taxichallenge.ui.components.UserRideCard
import com.joohhq.taxichallenge.ui.components.VerticalSpacer
import com.joohhq.taxichallenge.ui.presentation.travel_history.event.TravelHistoryEvent
import com.joohhq.taxichallenge.ui.presentation.travel_history.state.TravelHistoryState
import com.joohhq.taxichallenge.ui.presentation.travel_request.TravelRequestScreen
import com.joohhq.taxichallenge.ui.state.UiState
import com.joohhq.taxichallenge.ui.state.UiState.Companion.fold
import com.joohhq.taxichallenge.ui.theme.Colors
import com.joohhq.taxichallenge.ui.theme.TextStyles

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun TravelHistoryScreenUI(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    state: TravelHistoryState,
    onEvent: (TravelHistoryEvent) -> Unit,
    onGoBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = Colors.BG1
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            TopBar(onGoBack)
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.travel_history),
                    style = TextStyles.H2Bold,
                    color = Colors.Black
                )
                VerticalSpacer(30.dp)
                TextFieldWithLabel(
                    text = stringResource(R.string.user_id),
                    placeholder = stringResource(R.string.user_id),
                    value = state.userId,
                    error = state.userIdError,
                    onValueChange = { onEvent(TravelHistoryEvent.OnUserIdChange(it)) },
                )
                VerticalSpacer(10.dp)
                DropDownWithLabel(
                    text = stringResource(R.string.driver_id),
                    value = state.driverId,
                    onClick = { onEvent(TravelHistoryEvent.OnDriverIdChange(it)) }
                )
                VerticalSpacer(30.dp)
                PrimaryButton(
                    enabled = state.userTravels !is UiState.Loading,
                    text = stringResource(R.string.search),
                    onClick = { onEvent(TravelHistoryEvent.OnSearch) }
                )
                VerticalSpacer(10.dp)
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    state.userTravels.fold(
                        onSuccess = { res ->
                            val rides = res.rides
                            if (rides.isEmpty()) {
                                item {
                                    EmptyBox()
                                }
                            } else {
                                items(res.rides) { ride ->
                                    UserRideCard(ride)
                                }
                            }
                        },
                        onLoading = {
                            item {
                                LoadingBox()
                            }
                        },
                        onError = { error ->
                            item {
                                ErrorBox(error)
                            }
                        }
                    )
                }
            }
        }
    }
}

class TravelHistoryScreen : Screen {
    @Composable override fun Content() {
        val travelHistoryViewModel: TravelHistoryViewModel = koinActivityViewModel()
        val snackBarHostState = remember { SnackbarHostState() }
        val state by travelHistoryViewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        DisposableEffect(Unit) {
            onDispose {
                travelHistoryViewModel.onEvent(TravelHistoryEvent.ResetState)
            }
        }

        TravelHistoryScreenUI(
            snackBarHostState = snackBarHostState,
            state = state,
            onEvent = travelHistoryViewModel::onEvent,
            onGoBack = { navigator.popUntil { it == TravelRequestScreen() } }
        )
    }
}