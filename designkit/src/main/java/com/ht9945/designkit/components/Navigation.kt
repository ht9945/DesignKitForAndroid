package com.ht9945.designkit.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.ht9945.designkit.theme.DK
import com.ht9945.designkit.theme.DKType

data class DKNavItem(val label: String, val icon: ImageVector)

/** Alta yüzen cam stilinde tab bar. */
@Composable
fun DKBottomBar(
    items: List<DKNavItem>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = DK.colors
    val shape = RoundedCornerShape(28.dp)
    Row(
        modifier
            .fillMaxWidth()
            .clip(shape)
            .background(c.surfaceStrong)
            .border(1.dp, c.surfaceBorder, shape)
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        items.forEachIndexed { i, item ->
            val selected = i == selectedIndex
            val tint by animateColorAsState(if (selected) c.accentEnd else c.textSecondary, label = "navTint")
            Column(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(22.dp))
                    .background(if (selected) c.track else androidx.compose.ui.graphics.Color.Transparent)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        role = Role.Tab,
                        onClick = { onSelected(i) },
                    )
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(item.icon, contentDescription = item.label, tint = tint, modifier = Modifier.size(24.dp))
                Spacer(Modifier.height(2.dp))
                Text(item.label, style = DKType.caption, color = tint, maxLines = 1)
            }
        }
    }
}

/** Kompakt hap şeklinde sekmeler (filtre / kategori seçimi için). */
@Composable
fun DKTabs(
    tabs: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = DK.colors
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        tabs.forEachIndexed { i, tab ->
            val selected = i == selectedIndex
            Text(
                tab,
                style = DKType.label,
                color = if (selected) c.onAccent else c.textSecondary,
                modifier = Modifier
                    .clip(CircleShape)
                    .then(
                        if (selected) Modifier.background(c.accentBrush, CircleShape)
                        else Modifier.background(c.surface, CircleShape).border(1.dp, c.surfaceBorder, CircleShape)
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        role = Role.Tab,
                        onClick = { onSelected(i) },
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
    }
}
