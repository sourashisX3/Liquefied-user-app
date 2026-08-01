package com.lecomapp.liquefied.core.config.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object Splash : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object Register : Route

    @Serializable
    data object ForgotPassword : Route

    @Serializable
    data class OtpVerification(val identifier: String) : Route

    @Serializable
    data class ResetPassword(val identifier: String, val otp: String) : Route

    @Serializable
    data object Home : Route

    @Serializable
    data class ProductDetail(val uuid: String) : Route

    @Serializable
    data object ProductSearch : Route

    @Serializable
    data object Cart : Route

    @Serializable
    data object Checkout : Route

    @Serializable
    data object Orders : Route

    @Serializable
    data class OrderDetail(val uuid: String) : Route

    @Serializable
    data object Wishlist : Route

    @Serializable
    data class WriteReview(val productUuid: String) : Route

    @Serializable
    data object AddressList : Route

    @Serializable
    data class AddressForm(val uuid: String? = null) : Route

    @Serializable
    data object Wallet : Route

    @Serializable
    data object Notifications : Route

    @Serializable
    data object ChatList : Route

    @Serializable
    data class ChatDetail(val roomUuid: String) : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data object EditProfile : Route
}
