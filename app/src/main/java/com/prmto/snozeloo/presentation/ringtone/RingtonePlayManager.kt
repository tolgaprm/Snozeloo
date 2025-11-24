package com.prmto.snozeloo.presentation.ringtone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.prmto.snozeloo.presentation.ringtone.playmanager.RingtonePlayState
import com.prmto.snozeloo.presentation.ringtone.playmanager.RingtoneMediaPlayer

@Composable
fun RingtonePlayManager(
    ringtonePlayState: RingtonePlayState
) {
    val context = LocalContext.current
    val player = remember { RingtoneMediaPlayer() }

    DisposableEffect(ringtonePlayState.ringtoneUri, ringtonePlayState.volume) {
        val ringtoneUri = ringtonePlayState.ringtoneUri
        val volume = ringtonePlayState.volume

        if (!ringtoneUri.isNullOrEmpty()) {
            player.play(
                context = context,
                ringtoneUri = ringtoneUri,
                volume = volume,
                loop = true
            )
        } else {
            player.release()
        }

        onDispose {
            player.release()
        }
    }
}