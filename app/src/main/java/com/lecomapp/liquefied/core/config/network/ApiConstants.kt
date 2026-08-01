package com.lecomapp.liquefied.core.config.network

object ApiConstants {

    const val BASE_URL = "http://10.102.226.33:8083/api/v1/"

    object Auth {
        const val LOGIN = "auth/login"
        const val REGISTER = "auth/register"
        const val REFRESH = "auth/refresh"
        const val SEND_OTP = "auth/send-otp"
        const val VERIFY_OTP = "auth/verify-otp"
        const val LOGOUT = "auth/logout"
    }

    object Home {
        const val HOME = "home"
    }

    object Products {
        const val PRODUCTS = "products"
        const val PRODUCT = "products/{uuid}"
        const val SIMILAR = "products/{uuid}/similar"
        const val REVIEWS = "products/{uuid}/reviews"
    }

    object Categories {
        const val CATEGORIES = "categories"
        const val CATEGORY_TREE = "categories/tree"
        const val CATEGORY = "categories/{slug}"
    }

    object Brands {
        const val BRANDS = "brands"
    }

    object Cart {
        const val CARTS = "carts"
        const val CART_ITEM = "carts/{itemUuid}"
        const val ADD_TO_CART = "carts/{productUuid}"
    }

    object Orders {
        const val CHECKOUT = "orders/checkout"
        const val ORDERS = "orders"
        const val ORDER = "orders/{uuid}"
        const val CANCEL = "orders/{uuid}/cancel"
    }

    object Wishlist {
        const val WISHLIST = "wishlist"
        const val ADD_ITEM = "wishlist/{productUuid}"
        const val REMOVE_ITEM = "wishlist/{itemUuid}"
    }

    object Reviews {
        const val REVIEWS = "reviews"
        const val VOTE = "reviews/{id}/vote"
    }

    object Addresses {
        const val ADDRESSES = "addresses"
        const val ADDRESS = "addresses/{uuid}"
    }

    object Wallet {
        const val WALLET = "wallets/me"
        const val TRANSACTIONS = "wallets/me/transactions"
    }

    object Profile {
        const val USER = "users/me"
        const val PASSWORD = "users/me/password"
    }

    object Notifications {
        const val NOTIFICATIONS = "notifications"
        const val UNREAD_COUNT = "notifications/unread-count"
        const val READ = "notifications/{uuid}/read"
        const val READ_ALL = "notifications/read-all"
    }

    object Chat {
        const val ROOMS = "chat/rooms"
        const val ROOM_MESSAGES = "chat/rooms/{uuid}/messages"
    }

    object Coupons {
        const val VALIDATE = "coupons/validate"
    }
}
