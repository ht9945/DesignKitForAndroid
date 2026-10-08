package com.ht9945.designkit.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.ht9945.designkit.theme.DK
import com.ht9945.designkit.theme.DKShapes
import com.ht9945.designkit.theme.DKType

@Composable
fun DKTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    singleLine: Boolean = true,
    isError: Boolean = false,
) {
    val c = DK.colors
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()
    val border by animateColorAsState(
        when {
            isError -> c.danger
            focused -> c.accentEnd
            else -> c.surfaceBorder
        },
        label = "fieldBorder",
    )
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        singleLine = singleLine,
        interactionSource = interaction,
        textStyle = DKType.body.copy(color = c.textPrimary),
        cursorBrush = SolidColor(c.accentEnd),
        decorationBox = { inner ->
            Row(
                Modifier
                    .height(52.dp)
                    .clip(DKShapes.field)
                    .background(c.surface)
                    .border(if (focused || isError) 1.5.dp else 1.dp, border, DKShapes.field)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (leadingIcon != null) {
                    Icon(leadingIcon, null, tint = c.textSecondary, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(10.dp))
                }
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) Text(placeholder, style = DKType.body, color = c.textSecondary)
                    inner()
                }
            }
        },
    )
}

@Composable
fun DKSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = DK.colors
    val thumbX by animateDpAsState(if (checked) 22.dp else 2.dp, label = "thumb")
    val trackBrush: Brush = if (checked) c.accentBrush else SolidColor(c.track)
    Box(
        modifier
            .width(52.dp)
            .height(32.dp)
            .clip(CircleShape)
            .background(trackBrush)
            .toggleable(value = checked, role = Role.Switch, onValueChange = onCheckedChange),
    ) {
        Box(
            Modifier
                .offset(x = thumbX, y = 2.dp)
                .size(28.dp)
                .shadow(2.dp, CircleShape)
                .background(Color.White, CircleShape),
        )
    }
}

@Composable
fun DKSegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = DK.colors
    val outer = RoundedCornerShape(14.dp)
    val inner = RoundedCornerShape(11.dp)
    BoxWithConstraints(
        modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(outer)
            .background(c.track)
            .padding(3.dp),
    ) {
        val segment = maxWidth / options.size
        val x by animateDpAsState(segment * selectedIndex, label = "segment")
        Box(
            Modifier
                .offset(x = x)
                .width(segment)
                .fillMaxHeight()
                .clip(inner)
                .background(c.surfaceStrong),
        )
        Row(Modifier.fillMaxSize()) {
            options.forEachIndexed { i, option ->
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(inner)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            role = Role.Tab,
                            onClick = { onSelected(i) },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        option,
                        style = DKType.label,
                        color = if (i == selectedIndex) c.accentEnd else c.textSecondary,
                    )
                }
            }
        }
    }
}

/** Alışkanlık tamamlama için yuvarlak onay kutusu. */
@Composable
fun DKCheckCircle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = DK.colors
    val fill: Brush = if (checked) c.accentBrush else SolidColor(Color.Transparent)
    Box(
        modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(fill)
            .then(if (checked) Modifier else Modifier.border(BorderStroke(2.dp, c.textSecondary.copy(alpha = 0.5f)), CircleShape))
            .toggleable(value = checked, role = Role.Checkbox, onValueChange = onCheckedChange),
        contentAlignment = Alignment.Center,
    ) {
        if (checked) Icon(Icons.Rounded.Check, null, tint = c.onAccent, modifier = Modifier.size(20.dp))
    }
}
