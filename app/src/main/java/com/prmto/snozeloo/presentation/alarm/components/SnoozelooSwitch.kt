package com.prmto.snozeloo.presentation.alarm.components

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.prmto.snozeloo.presentation.theme.blue50

@Composable
fun SnoozelooSwitch(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
) {
    Switch(
        checked = checked,
        colors = SwitchDefaults.colors(
            uncheckedTrackColor = blue50,
            uncheckedThumbColor = Color.White,
            uncheckedBorderColor = blue50
        ),
        onCheckedChange = onCheckedChange
    )
}