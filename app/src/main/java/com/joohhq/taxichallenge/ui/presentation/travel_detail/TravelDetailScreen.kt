package com.joohhq.taxichallenge.ui.presentation.travel_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.joohhq.taxichallenge.R
import com.joohhq.taxichallenge.data.mapper.toLatLng
import com.joohhq.taxichallenge.entities.response.RideEstimateResponse
import com.joohhq.taxichallenge.koinActivityViewModel
import com.joohhq.taxichallenge.ui.components.DetailBottomSheet
import com.joohhq.taxichallenge.ui.components.LocationMarker
import com.joohhq.taxichallenge.ui.components.TopBar
import com.joohhq.taxichallenge.ui.presentation.travel_history.TravelHistoryScreen
import com.joohhq.taxichallenge.ui.presentation.travel_request.TravelViewModel
import com.joohhq.taxichallenge.ui.presentation.travel_request.event.TravelRequestEvent
import com.joohhq.taxichallenge.ui.state.UiState
import com.joohhq.taxichallenge.ui.state.UiState.Companion.fold
import com.joohhq.taxichallenge.ui.state.UiState.Companion.getValue
import com.joohhq.taxichallenge.ui.state.UiState.Companion.getValueComposable
import com.joohhq.taxichallenge.ui.theme.Colors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun TravelDetailScreenUI(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    markers: List<LatLng>,
    confirm: UiState<Boolean>,
    rideEstimate: RideEstimateResponse,
    cameraPositionState: CameraPositionState,
    bottomSheetState: BottomSheetScaffoldState,
    onGoBack: () -> Unit,
    onEvent: (TravelRequestEvent) -> Unit,
) {
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = Colors.BG1,
        scaffoldState = bottomSheetState,
        sheetPeekHeight = 100.dp,
        sheetContainerColor = Colors.BG1,
        sheetTonalElevation = 0.dp,
        sheetContent = {
            DetailBottomSheet(
                isLoading = confirm is UiState.Loading,
                options = rideEstimate.options,
                onClick = { option -> onEvent(TravelRequestEvent.OnConfirm(option)) }
            )
        }
    ) { _ ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
        ) {
            TopBar(onGoBack)
            GoogleMap(
                modifier = Modifier.weight(1f),
                cameraPositionState = cameraPositionState
            ) {
                markers.forEachIndexed { i, marker ->
                    LocationMarker(
                        marker = marker,
                        i = i
                    )
                }
            }
        }
    }
}

class TravelDetailScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable override fun Content() {
        val error = stringResource(R.string.travel_request_confirmed)
        val travelViewModel: TravelViewModel = koinActivityViewModel()
        val state by travelViewModel.state.collectAsState()
        val scope = rememberCoroutineScope()
        val rideEstimate = state.rideEstimate.getValue()
        val markers =
            listOf(
                rideEstimate.origin.toLatLng(),
                rideEstimate.destination.toLatLng()
            )

        val cameraPositionState = rememberCameraPositionState()
        val snackBarHostState = remember { SnackbarHostState() }
        val bottomSheetState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberStandardBottomSheetState(
                initialValue = SheetValue.PartiallyExpanded,
            ),
            snackbarHostState = snackBarHostState
        )
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(state.rideEstimate) {
            val bounds = LatLngBounds.builder()
            markers.forEach { bounds.include(it) }

            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds.build(), 300)
            cameraPositionState.animate(cameraUpdate)
        }

        LaunchedEffect(state.confirm) {
            state.confirm.fold(
                onError = { error ->
                    scope.launch {
                        snackBarHostState.showSnackbar(error)
                    }
                },
                onSuccess = {
                    scope.launch {
                        snackBarHostState.showSnackbar(error)
                    }
                    navigator.push(TravelHistoryScreen())
                }
            )
        }

        DisposableEffect(Unit) {
            onDispose {
                travelViewModel.onEvent(TravelRequestEvent.ResetState)
            }
        }

        TravelDetailScreenUI(
            snackBarHostState = snackBarHostState,
            cameraPositionState = cameraPositionState,
            markers = markers,
            confirm = state.confirm,
            rideEstimate = state.rideEstimate.getValueComposable(),
            bottomSheetState = bottomSheetState,
            onGoBack = navigator::pop,
            onEvent = travelViewModel::onEvent,
        )
    }
}