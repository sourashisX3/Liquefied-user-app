package com.lecomapp.liquefied.features.profile.presentation.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.common.AppIcons
import com.lecomapp.liquefied.features.profile.domain.models.ProfileUser
import java.io.File

@Composable
fun ProfileAvatar(
    user: ProfileUser?,
    isUploading: Boolean,
    onClick: () -> Unit,
) {
    val fullName = user?.fullName ?: ""
    Box(contentAlignment = Alignment.BottomEnd) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .clickable(enabled = !isUploading && user != null, onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            if (!user?.profilePictureUrl.isNullOrBlank()) {
                SubcomposeAsyncImage(
                    model = user?.profilePictureUrl,
                    contentDescription = fullName,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    when (painter.state) {
                        is AsyncImagePainter.State.Error -> AvatarPlaceholder(fullName)
                        else -> SubcomposeAsyncImageContent()
                    }
                }
            } else if (user != null) {
                AvatarPlaceholder(fullName)
            }
            if (isUploading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.35f)),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 3.dp,
                    )
                }
            }
        }
        Icon(
            imageVector = AppIcons.Action.PhotoCamera,
            contentDescription = stringResource(R.string.profile_change_picture),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .padding(6.dp),
        )
    }
}

@Composable
private fun AvatarPlaceholder(fullName: String) {
    Icon(
        imageVector = Icons.Outlined.Person,
        contentDescription = fullName,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(36.dp),
    )
}

fun createCameraImageUri(context: Context): Uri? {
    return runCatching {
        val file = File(context.cacheDir, "profile_camera_${System.currentTimeMillis()}.jpg")
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }.getOrNull()
}