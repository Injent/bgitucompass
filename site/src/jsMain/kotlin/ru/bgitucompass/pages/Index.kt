package ru.bgitucompass.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.CSSAnimation
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.document
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.*
import ru.bgitucompass.BodyTextStyle
import ru.bgitucompass.MediumHeadline
import ru.bgitucompass.SitePalettes
import ru.bgitucompass.components.sections.NavBar
import ru.bgitucompass.components.sections.UiPreviewSection
import ru.bgitucompass.components.widgets.IconButton
import ru.bgitucompass.theme.Dimens
import ru.bgitucompass.theme.Fonts
import ru.bgitucompass.toSitePalette

@Page
@Composable
fun HomePage() {
    val colors = ColorMode.current.toSitePalette()
    LaunchedEffect(Unit) {
        document.title = "БГИТУ Компас"
    }

    Column(
        Modifier
            .fillMaxWidth()
            .minHeight(100.percent)
            .background(colors.background)
    ) {
        NavBar {  }

        val breakpoint = rememberBreakpoint()
        SimpleGrid(
            numColumns = numColumns(base = 1,  md = 2),
            modifier = Modifier
                .margin(top = Dimens.NAVBAR_HEIGHT.px)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(
                    if (breakpoint <= Breakpoint.MD) 100.percent
                    else 1500.px
                )
                .thenIf(breakpoint <= Breakpoint.MD) { Modifier.height(auto) }
                .thenIf(breakpoint > Breakpoint.MD) { Modifier.height(100.vh) }
                .padding(32.px)
                .scrollBehavior(ScrollBehavior.Smooth)
        ) {
            LeftSide()
            RightSide()
        }
        InfoSection()
        UiPreviewSection()
        WhatNext(
            modifier = Modifier
                .margin(top = if (breakpoint <= Breakpoint.MD) 64.px else 0.px)
        )
        Questions(
            modifier = Modifier
                .margin(top = if (breakpoint <= Breakpoint.MD) 64.px else 0.px)
        )
    }
}

@Composable
private fun Question(
    ask: String,
    response: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier,
    ) {
        P(
            BodyTextStyle
                .toModifier()
                .fontWeight(FontWeight.Bold)
                .fillMaxWidth(80.percent)
                .toAttrs()
        ) {
            Text(ask)
        }
        P(
            BodyTextStyle
                .toModifier()
                .margin(top = 8.px)
                .fillMaxWidth(80.percent)
                .toAttrs()
        ) {
            Text(response)
        }
    }
}

@Composable
private fun Questions(
    modifier: Modifier = Modifier,
) {
    val breakpoint = rememberBreakpoint()
    Column(modifier.fillMaxSize()) {
        H2(
            MediumHeadline
                .toModifier()
                .thenIf(
                    condition = breakpoint <= Breakpoint.MD,
                    other = Modifier.margin(top = 32.px).padding(leftRight = 32.px)
                )
                .thenIf(
                    condition = breakpoint > Breakpoint.MD,
                    other = Modifier.margin(topBottom = 32.px)
                )
                .align(Alignment.CenterHorizontally)
                .toAttrs()
        ) {
            Text("Часто задаваемые вопросы")
        }

        SimpleGrid(
            numColumns = numColumns(base = 1, md = 3),
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .height(auto)
                .maxWidth(1500.px)
                .thenIf(condition = breakpoint <= Breakpoint.MD, other = Modifier.gap(32.px))
                .padding(32.px),
        ) {
            val mod = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
            Question(
                ask = "Будет ли приложение для IPhone?",
                response = "К сожалению нет, у нас нет техники Apple и мы не можем разрабатывать под IPhone",
                modifier = mod
            )
            Question(
                ask = "Есть ли у приложения регулярные обновления?",
                response = "Да, каждое обновление содержит новые полезные функции для наших пользователей",
                modifier = mod
            )
        }
    }
}

@Composable
private fun Ca(
    src: String,
    name: String,
    label: String,
    modifier: Modifier = Modifier,
    link: String? = null,
    linkPath: String = "",
) {
    val colors = ColorMode.current.toSitePalette()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            src = src,
            modifier = Modifier
                .size(72.px)
        )
        H3(
            Modifier
                .fontFamily(Fonts.ALSHAUSS)
                .fontWeight(FontWeight.Medium)
                .fontSize(24.px)
                .color(colors.blackText)
                .toAttrs()
        ) {
            Text(name)
        }
        P(
            BodyTextStyle
                .toModifier()
                .margin(top = 8.px)
                .textAlign(TextAlign.Center)
                .maxWidth(400.px)
                .toAttrs()
        ) {
            Text(label)
            link?.let {
                Link(
                    path = linkPath,
                    text = it,
                    modifier = Modifier.color(SitePalettes.light.blue)
                )
            }
        }
    }
}

