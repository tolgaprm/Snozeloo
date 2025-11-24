package com.prmto.snozeloo.presentation.ringtone.playmanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Stable
interface RingtonePlayState {

    val ringtoneUri: String?

    val volume: Float

    fun updateRingtoneUri(ringtoneUri: String?)

    fun updateVolume(volume: Float)
}

internal class RingtonePlayStateImpl(
    private val initialRingtoneUri: String?,
    private val initialVolume: Float
) : RingtonePlayState {

    override val ringtoneUri: String? get() = _ringtoneUri
    override val volume: Float get() = _volume

    private var _ringtoneUri by mutableStateOf(initialRingtoneUri)
    private var _volume by mutableFloatStateOf(initialVolume)

    override fun updateRingtoneUri(ringtoneUri: String?) {
        _ringtoneUri = ringtoneUri
    }

    override fun updateVolume(volume: Float) {
        _volume = volume
    }
}

@Composable
fun rememberRingtonePlayManager(ringtoneUri: String? = null, volume: Float = 0.5f): RingtonePlayState {
    val state = remember {
        RingtonePlayStateImpl(
            initialRingtoneUri = ringtoneUri,
            initialVolume = volume
        )
    }
    return state
}