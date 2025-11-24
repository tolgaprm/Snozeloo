package com.prmto.snozeloo.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VibrationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createVibrationEffect(pattern: LongArray, repeat: Int): VibrationEffect {
        return VibrationEffect.createWaveform(pattern, repeat)
    }

    fun startVibration() {
        val pattern = longArrayOf(0, 1000, 1000) // 1 saniye titreşim, 1 saniye bekleme

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = createVibrationEffect(pattern, 0) // 0 = tekrarlama yok
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, 0)
        }
    }

    fun stopVibration() {
        vibrator.cancel()
    }
}
