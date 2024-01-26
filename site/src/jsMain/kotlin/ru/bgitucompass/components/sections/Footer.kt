package ru.bgitucompass.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.toAttrs
import com.varabyte.kobweb.silk.components.style.toModifier
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import ru.bgitucompass.BodyTextStyle
import ru.bgitucompass.FooterLinkTextStyle
import ru.bgitucompass.SitePalettes

@Composable
fun Footer() {
    SimpleGrid(
        numColumns = numColumns(base = 1, md = 1),
        modifier = Modifier
            .fillMaxWidth()
            .background(SitePalettes.light.backgroundVariant)
            .padding(32.px)
            .minHeight(150.px)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            P(
                BodyTextStyle.toModifier().fontWeight(FontWeight.SemiBold).toAttrs()
            ) {
                Text("Связь с разработчиками")
            }
            A(
                attrs = FooterLinkTextStyle
                    .toAttrs(),
                href = "https://t.me/koespe"
            ) {
                Text("Пудов Кирилл")
                Span(BodyTextStyle.toAttrs()) {
                    Text(" — Cерверная часть и Telegram-бот")
                }
            }
            A(
                attrs = FooterLinkTextStyle.toAttrs(),
                href = "https://t.me/Injent"
            ) {
                Text("Веревкин Елисей")
                Span(BodyTextStyle.toAttrs()) {
                    Text(" — Android приложение")
                }
            }
        }
    }
}