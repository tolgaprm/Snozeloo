package com.prmto.snozeloo.presentation.alarm.ringtone.playmanager

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.core.net.toUri

class RingtoneMediaPlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun play(
        context: Context,
        ringtoneUri: String?,
        volume: Float? = 0.5f,
        loop: Boolean = true
    ) {
        if (ringtoneUri == null || volume == null) return
        release()
        runCatching {
            val mp = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setDataSource(context, ringtoneUri.toUri())
                setOnPreparedListener { prepared ->
                    val v = volume.coerceIn(0f, 1f)
                    prepared.setVolume(v, v)
                    prepared.isLooping = loop
                    prepared.start()
                }
                setOnErrorListener { errPlayer, _, _ ->
                    runCatching {
                        if (errPlayer.isPlaying) errPlayer.stop()
                        errPlayer.reset()
                        errPlayer.release()
                    }
                    mediaPlayer = null
                    true
                }
                prepareAsync()
            }
            mediaPlayer = mp
        }.onFailure {
            mediaPlayer = null
        }
    }

    fun setVolume(volume: Float) {
        mediaPlayer?.let { mp ->
            val v = volume.coerceIn(0f, 1f)
            runCatching { mp.setVolume(v, v) }
        }
    }

    fun stop() {
        mediaPlayer?.let { mp ->
            runCatching {
                if (mp.isPlaying) mp.stop()
            }
        }
    }

    fun release() {
        mediaPlayer?.let { mp ->
            runCatching {
                if (mp.isPlaying) mp.stop()
                mp.reset()
                mp.release()
            }
        }
        mediaPlayer = null
    }
}
