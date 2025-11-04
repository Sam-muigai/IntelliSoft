package com.samkt.intellisoft.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.samkt.intellisoft.R

val geistMono = FontFamily(
    Font(R.font.geist_mono_regular, FontWeight.Normal),
    Font(R.font.geist_mono_black, FontWeight.Black),
    Font(R.font.geist_mono_semibold, FontWeight.SemiBold),
    Font(R.font.geist_mono_bold, FontWeight.Bold),
    Font(R.font.geist_mono_extra_bold, FontWeight.ExtraBold),
    Font(R.font.geist_mono_medium, FontWeight.Medium),
    Font(R.font.geist_mono_thin, FontWeight.Thin),
    Font(R.font.geist_mono_light, FontWeight.Light),
    Font(R.font.geist_mono_extra_light, FontWeight.ExtraLight),
)

val baseline = Typography()

val Typography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = geistMono),
    displayMedium = baseline.displayMedium.copy(fontFamily = geistMono),
    displaySmall = baseline.displaySmall.copy(fontFamily = geistMono),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = geistMono),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = geistMono),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = geistMono),
    titleLarge = baseline.titleLarge.copy(fontFamily = geistMono),
    titleMedium = baseline.titleMedium.copy(fontFamily = geistMono),
    titleSmall = baseline.titleSmall.copy(fontFamily = geistMono),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = geistMono),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = geistMono),
    bodySmall = baseline.bodySmall.copy(fontFamily = geistMono),
    labelLarge = baseline.labelLarge.copy(fontFamily = geistMono),
    labelMedium = baseline.labelMedium.copy(fontFamily = geistMono),
    labelSmall = baseline.labelSmall.copy(fontFamily = geistMono),
)
