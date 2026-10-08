package com.ht9945.designkit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Tasarım token'ları. Projeye özel renk değiştirmek için sadece
 * LightDKColors / DarkDKColors içindeki değerleri düzenle.
 * Cam estetiği: soluk mavi-gri zemin, yarı saydam yüzeyler, mavi gradyan vurgu.
 */
@Immutable
data class DKColors(
    val backgroundTop: Color,
    val backgroundBottom: Color,
    val surface: Color,
    val surfaceStrong: Color,
    val surfaceBorder: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val accentStart: Color,
    val accentEnd: Color,
    val onAccent: Color,
    val success: Color,
    val warning: Color,
    val danger: Color,
    val track: Color,
) {
    val accentBrush: Brush get() = Brush.linearGradient(listOf(accentStart, accentEnd))
    val backgroundBrush: Brush get() = Brush.verticalGradient(listOf(backgroundTop, backgroundBottom))
}

val LightDKColors = DKColors(
    backgroundTop = Color(0xFFEFF4FB),
    backgroundBottom = Color(0xFFD8E3F2),
    surface = Color(0x9EFFFFFF),
    surfaceStrong = Color(0xE6FFFFFF),
    surfaceBorder = Color(0xCCFFFFFF),
    textPrimary = Color(0xFF16202E),
    textSecondary = Color(0xFF5E6B7E),
    accentStart = Color(0xFF5B9BFF),
    accentEnd = Color(0xFF2F6BFF),
    onAccent = Color.White,
    success = Color(0xFF18B26B),
    warning = Color(0xFFFF9F1C),
    danger = Color(0xFFFF5A5F),
    track = Color(0x1F2F6BFF),
)

val DarkDKColors = DKColors(
    backgroundTop = Color(0xFF0F1626),
    backgroundBottom = Color(0xFF1A2540),
    surface = Color(0x1FFFFFFF),
    surfaceStrong = Color(0x38FFFFFF),
    surfaceBorder = Color(0x33FFFFFF),
    textPrimary = Color(0xFFF2F6FC),
    textSecondary = Color(0xFF9AA7BC),
    accentStart = Color(0xFF6FA8FF),
    accentEnd = Color(0xFF3D78FF),
    onAccent = Color.White,
    success = Color(0xFF2ED48A),
    warning = Color(0xFFFFB347),
    danger = Color(0xFFFF6B70),
    track = Color(0x33FFFFFF),
)

object DKSpacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 24.dp
    val xxl = 32.dp
}

object DKShapes {
    val card = RoundedCornerShape(24.dp)
    val button = RoundedCornerShape(16.dp)
    val field = RoundedCornerShape(16.dp)
    val chip = CircleShape
}

object DKType {
    val largeTitle = TextStyle(fontSize = 32.sp, lineHeight = 38.sp, fontWeight = FontWeight.Bold)
    val title = TextStyle(fontSize = 22.sp, lineHeight = 28.sp, fontWeight = FontWeight.SemiBold)
    val headline = TextStyle(fontSize = 17.sp, lineHeight = 22.sp, fontWeight = FontWeight.SemiBold)
    val body = TextStyle(fontSize = 16.sp, lineHeight = 22.sp, fontWeight = FontWeight.Normal)
    val label = TextStyle(fontSize = 14.sp, lineHeight = 18.sp, fontWeight = FontWeight.Medium)
    val caption = TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Medium)
}

private val LocalDKColors = staticCompositionLocalOf { LightDKColors }

object DK {
    val colors: DKColors
        @Composable @ReadOnlyComposable get() = LocalDKColors.current
}

@Composable
fun DKTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: DKColors = if (darkTheme) DarkDKColors else LightDKColors,
    content: @Composable () -> Unit,
) {
    val material = if (darkTheme) {
        darkColorScheme(primary = colors.accentEnd, background = colors.backgroundTop, onBackground = colors.textPrimary)
    } else {
        lightColorScheme(primary = colors.accentEnd, background = colors.backgroundTop, onBackground = colors.textPrimary)
    }
    CompositionLocalProvider(
        LocalDKColors provides colors,
        LocalContentColor provides colors.textPrimary,
    ) {
        MaterialTheme(colorScheme = material, content = content)
    }
}
