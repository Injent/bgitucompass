package ru.bgitucompass.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.autoLength
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.toAttrs
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.*
import ru.bgitucompass.BodyTextStyle
import ru.bgitucompass.SmallHeadlineTextStyle
import ru.bgitucompass.theme.Assets

@Composable
fun ColumnScope.UiPreviewSection() {
    val breakpoint = rememberBreakpoint()
    SimpleGrid(
        numColumns = numColumns(base = 1, md = 2),
        modifier = Modifier
            .margin(top = 32.px)
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(
                if (breakpoint <= Breakpoint.MD) 100.percent
                else 1500.px
            )
            .thenIf(breakpoint <= Breakpoint.MD) { Modifier.height(auto) }
            .thenIf(breakpoint > Breakpoint.MD) { Modifier.height(100.vh) }
            .padding(
                if (breakpoint <= Breakpoint.MD) 24.px else 32.px
            )
            .scrollBehavior(ScrollBehavior.Smooth)
    ) {
        WidgetLeftSide()
        WidgetRightSide()
        if (breakpoint <= Breakpoint.MD) {
            NotificationRightSide()
            NotificationLeftSide()
        } else {
            NotificationLeftSide()
            NotificationRightSide()
        }
    }
}

@Composable
private fun NotificationLeftSide() {
    val breakpoint = rememberBreakpoint()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(auto)
            .padding(autoLength),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .width(
                    if (breakpoint <= Breakpoint.MD) 100.percent else 70.percent
                )
                .margin(bottom = 32.px)
        ) {
            H3(
                SmallHeadlineTextStyle
                    .toModifier()
                    .margin(top = 24.px, bottom = 12.px)
                    .toAttrs()
            ) {
                Text("Мгновенный просмотр расписания")
            }
            P(
                BodyTextStyle
                    .toAttrs()
            ) {
                Text("Активируйте постоянные уведомления в настройках для отображения информации о текущей и следующей паре в течение учебного дня")
            }
        }
    }
}

@Composable
private fun NotificationRightSide() {
    val breakpoint = rememberBreakpoint()
    Column(
        Modifier
            .fillMaxWidth()
            .margin(top = 64.px)
            .height(auto),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            src = Assets.NOTIFICATION,
            modifier = Modifier
                .width(if (breakpoint <= Breakpoint.MD) 80.percent else 70.percent)
                .maxHeight(520.px)
                .height(auto)
                .borderRadius(32.px)
        )
    }
}

@Composable
private fun WidgetLeftSide() {
    val breakpoint = rememberBreakpoint()
    Column(
        Modifier
            .fillMaxWidth()
            .height(auto),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            src = Assets.WIDGET,
            modifier = Modifier
                .width(if (breakpoint <= Breakpoint.MD) 80.percent else 70.percent)
                .maxHeight(520.px)
                .height(auto)
                .borderRadius(32.px)
        )
    }
}

@Composable
private fun WidgetRightSide() {
    val breakpoint = rememberBreakpoint()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(auto),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .width(
                    if (breakpoint <= Breakpoint.MD) 100.percent else 72.percent
                )
        ) {
            H3(
                SmallHeadlineTextStyle
                    .toModifier()
                    .margin(top = 24.px, bottom = 12.px)
                    .toAttrs()
            ) {
                Text("Кастомизируемые виджеты")
            }
            Column {
                P(
                    BodyTextStyle
                        .toAttrs()
                ) {
                    Text("Доступно 3 темы для виджета:")
                }
                Ul(
                    BodyTextStyle
                        .toModifier()
                        .margin(topBottom = 18.px)
                        .toAttrs()
                ) {
                    Li {
                        Text("Светлая")
                    }
                    Li {
                        Text("Темная")
                    }
                    Li {
                        Text("Динамическая (Android 13+)")
                    }
                }
                P(
                    BodyTextStyle
                        .toAttrs()
                ) {
                    Text("Настройте прозрачность виджета и адаптируйте его к любым обоям и стилям благодаря гибким настройкам")
                }
            }
        }
    }
}