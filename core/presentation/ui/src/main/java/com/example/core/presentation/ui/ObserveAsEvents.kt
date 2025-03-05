package com.example.core.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    // ChatGPT: LocalLifecycleOwner.current: Provides the current lifecycle owner of the
    // composable's context. This is typically the Activity or Fragment hosting the Compose UI.
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner.lifecycle, key1, key2) {
        // repeatOnLifecycle launches the block in a new coroutine every time the
        // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // ChatGPT: Dispatchers.Main.immediate: Ensures that the collect operation runs on the
            // main thread immediately without dispatching if already on the main thread.
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }

}