package com.lecomapp.liquefied.features.splash.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lecomapp.liquefied.core.ui.components.common.AnimatedDiamonds
import com.lecomapp.liquefied.core.ui.components.common.AppLogoSection
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LiquefiedTheme
import com.lecomapp.liquefied.features.splash.presentation.animation.animateSequence
import com.lecomapp.liquefied.features.splash.presentation.animation.rememberSplashAnimState

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val anim = rememberSplashAnimState()

    LaunchedEffect(Unit) {
        anim.animateSequence {
            // TODO(uncomment-on-first-launch-only): read "onboarding_seen" flag and
            // navigate straight to Login if already seen, otherwise to Onboarding.
            onFinished()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedDiamonds(modifier = Modifier.matchParentSize())
        AppLogoSection(
            appNameAlpha = anim.logoAlpha.value,
            appNameScale = anim.logoScale.value,
            taglineAlpha = anim.taglineAlpha.value,
            taglineOffsetY = anim.taglineOffsetY.value,
            modifier = Modifier.padding(AppSpacing.xl),
        )
    }
}

@Preview(name = "Splash - Light", showBackground = true)
@Composable
private fun SplashScreenPreview() {
    LiquefiedTheme {
        SplashScreen(onFinished = {})
    }
}

@Preview(name = "Splash - Dark", showBackground = true)
@Composable
private fun SplashScreenDarkPreview() {
    LiquefiedTheme(darkTheme = true) {
        SplashScreen(onFinished = {})
    }
}
