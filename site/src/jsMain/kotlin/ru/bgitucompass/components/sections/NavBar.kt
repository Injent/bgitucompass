package ru.bgitucompass.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import ru.bgitucompass.theme.Assets
import ru.bgitucompass.theme.Dimens.NAVBAR_HEIGHT
import ru.bgitucompass.theme.Fonts
import ru.bgitucompass.toSitePalette

@Composable
fun NavBar(modifier: Modifier = Modifier) {
    val colors = ColorMode.current.toSitePalette()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .position(Position.Fixed)
            .top(0.px)
            .left(0.px)
            .fillMaxWidth()
            .height(NAVBAR_HEIGHT.px)
            .background(colors.backgroundVariant.toRgb().copy(alpha = 220))
            .backdropFilter(blur(2.px))
            .padding(left = 16.px),
    ) {
        Image(
            src = Assets.LOGO,
            modifier = Modifier
                .size(48.px)
        )
        P(
            Modifier
                .color(colors.blue)
                .fontSize(24.px)
                .fontFamily(Fonts.ERMILOV)
                .margin(left = 8.px, right = 16.px)
                .toAttrs()
        ) {
            Text("БГИТУ Компас")
        }
    }
}