@Composable
private fun WhatNext(modifier: Modifier = Modifier) {
    val colors = ColorMode.current.toSitePalette()
    val breakpoint = rememberBreakpoint()

    Column(
        modifier
            .fillMaxWidth()
            .height(auto)
            .padding(
                if (breakpoint > Breakpoint.MD) 32.px else 16.px
            )
            .background(colors.surface),
    ) {
        H2(
            Modifier
                .fontFamily(Fonts.ALSHAUSS)
                .fontWeight(FontWeight.Bold)
                .fontSize(32.px)
                .color(colors.blackText)
                .align(Alignment.CenterHorizontally)
                .margin(bottom = 32.px)
                .toAttrs()
        ) {
            Text("Что дальше?")
        }

        @Composable
        fun content(modifier: Modifier = Modifier) {
            Ca(
                src = "chart-line-up.svg",
                name = "Только вперед",
                label = "У нас есть еще множество незаконченных фишех. Оставайтесь с нами чтобы получить их",
                modifier = modifier
            )
            Ca(
                src = "messages-question.svg",
                name = "Мы ждем ваши идеи",
                label = "Вы можете предлагать свои идеи в телеграм ",
                modifier = modifier,
                link = "@koespe",
                linkPath = "https://t.me/koespe"
            )
            Ca(
                src = "bug.svg",
                name = "Помогите улучшить приложение",
                label = "Если вы столкнулись с ошибками, то пишите сюда ",
                modifier = modifier,
                link = "@Injent",
                linkPath = "https://t.me/Injent"
            )
        }

        if (breakpoint > Breakpoint.MD) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(32.px),
                horizontalArrangement = Arrangement.SpaceAround
            ) { content(Modifier.width(30.percent)) }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .gap(64.px),
                horizontalAlignment = Alignment.CenterHorizontally
            ) { content() }
        }
    }
}

@Composable
private fun InfoSection() {
    val colors = ColorMode.current.toSitePalette()
    val breakpoint = rememberBreakpoint()

    Column(
        Modifier
            .fillMaxWidth()
            .height(auto)
            .padding(
                if (breakpoint > Breakpoint.MD) 32.px else 16.px
            )
            .background(colors.surface),
    ) {
        H2(
            MediumHeadline
                .toModifier()
                .align(Alignment.CenterHorizontally)
                .margin(bottom = 32.px)
                .toAttrs()
        ) {
            Text("Почему стоит скачать?")
        }

        @Composable
        fun content(modifier: Modifier = Modifier) {
            Ca(
                src = "calendar-clock.svg",
                name = "Актуальное расписание",
                label = "Информация о расписании обновляется ежедневно с сайта вуза",
                modifier = modifier
            )
            Ca(
                src = "objects-column.svg",
                name = "Удобный интерфейс",
                label = "Вы можете просматривать расписание из приложения, виджета и даже уведомления",
                modifier = modifier
            )
            Ca(
                src = "wifi-slash.svg",
                name = "Офлайн доступ",
                label = "Расписание сохраняется на вашем устройстве и доступно в режиме без интернета",
                modifier = modifier
            )
        }

        if (breakpoint > Breakpoint.MD) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(32.px),
                horizontalArrangement = Arrangement.SpaceAround
            ) { content(Modifier.width(30.percent)) }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .gap(64.px),
                horizontalAlignment = Alignment.CenterHorizontally
            ) { content() }
        }
    }
}

@Composable
private fun LeftSide() {
    val breakpoint = rememberBreakpoint()
    val colors = ColorMode.current.toSitePalette()
    val ctx = rememberPageContext()

    Column(Modifier.fillMaxSize()) {
        H1(
            Modifier
                .color(colors.blackText)
                .fontFamily(Fonts.ALSHAUSS)
                .fontWeight(FontWeight.Bold)
                .fontSize(64.px)
                .lineHeight(1.0)
                .toAttrs()
        ) {
            Text("Упрости себе жизнь в новом семестре")
        }
        P(
            Modifier
                .color(colors.blackText)
                .fontFamily(Fonts.ALSHAUSS)
                .fontWeight(FontWeight.Medium)
                .lineHeight(1.0)
                .fontSize(32.px)
                .toAttrs()
        ) {
            Text("Просматривайте расписание занятий в БГИТУ прямо из приложения")
        }
        Row(
            modifier = Modifier
                .flexWrap(FlexWrap.Wrap)
                .margin(top = 64.px)
                .gap(16.px)
                .align(
                    if (breakpoint <= Breakpoint.MD) Alignment.CenterHorizontally
                    else Alignment.Start
                )
        ) {
            IconButton(
                onClick = { ctx.router.navigateTo("https://api.bgitu-compass.ru/download") },
            ) {
                P(
                    Modifier
                        .fontFamily(Fonts.ALSHAUSS)
                        .fontWeight(FontWeight.Medium)
                        .fontSize(20.px)
                        .lineHeight(0.9)
                        .color(colors.whiteText)
                        .toAttrs()
                ) {
                    Text("Скачать для Android")
                }
            }
            IconButton(
                onClick = { ctx.router.navigateTo("https://t.me/bgitu_compass_bot") },
            ) {
                P(
                    Modifier
                        .fontFamily(Fonts.ALSHAUSS)
                        .fontWeight(FontWeight.Medium)
                        .fontSize(20.px)
                        .lineHeight(0.9)
                        .color(colors.whiteText)
                        .toAttrs()
                ) {
                    Text("Telegram Бот")
                }
            }
        }
    }
}

@Composable
private fun RightSide() {
    val breakpoint = rememberBreakpoint()

    val compact = breakpoint <= Breakpoint.MD
    Box(
        Modifier.fillMaxSize()
    ) {
        Image(
            src = "home.png",
            modifier = Modifier
                .fillMaxSize()
                .margin(
                    top = if (compact) 64.px
                    else 0.px
                )
                .align(
                    if (compact) Alignment.TopCenter
                    else Alignment.TopCenter
                )
                .maxWidth(
                    if (compact) 80.percent
                    else 350.px
                )
                .height(auto)
                .borderRadius(32.px)
                .animation(CSSAnimation("fadeIn", duration = 5.s))
        )
    }
}