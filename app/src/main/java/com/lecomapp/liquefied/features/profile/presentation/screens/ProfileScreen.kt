package com.lecomapp.liquefied.features.profile.presentation.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.common.AnimatedDiamonds
import com.lecomapp.liquefied.core.ui.components.common.AppHeaderContainer
import com.lecomapp.liquefied.core.ui.components.common.brandHeaderDiamonds
import com.lecomapp.liquefied.core.ui.components.feedback.ConfirmSheet
import com.lecomapp.liquefied.core.ui.components.feedback.ErrorView
import com.lecomapp.liquefied.core.ui.components.feedback.LiquefiedOptionSheet
import com.lecomapp.liquefied.core.ui.components.feedback.PictureSourceSheet
import com.lecomapp.liquefied.core.ui.components.feedback.SheetOption
import com.lecomapp.liquefied.core.ui.theme.LocalSnackBarHostState
import com.lecomapp.liquefied.core.ui.theme.ThemeMode
import com.lecomapp.liquefied.core.utils.LocaleManager
import com.lecomapp.liquefied.features.profile.presentation.components.ProfileErrorSnackbar
import com.lecomapp.liquefied.features.profile.presentation.components.ProfileIdentityHeader
import com.lecomapp.liquefied.features.profile.presentation.components.ProfileMenuBody
import com.lecomapp.liquefied.features.profile.presentation.components.ProfileSkeleton
import com.lecomapp.liquefied.features.profile.presentation.components.createCameraImageUri
import com.lecomapp.liquefied.features.profile.presentation.viewmodels.ProfileViewModel
import kotlinx.coroutines.launch

private enum class ProfileSheet { PICTURE, LANGUAGE, THEME, LOGOUT }

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onEditProfileClick: () -> Unit = {},
    onAddressesClick: () -> Unit = {},
    onOrdersClick: () -> Unit = {},
    onWalletClick: () -> Unit = {},
    onSupportClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackBarHostState.current
    var activeSheet by remember { mutableStateOf<ProfileSheet?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    val cameraImageUri = remember { createCameraImageUri(context) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) cameraImageUri?.let(viewModel::uploadProfilePicture)
        },
    )
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                cameraImageUri?.let(cameraLauncher::launch)
            } else {
                val message = context.getString(R.string.profile_camera_permission_needed)
                scope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            }
        },
    )
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> uri?.let(viewModel::uploadProfilePicture) },
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        AppHeaderContainer(
            backgroundContent = {
                AnimatedDiamonds(
                    modifier = Modifier.matchParentSize(),
                    baseColor = MaterialTheme.colorScheme.onPrimary,
                    diamonds = brandHeaderDiamonds,
                )
            },
        ) {
            ProfileIdentityHeader(
                user = state.user,
                isUploading = state.isUploading,
                onPictureClick = { activeSheet = ProfileSheet.PICTURE },
                onEditProfileClick = onEditProfileClick,
            )
        }

        when {
            state.isLoading && state.user == null -> ProfileSkeleton()
            state.user != null -> ProfileMenuBody(
                onAddressesClick = onAddressesClick,
                onOrdersClick = onOrdersClick,
                onWalletClick = onWalletClick,
                onSupportClick = onSupportClick,
                onLanguageClick = { activeSheet = ProfileSheet.LANGUAGE },
                onAppearanceClick = { activeSheet = ProfileSheet.THEME },
                onLogoutClick = { activeSheet = ProfileSheet.LOGOUT },
            )
            else -> ErrorView(
                title = stringResource(R.string.profile_error_title),
                subtitle = stringResource(R.string.profile_error_subtitle),
                onRetry = viewModel::loadProfile,
            )
        }
    }

    when (activeSheet) {
        ProfileSheet.PICTURE -> PictureSourceSheet(
            onCamera = {
                activeSheet = null
                val granted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA,
                ) == PackageManager.PERMISSION_GRANTED
                if (granted) {
                    cameraImageUri?.let(cameraLauncher::launch)
                } else {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            onGallery = {
                activeSheet = null
                galleryLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                )
            },
            onDismissRequest = { activeSheet = null },
        )
        ProfileSheet.LANGUAGE -> {
            val currentCode = LocaleManager.getSelectedLocaleCode(context)
            LiquefiedOptionSheet(
                title = stringResource(R.string.profile_language),
                options = LocaleManager.supportedLocales.map { locale ->
                    SheetOption(
                        label = locale.displayName,
                        selected = currentCode == locale.code,
                    )
                },
                onSelect = { index ->
                    activeSheet = null
                    val code = LocaleManager.supportedLocales[index].code
                    context.findActivity()?.let { activity ->
                        LocaleManager.setLocale(activity, code)
                        activity.recreate()
                    }
                },
                onDismissRequest = { activeSheet = null },
            )
        }
        ProfileSheet.THEME -> LiquefiedOptionSheet(
            title = stringResource(R.string.profile_appearance),
            options = ThemeMode.entries.map { mode ->
                SheetOption(
                    label = stringResource(
                        when (mode) {
                            ThemeMode.SYSTEM -> R.string.profile_theme_system
                            ThemeMode.LIGHT -> R.string.profile_theme_light
                            ThemeMode.DARK -> R.string.profile_theme_dark
                        },
                    ),
                    selected = themeMode == mode,
                )
            },
            onSelect = { index ->
                activeSheet = null
                viewModel.setThemeMode(ThemeMode.entries[index])
            },
            onDismissRequest = { activeSheet = null },
        )
        ProfileSheet.LOGOUT -> ConfirmSheet(
            title = stringResource(R.string.profile_logout_title),
            message = stringResource(R.string.profile_logout_message),
            confirmLabel = stringResource(R.string.profile_logout_confirm),
            isLoading = state.isLoggingOut,
            onConfirm = {
                activeSheet = null
                viewModel.logout()
            },
            onDismissRequest = { activeSheet = null },
        )
        null -> Unit
    }

    ProfileErrorSnackbar(error = state.error)
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}