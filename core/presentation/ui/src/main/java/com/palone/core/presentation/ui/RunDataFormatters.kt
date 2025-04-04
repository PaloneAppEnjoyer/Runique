package com.palone.core.presentation.ui

import android.annotation.SuppressLint
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.time.Duration

@SuppressLint("DefaultLocale")
fun Duration.formatted(): String {
    val totalSeconds = inWholeSeconds
    val hours = String.format("%02d", totalSeconds / 3600)
    val minutes = String.format("%02d", totalSeconds % 3600 / 60)
    val seconds = String.format("%02d", totalSeconds % 60)
    return "$hours:$minutes:$seconds"
}

fun Double.toFormattedKilometers(): String {
    return "${this.roundToDecimals(1)} km"
}

@SuppressLint("DefaultLocale")
fun Duration.toFormattedPace(distanceKm: Double): String {
    if (this == Duration.ZERO || distanceKm <= 0.0) {
        return "-"
    }
    val secondsPerKilometer = (this.inWholeSeconds / distanceKm).roundToInt()
    val avgPaceMinutes = secondsPerKilometer / 60
    val avgPaceSeconds = String.format("%02d", secondsPerKilometer % 60)
    return "$avgPaceMinutes:$avgPaceSeconds /km"
}

private fun Double.roundToDecimals(decimalCount: Int): Double {
    val factor = 10f.pow(decimalCount)
    return round(this * factor) / factor
}