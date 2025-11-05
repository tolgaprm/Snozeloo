package com.prmto.snozeloo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.prmto.snozeloo.presentation.theme.AlarmItemShape
import com.prmto.snozeloo.presentation.theme.SnozelooTheme

@Composable
fun SnoozelooEditText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Number,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        modifier = modifier
            .width(128.dp)
            .height(95.dp)
            .clip(AlarmItemShape)
            .background(MaterialTheme.colorScheme.surface),
        textStyle = MaterialTheme.typography.displayMedium.copy(
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                innerTextField()
            }
        },
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
    )
}

@Preview(showBackground = true)
@Composable
fun SnoozelooEditTextPreview() {
    SnozelooTheme {
        SnoozelooEditText(
            value = "45",
            onValueChange = {}
        )
    }
}
