package com.prmto.snozeloo.presentation.theme

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val blue = Color(0xff4664ff)
val surface = Color(0xfff6f6f6)
val textSecondary = Color(0xff858585)

val blue50 = Color(0xFFBCC6FF) // For unchecked switch colors
val blue100 = Color(0xFFECEFFF) // For uncheck chips

val Gray = Color(0xFF858585)

val Gray100 = Color(0xFFE6E6E6)

@Composable
fun buttonColors() =
    ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = Gray100,
        disabledContentColor = Color.White
    )