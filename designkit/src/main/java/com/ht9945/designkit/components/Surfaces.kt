package com.ht9945.designkit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ht9945.designkit.theme.DK
import com.ht9945.designkit.theme.DKShapes
import com.ht9945.designkit.theme.DKSpacing

/** Ekran zemini: soluk mavi-gri dikey gradyan. */
@Composable
fun DKBackground(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(modifier.fillMaxSize().background(DK.colors.backgroundBrush), content = content)
}

/** Cam efektli kart: yarı saydam dolgu + ince açık kenarlık. */
@Composable
fun DKGlassCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(DKSpacing.lg),
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = DKShapes.card
    var m = modifier
        .clip(shape)
        .background(DK.colors.surface)
        .border(1.dp, DK.colors.surfaceBorder, shape)
    if (onClick != null) m = m.clickable(onClick = onClick)
    Column(m.padding(contentPadding), content = content)
}
