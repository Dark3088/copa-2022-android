package me.dio.copa.catar.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
//  Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)

val Roboto = FontFamily.Default
object MatchTextStyle {
    val titleMedium = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.Medium,
        letterSpacing   = 0.15000000596046448.sp,
        lineHeight      = 24.sp,
        fontSize        = 16.sp
    )

   val titleSmall = TextStyle(
    fontFamily      = Roboto,
    fontWeight      = FontWeight.Medium,
    letterSpacing   = 0.10000000149011612.sp,
    lineHeight      = 5.sp,
    fontSize        = 14.sp
    )
}

/*

val Typography = Typography(
    labelLarge = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.Medium,
        letterSpacing   = 0.10000000149011612.sp,
        lineHeight      = 20.sp,
        fontSize        = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.Medium,
        letterSpacing   = 0.5.sp,
        lineHeight      = 16.sp,
        fontSize        = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.Medium,
        letterSpacing   = 0.5.sp,
        lineHeight      = 16.sp,
        fontSize        = 11.sp
    ),
    bodyLarge = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.W400,
        letterSpacing   = 0.5.sp,
        lineHeight      = 24.sp,
        fontSize        = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.W400,
        letterSpacing   = 0.25.sp,
        lineHeight      = 20.sp,
        fontSize        = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.W400,
        letterSpacing   = 0.4000000059604645.sp,
        lineHeight      = 16.sp,
        fontSize        = 12.sp
    ),
    headlineLarge = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.W400,
        letterSpacing   = 0.sp,
        lineHeight      = 40.sp,
        fontSize        = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.W400,
        letterSpacing   = 0.sp,
        lineHeight      = 36.sp,
        fontSize        = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.W400,
        letterSpacing   = 0.sp,
        lineHeight      = 32.sp,
        fontSize        = 24.sp
    ),
    displayLarge = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.W400,
        letterSpacing   = (-0.25).sp,
        lineHeight      = 64.sp,
        fontSize        = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.W400,
        letterSpacing   = 0.sp,
        lineHeight      = 52.sp,
        fontSize        = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.W400,
        letterSpacing   = 0.sp,
        lineHeight      = 44.sp,
        fontSize        = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.W400,
        letterSpacing   = 0.sp,
        lineHeight      = 28.sp,
        fontSize        = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.Medium,
        letterSpacing   = 0.15000000596046448.sp,
        lineHeight      = 24.sp,
        fontSize        = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily      = Roboto,
        fontWeight      = FontWeight.Medium,
        letterSpacing   = 0.10000000149011612.sp,
        lineHeight      = 20.sp,
        fontSize        = 14.sp
    ),
)
 */