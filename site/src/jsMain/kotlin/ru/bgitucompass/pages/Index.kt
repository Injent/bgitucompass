package ru.bgitucompass.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.CSSAnimation
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
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
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.document
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.*
import ru.bgitucompass.*
import ru.bgitucompass.components.sections.Footer
import ru.bgitucompass.components.sections.UiPreviewSection
import ru.bgitucompass.components.widgets.IconButton
import ru.bgitucompass.theme.AppIcons
import ru.bgitucompass.theme.Assets
import ru.bgitucompass.theme.Fonts

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
        val breakpoint = rememberBreakpoint()

        CustomSimpleGrid(
            modifier = Modifier
                .fillMaxWidth(
                    if (breakpoint >= Breakpoint.MD) 1500.px
                    else 100.percent
                )
                .height(auto)
                .thenIf(breakpoint <= Breakpoint.MD) { Modifier.height(auto) }
                .thenIf(breakpoint > Breakpoint.MD) { Modifier.height(100.vh) }
                .padding(32.px)
                .align(Alignment.CenterHorizontally),
            firstContent = {
                Column(
                    Modifier.flex(1).fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LeftSide()
                }
            },
            secondContent = {
                Column(
                    Modifier.flex(1).fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    RightSide()
                }
            }
        )

        InfoSection()
        UiPreviewSection()
        WhatNext()
        Questions()

        Footer()
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
                .fillMaxWidth()
                .textAlign(TextAlign.Left)
                .toAttrs()
        ) {
            Text(ask)
        }
        P(
            BodyTextStyle
                .toModifier()
                .margin(top = 8.px)
                .fillMaxWidth()
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
                .lineHeight(1.0)
                .toAttrs()
        ) {
            Text("Часто задаваемые вопросы")
        }

        val itemModifier = Modifier
            .fillMaxSize()
            .align(Alignment.CenterHorizontally)
        CustomSimpleGrid(
            modifier = Modifier.fillMaxWidth()
                .height(auto)
                .padding(32.px)
                .maxWidth(1500.px)
                .align(Alignment.CenterHorizontally),
            itemModifier = Modifier
                .gap(64.px),
            firstContent = {
                Question(
                    ask = "Будет ли приложение для iPhone?",
                    response = "К сожалению, мы не поддерживаем устройства Apple",
                    modifier = itemModifier
                )
            },
            secondContent = {
                Question(
                    ask = "Будут ли регулярные обновления?",
                    response = "Каждое обновление включает новые полезные функции для наших пользователей",
                    modifier = itemModifier
                )
            },
            thirdContent = {
                Question(
                    ask = "Почему приложения нет в маркетах?",
                    response = "Санкции против России мешают размещению на Google Play. Прочие магазины не рассматриваются из-за низкой популярности",
                    modifier = itemModifier
                )
            }
        )
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
            Headline2TextStyle
                .toModifier()
                .margin(top = 24.px)
                .toAttrs()
        ) {
            Text(name)
        }
        P(
            BodyTextStyle
                .toModifier()
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
                src = AppIcons.CHART,
                name = "Только вперед",
                label = "Откройте для себя наши предстоящие функции – следите за обновлениями",
                modifier = modifier
            )
            Ca(
                src = AppIcons.MESSAGES,
                name = "Мы ждем ваших идей!",
                label = "Отправляйте свои идеи через Telegram: ",
                modifier = modifier,
                link = "@koespe",
                linkPath = "https://t.me/koespe"
            )
            Ca(
                src = AppIcons.BUG,
                name = "Помогите улучшить приложение",
                label = "Обнаружили ошибки? Сообщите нам: ",
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
                src = AppIcons.CALENDAR,
                name = "Актуальное расписание",
                label = "Расписание обновляется ежедневно с официального сайта университета",
                modifier = modifier
            )
            Ca(
                src = AppIcons.OBJECTS,
                name = "Удобный интерфейс",
                label = "Просматривайте расписание через приложение, виджет или уведомления",
                modifier = modifier
            )
            Ca(
                src = AppIcons.WIFI,
                name = "Офлайн доступ",
                label = "Расписание сохраняется на устройстве и доступно без интернета",
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

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = if (breakpoint > Breakpoint.MD) Arrangement.Center else Arrangement.Top
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .margin(bottom = 32.px)
        ) {
            Image(
                src = Assets.LOGO,
                modifier = Modifier
                    .size(48.px)
            )
            P(
                Modifier
                    .color(colors.blue)
                    .fontSize(40.px)
                    .fontFamily(Fonts.ERMILOV)
                    .margin(left = 16.px, right = 16.px)
                    .toAttrs()
            ) {
                Text("БГИТУ Компас")
            }
        }

        H1(
            Title1TextStyle
                .toModifier()
                .thenIf(condition = breakpoint <= Breakpoint.MD, other = Modifier.fontSize(48.px))
                .toAttrs()
        ) {
            Text("Упрости себе жизнь в новом семестре")
        }
        P(
            MediumHeadline
                .toModifier()
                .margin(top = 32.px)
                .toAttrs()
        ) {
            Text("Просматривайте расписание прямо через наше приложение или Телеграм")
        }
        Row(
            modifier = Modifier
                .flexWrap(FlexWrap.Wrap)
                .margin(top = 32.px)
                .gap(16.px)
                .align(
                    if (breakpoint <= Breakpoint.MD) Alignment.CenterHorizontally
                    else Alignment.Start
                )
        ) {
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
            IconButton(
                onClick = { ctx.router.navigateTo("https://bgitu-compass.ru/download") },
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
        }
    }
}

@Composable
private fun RightSide() {
    val breakpoint = rememberBreakpoint()

    val compact = (breakpoint < Breakpoint.MD)
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            src = Assets.HOME,
            modifier = Modifier
                .fillMaxSize()
                .margin(
                    top = if (compact) 32.px
                    else 0.px
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