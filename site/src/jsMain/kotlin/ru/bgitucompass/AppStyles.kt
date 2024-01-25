package ru.bgitucompass

import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.ButtonStyle
import com.varabyte.kobweb.silk.components.layout.HorizontalDividerStyle
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.ariaDisabled
import com.varabyte.kobweb.silk.components.style.hover
import com.varabyte.kobweb.silk.components.style.not
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.theme.modifyComponentStyle
import com.varabyte.kobweb.silk.theme.modifyComponentStyleBase
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import ru.bgitucompass.theme.Fonts

@InitSilk
fun initSiteStyles(ctx: InitSilkContext) {
    ctx.stylesheet.registerStyleBase("body") {
        Modifier
            .fontFamily(
                "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Oxygen", "Ubuntu",
                "Cantarell", "Fira Sans", "Droid Sans", "Helvetica Neue", "sans-serif"
            )
            .fontSize(18.px)
            .lineHeight(1.5)
    }

    // Silk dividers only extend 90% by default; we want full width dividers in our site
    ctx.theme.modifyComponentStyleBase(HorizontalDividerStyle) {
        Modifier.fillMaxWidth()
    }
    ctx.theme.modifyComponentStyle(ButtonStyle) {
        (hover + not(ariaDisabled)) {
            Modifier
        }
    }
}

val IconButtonStyle by ComponentStyle {
    base {
        Modifier
            .backgroundColor(SitePalettes.light.blue)
            .transition(
                CSSTransition(property = "background-color", duration = 300.ms)
            )
    }

    (hover + not(ariaDisabled)) {
        Modifier
            .backgroundColor(SitePalettes.light.cobalt)
            .transition(
                CSSTransition(property = "background-color", duration = 300.ms)
            )
    }
}

val Title1TextStyle by ComponentStyle {
    base {
        Modifier
            .color(SitePalettes.light.blackText)
            .fontFamily(Fonts.ALSHAUSS)
            .fontWeight(FontWeight.Bold)
            .fontSize(64.px)
            .lineHeight(1.0)
            .margin(0.px)
    }
}

val BodyTextStyle by ComponentStyle {
    base {
        Modifier
            .fontFamily(Fonts.ALSHAUSS)
            .fontSize(20.px)
            .fontWeight(FontWeight.Normal)
            .color(SitePalettes.light.blackText)
            .lineHeight(1.4)
            .margin(0.px)
    }
}

val SmallHeadlineTextStyle by ComponentStyle {
    base {
        Modifier
            .fontFamily(Fonts.ALSHAUSS)
            .fontWeight(FontWeight.Bold)
            .fontSize(24.px)
            .lineHeight(1.0)
            .color(SitePalettes.light.blackText)
            .margin(0.px)
    }
}

val Headline2TextStyle by ComponentStyle {
    base {
        Modifier
            .fontFamily(Fonts.ALSHAUSS)
            .fontWeight(FontWeight.Medium)
            .fontSize(24.px)
            .color(SitePalettes.light.blackText)
            .margin(0.px)
    }
}

val MediumHeadline by ComponentStyle {
    base {
        Modifier
            .fontFamily(Fonts.ALSHAUSS)
            .fontWeight(FontWeight.Medium)
            .fontSize(32.px)
            .color(SitePalettes.light.blackText)
            .margin(0.px)
    }
}

val FooterLinkTextStyle by ComponentStyle {
    base {
        Modifier
            .fontFamily(Fonts.ALSHAUSS)
            .fontWeight(FontWeight.Medium)
            .fontSize(18.px)
            .lineHeight(1.0)
            .color(SitePalettes.light.blue)
            .margin(0.px)
            .textDecorationLine(TextDecorationLine.None)
    }
}