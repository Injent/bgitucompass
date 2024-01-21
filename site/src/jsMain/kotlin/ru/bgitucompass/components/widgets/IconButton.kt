package ru.bgitucompass.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AlignContent
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignContent
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.outlineColor
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.toModifier
import org.jetbrains.compose.web.css.px
import ru.bgitucompass.IconButtonStyle
import ru.bgitucompass.theme.Dimens

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = { onClick() },
        modifier = IconButtonStyle.toModifier()
            .then(modifier)
            .height(56.px)
            .outlineColor(Colors.Transparent)
            .borderRadius(Dimens.ROUNDED_BUTTON.px)
            .alignContent(AlignContent.Center)
    ) {
        content()
    }
}