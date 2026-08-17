package com.lecomapp.liquefied.features.home.data.datasources.remote.dto

import com.lecomapp.liquefied.features.catalog.data.datasources.remote.dto.BrandDto
import com.lecomapp.liquefied.features.catalog.data.datasources.remote.dto.CategoryDto
import com.lecomapp.liquefied.features.catalog.data.datasources.remote.dto.ProductDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeDto(
    val categories: List<CategoryDto> = emptyList(),
    val brands: List<BrandDto> = emptyList(),
    val banners: List<BannerDto> = emptyList(),
    @SerialName("newArrivals")
    val newArrivals: List<ProductDto> = emptyList(),
    @SerialName("featuredProducts")
    val featuredProducts: List<ProductDto> = emptyList(),
    @SerialName("bestSellers")
    val bestSellers: List<ProductDto> = emptyList(),
    val trending: List<ProductDto> = emptyList(),
    val deals: List<ProductDto> = emptyList(),
    @SerialName("cartCount")
    val cartCount: Long = 0,
    @SerialName("wishlistCount")
    val wishlistCount: Long = 0,
    @SerialName("unreadNotificationCount")
    val unreadNotificationCount: Long = 0,
    val walletBalance: Double? = null,
)

@Serializable
data class BannerDto(
    val uuid: String? = null,
    val title: String = "",
    val subtitle: String? = null,
    val imageUrl: String? = null,
    val linkType: String? = null,
    val linkValue: String? = null,
    val sortOrder: Int = 0,
    @SerialName("isActive")
    val isActive: Boolean = true,
)