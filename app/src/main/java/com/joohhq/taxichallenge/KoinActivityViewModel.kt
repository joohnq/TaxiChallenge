package com.joohhq.taxichallenge

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import org.koin.compose.currentKoinScope
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.viewmodel.defaultExtras

@Composable
inline fun <reified T : ViewModel> koinActivityViewModel(
    qualifier: Qualifier? = null,
    key: String? = null,
    extras: CreationExtras = defaultExtras(LocalContext.current as ComponentActivity),
    scope: Scope = currentKoinScope(),
    noinline parameters: ParametersDefinition? = null,
) = koinViewModel<T>(
    qualifier = qualifier,
    viewModelStoreOwner = LocalContext.current as ComponentActivity,
    key = key,
    extras = extras,
    scope = scope,
    parameters = parameters,
)
