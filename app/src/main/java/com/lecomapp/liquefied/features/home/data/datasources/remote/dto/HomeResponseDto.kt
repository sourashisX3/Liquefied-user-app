package com.lecomapp.liquefied.features.home.data.datasources.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeDto(
    val categories: List<CategoryDto> = emptyList(),
    val brands: List<BrandDto> = emptyList(),
    @SerialName("newArrivals")
    val newArrivals: List<ProductDto> = emptyList(),
    @SerialName("featuredProducts")
    val featuredProducts: List<ProductDto> = emptyList(),
)

@Serializable
data class CategoryDto(
    val id: Long = 0,
    val uuid: String? = null,
    val name: String = "",
    val slug: String = "",
    val description: String? = null,
    val imageUrl: String? = null,
    val parentSlug: String? = null,
    val sortOrder: Int = 0,
    @SerialName("isActive")
    val isActive: Boolean = true,
    val productCount: Long = 0,
)

@Serializable
data class BrandDto(
    val id: Long = 0,
    val uuid: String? = null,
    val name: String = "",
    val slug: String = "",
    val description: String? = null,
    val logoUrl: String? = null,
    val website: String? = null,
    @SerialName("isActive")
    val isActive: Boolean = true,
    val productCount: Long = 0,
)

@Serializable
data class ProductDto(
    val uuid: String? = null,
    val sku: String? = null,
    val name: String = "",
    val slug: String = "",
    val description: String? = null,
    val shortDescription: String? = null,
    val basePrice: Double = 0.0,
    val minVariantPrice: Double? = null,
    val maxVariantPrice: Double? = null,
    @SerialName("isActive")
    val isActive: Boolean = true,
    @SerialName("isFeatured")
    val isFeatured: Boolean = false,
    val primaryImage: String? = null,
    val category: CategorySummaryDto? = null,
    val brand: BrandSummaryDto? = null,
    val reviewStats: ReviewStatsDto? = null,
)

@Serializable
data class CategorySummaryDto(
    val id: Long = 0,
    val name: String = "",
    val slug: String = "",
)

@Serializable
data class BrandSummaryDto(
    val id: Long = 0,
    val name: String = "",
    val slug: String = "",
    val logoUrl: String? = null,
)

@Serializable
data class ReviewStatsDto(
    val averageRating: Double = 0.0,
    val totalCount: Int = 0,
)
