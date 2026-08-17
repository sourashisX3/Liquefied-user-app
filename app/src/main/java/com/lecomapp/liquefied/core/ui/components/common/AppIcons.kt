package com.lecomapp.liquefied.core.ui.components.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Semantic icon catalog. Pairs are (unselected, selected) for togglable icons.
 */
object AppIcons {

    object Nav {
        val Home: Pair<ImageVector, ImageVector> = Icons.Outlined.Home to Icons.Filled.Home
        val Categories: Pair<ImageVector, ImageVector> = Icons.Outlined.Category to Icons.Filled.Category
        val Orders: Pair<ImageVector, ImageVector> = Icons.AutoMirrored.Outlined.ReceiptLong to Icons.AutoMirrored.Filled.ReceiptLong
        val Profile: Pair<ImageVector, ImageVector> = Icons.Outlined.AccountCircle to Icons.Filled.AccountCircle
    }

    object Action {
        val Search: ImageVector = Icons.Outlined.Search
        val Notifications: Pair<ImageVector, ImageVector> = Icons.Outlined.Notifications to Icons.Filled.Notifications
        val Cart: Pair<ImageVector, ImageVector> = Icons.Outlined.ShoppingCart to Icons.Filled.ShoppingCart
        val Location: ImageVector = Icons.Outlined.LocationOn
        val ExpandMore: ImageVector = Icons.Outlined.ExpandMore
        val Logout: ImageVector = Icons.AutoMirrored.Outlined.Logout
        val Edit: ImageVector = Icons.Outlined.Edit
        val Wallet: ImageVector = Icons.Outlined.AccountBalanceWallet
        val Support: ImageVector = Icons.Outlined.SupportAgent
        val PhotoCamera: ImageVector = Icons.Outlined.PhotoCamera
        val PhotoLibrary: ImageVector = Icons.Outlined.PhotoLibrary
        val Translate: ImageVector = Icons.Outlined.Translate
        val Palette: ImageVector = Icons.Outlined.Palette
        val Check: ImageVector = Icons.Outlined.Check
        val ChevronRight: ImageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight
    }
}