package com.joohhq.taxichallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import cafe.adriel.voyager.navigator.Navigator
import com.joohhq.taxichallenge.ui.presentation.no_internet.NoInternetScreen
import com.joohhq.taxichallenge.ui.presentation.travel_request.TravelRequestScreen
import com.joohhq.taxichallenge.utils.ConnectivityManager
import org.koin.compose.KoinContext

class TaxiChallengeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinContext {
                val hasInternet = ConnectivityManager.isInternetAvailable(this)
                MaterialTheme {
                    Navigator(if (hasInternet) TravelRequestScreen() else NoInternetScreen())
                }
            }
        }
    }
}
