package com.ht9945.designkit.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ht9945.designkit.components.*
import com.ht9945.designkit.theme.DKTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DKTheme {
                DKBackground {
                    var text by remember { mutableStateOf("") }
                    var on by remember { mutableStateOf(true) }
                    var seg by remember { mutableIntStateOf(0) }
                    Column(
                        Modifier.statusBarsPadding().verticalScroll(rememberScrollState()).padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        DKButton("Birincil", onClick = {}, modifier = Modifier.fillMaxWidth())
                        DKButton("İkincil", onClick = {}, style = DKButtonStyle.Secondary, modifier = Modifier.fillMaxWidth())
                        DKTextField(text, { text = it }, placeholder = "Metin alanı")
                        DKSegmentedControl(listOf("Hafta", "Ay"), seg, { seg = it })
                        DKSwitch(on, { on = it })
                        DKGlassCard(Modifier.fillMaxWidth()) { DKBadge("Rozet") }
                        DKProgressRing(0.7f)
                    }
                }
            }
        }
    }
}