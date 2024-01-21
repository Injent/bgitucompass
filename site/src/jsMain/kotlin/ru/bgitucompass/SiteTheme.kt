package ru.bgitucompass

import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode

class SitePalette(
    val background: Color,
    val surface: Color,
    val blackText: Color,
    val whiteText: Color,
    val backgroundVariant: Color,
    val blue: Color,
    val cobalt: Color,
)

object SitePalettes {
    val light = SitePalette(
        background = Color.rgb(255, 255, 255),
        surface = Color.rgb(242, 247, 255),
        blackText = Color.rgb(11, 31, 51),
        whiteText = Color.rgb(255, 255, 255),
        backgroundVariant = Color.rgb(248, 250, 253),
        blue = Color.rgb(0, 119, 255),
        cobalt = Color.rgb(0, 87, 255)
    )
    val dark = light
}

fun ColorMode.toSitePalette(): SitePalette {
    return when (this) {
        ColorMode.LIGHT -> SitePalettes.light
        ColorMode.DARK -> SitePalettes.dark
    }
}

@InitSilk
fun initTheme(ctx: InitSilkContext) {

}
