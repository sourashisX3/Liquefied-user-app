package com.lecomapp.liquefied.features.home.domain.models

import com.lecomapp.liquefied.features.catalog.domain.models.Brand
import com.lecomapp.liquefied.features.catalog.domain.models.Category
import com.lecomapp.liquefied.features.catalog.domain.models.Product

data class HomeData(
    val categories: List<Category>,
    val brands: List<Brand>,
    val banners: List<Banner>,
    val newArrivals: List<Product>,
    val featuredProducts: List<Product>,
    val bestSellers: List<Product>,
    val trending: List<Product>,
    val deals: List<Product>,
    val cartCount: Long = 0,
    val wishlistCount: Long = 0,
    val unreadNotificationCount: Long = 0,
    val walletBalance: Double? = null,
)

data class Banner(
    val uuid: String,
    val title: String,
    val subtitle: String?,
    val imageUrl: String?,
    val linkType: String?,
    val linkValue: String?,
)