package com.lecomapp.liquefied.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

val LiquefiedShapes = Shapes(
    extraSmall = RoundedCornerShape(AppCornerRadius.extraSmall),
    small = RoundedCornerShape(AppCornerRadius.small),
    medium = RoundedCornerShape(AppCornerRadius.medium),
    large = RoundedCornerShape(AppCornerRadius.large),
    extraLarge = RoundedCornerShape(AppCornerRadius.extraLarge),
)

object ShapeTokens {
    val button: Shape = RoundedCornerShape(AppCornerRadius.large)
    val card: Shape = RoundedCornerShape(AppCornerRadius.large)
    val textField: Shape = RoundedCornerShape(AppCornerRadius.large)
    val chip: Shape = RoundedCornerShape(AppCornerRadius.full)
    val dialog: Shape = RoundedCornerShape(AppCornerRadius.full)
    val bottomSheet: Shape = RoundedCornerShape(topStart = AppCornerRadius.large, topEnd = AppCornerRadius.large)
    val image: Shape = RoundedCornerShape(AppCornerRadius.medium)
    val badge: Shape = RoundedCornerShape(AppCornerRadius.full)
}
