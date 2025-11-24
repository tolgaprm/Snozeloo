package com.prmto.snozeloo.presentation.alarm.ringtone

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prmto.snozeloo.R
import com.prmto.snozeloo.domain.model.Ringtone
import com.prmto.snozeloo.presentation.theme.SnozelooTheme
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RingtoneScreen(
    state: RingtoneState,
    onAction: (RingtoneAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.ringtone_settings)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(RingtoneAction.OnNavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(state.ringtones, key = { it.id }) {
                    RingtoneItem(
                        ringtone = it,
                        isSelected = it.uri == state.selectedRingtoneUri,
                        onSelect = { onAction(RingtoneAction.OnSelectRingtone(it)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RingtoneItem(
    ringtone: Ringtone,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onSelect)
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_notification),
                contentDescription = ringtone.name,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = ringtone.name)
        }
        if (isSelected) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "Selected")
        }
    }
}

@Preview
@Composable
private fun RingtoneScreenPreview() {
    SnozelooTheme {
        RingtoneScreen(
            state = RingtoneState(
                ringtones = persistentListOf(
                    Ringtone(id = "",name = "Slient", uri = "uri1"),
                    Ringtone(id = "",name = "Ringtone 2", uri = "uri2")
                ),
                selectedRingtoneUri = "uri2"
            )
        ) { }
    }
}
