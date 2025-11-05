package com.prmto.snozeloo.presentation.alarm.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prmto.snozeloo.domain.model.DayValue
import com.prmto.snozeloo.presentation.components.DayChip
import com.prmto.snozeloo.presentation.theme.SnozelooTheme
import kotlin.random.Random

@Composable
fun DetailItemSection(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(16.dp),
    ) {
        content()
    }
}

@Composable
fun DetailRowItemSection(
    title: String,
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    DetailItemSection {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )

            content()
        }
    }
}

@Composable
fun DetailColumnItemSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    DetailItemSection {
        Column(
            modifier = modifier.fillMaxWidth(),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            content()
        }
    }
}

@Preview
@Composable
private fun DetailRowItemSectionPreview() {
    SnozelooTheme {
        DetailRowItemSection(
            title = "Alarm ringtone"
        ) {
            Text("Default")
        }
    }
}

@Preview
@Composable
private fun DetailColumnItemSectionPreview() {
    SnozelooTheme {
        DetailColumnItemSection(
            title = "Repeat"
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                DayValue.entries.forEach { dayValue ->
                    DayChip(
                        dayValue = dayValue,
                        isSelected = Random.nextBoolean(),
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .clickable {}
                    )
                }
            }
        }
    }
}