package ru.bgitucompass

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint

@Composable
fun CustomSimpleGrid(
    modifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier,
    firstContent: @Composable () -> Unit,
    secondContent: @Composable () -> Unit,
    thirdContent: @Composable (() -> Unit)? = null
) {
    val breakpoint = rememberBreakpoint()

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val modifierSize = itemModifier
            .fillMaxSize()
            .scrollBehavior(ScrollBehavior.Smooth)

        if (breakpoint >= Breakpoint.MD) {
            Row(
                modifierSize,
            ) {
                val a = Modifier.flex(1).fillMaxSize()
                Column(a) {
                    firstContent()
                }
                Column(a) {
                    secondContent()
                }
                thirdContent?.let {
                    Column(a) {
                        it()
                    }
                }
            }
        } else {
            Column(modifierSize) {
                firstContent()
                secondContent()
                thirdContent?.let {
                    it()
                }
            }
        }
    }
}