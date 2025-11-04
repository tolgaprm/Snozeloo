package com.prmto.snozeloo.presentation.alarm.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.prmto.snozeloo.domain.model.DayValue
import com.prmto.snozeloo.presentation.theme.SnozelooTheme
import com.prmto.snozeloo.presentation.theme.blue100

@Composable
fun DayChip(
    dayValue: DayValue,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {

    FilterChip(
        modifier = modifier,
        label = { Text(text = dayValue.getDayName() ) },
        selected = isSelected,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = blue100,
            labelColor = MaterialTheme.colorScheme.onSurface
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            selectedBorderColor = MaterialTheme.colorScheme.primary,
            borderColor = blue100
        ),
        onClick = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun DayChipPreview() {
    SnozelooTheme {
        DayChip(
            dayValue = DayValue.MONDAY,
            isSelected = true
        )
    }
}