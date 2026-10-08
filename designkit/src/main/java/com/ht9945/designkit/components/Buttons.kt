package com.ht9945.designkit.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ht9945.designkit.theme.DK
import com.ht9945.designkit.theme.DKShapes
import com.ht9945.designkit.theme.DKType

enum class DKButtonStyle { Primary, Secondary, Ghost, Danger }

enum class DKButtonSize(val height: Dp, val horizontalPadding: Dp) {
    Small(36.dp, 14.dp),
    Medium(48.dp, 20.dp),
    Large(56.dp, 24.dp),
}

@Composable
fun DKButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: DKButtonStyle = DKButtonStyle.Primary,
    size: DKButtonSize = DKButtonSize.Medium,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
) {
    val c = DK.colors
    val shape = DKShapes.button
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) 0.97f else 1f, label = "press")

    val contentColor = when (style) {
        DKButtonStyle.Primary -> c.onAccent
        DKButtonStyle.Secondary, DKButtonStyle.Ghost -> c.accentEnd
        DKButtonStyle.Danger -> Color.White
    }
    val surface = when (style) {
        DKButtonStyle.Primary -> Modifier.background(c.accentBrush, shape)
        DKButtonStyle.Secondary -> Modifier.background(c.surfaceStrong, shape).border(1.dp, c.surfaceBorder, shape)
        DKButtonStyle.Ghost -> Modifier
        DKButtonStyle.Danger -> Modifier.background(c.danger, shape)
    }

    Row(
        modifier = modifier
            .height(size.height)
            .graphicsLayer { scaleX = scale; scaleY = scale; alpha = if (enabled) 1f else 0.45f }
            .clip(shape)
            .then(surface)
            .clickable(
                interactionSource = interaction,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = size.horizontalPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leadingIcon != null) {
            Icon(leadingIcon, contentDescription = null, tint = contentColor, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, style = DKType.headline, color = contentColor, maxLines = 1)
    }
}

/** Yuvarlak, cam stilinde ikon butonu. */
@Composable
fun DKIconButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    filled: Boolean = false,
) {
    val c = DK.colors
    val bg = if (filled) Modifier.background(c.accentBrush, CircleShape)
    else Modifier.background(c.surfaceStrong, CircleShape).border(1.dp, c.surfaceBorder, CircleShape)
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .then(bg)
            .clickable(role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription, tint = if (filled) c.onAccent else c.accentEnd, modifier = Modifier.size(22.dp))
    }
}
