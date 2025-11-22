package com.prmto.snozeloo.core.util

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList

@Composable
fun <T> ShowSectionsWithEmptyState(
    data: ImmutableList<T>?,
    onEmptyState: @Composable () -> Unit,
    onNotEmptyState: @Composable (data: ImmutableList<T>) -> Unit
) {
    if (data.isNullOrEmpty()) {
        onEmptyState()
    } else {
        onNotEmptyState(data)
    }
}
