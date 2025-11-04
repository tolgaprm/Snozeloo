package com.prmto.snozeloo.core.util

import androidx.compose.runtime.Composable

@Composable
fun <T> ShowSectionsWithEmptyState(
    data: List<T>?,
    onEmptyState: @Composable () -> Unit,
    onNotEmptyState: @Composable (data: List<T>) -> Unit
) {
    if (data.isNullOrEmpty()) {
        onEmptyState()
    } else {
        onNotEmptyState(data)
    }
}
