package com.lecomapp.liquefied.features.home.domain.models

data class HomeData(
    val categories: List<Category>,
    val brands: List<Brand>,
    val newArrivals: List<Product>,
    val featuredProducts: List<Product>,
)

data class Category(
    val uuid: String,
    val name: String,
    val slug: String,
    val imageUrl: String?,
    val productCount: Long,
)

data class Brand(
    val uuid: String,
    val name: String,
    val slug: String,
    val logoUrl: String?,
    val productCount: Long,
)

data class Product(
    val uuid: String,
    val name: String,
    val slug: String,
    val shortDescription: String?,
    val basePrice: Double,
    val minVariantPrice: Double?,
    val maxVariantPrice: Double?,
    val isFeatured: Boolean,
    val primaryImage: String?,
    val categoryName: String,
    val brandName: String,
    val brandLogoUrl: String?,
    val averageRating: Double,
    val ratingCount: Int,
)
