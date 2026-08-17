package com.lecomapp.liquefied.features.catalog.data.mappers

import com.lecomapp.liquefied.core.config.network.EnvironmentConfig
import com.lecomapp.liquefied.features.catalog.data.datasources.remote.dto.BrandDto
import com.lecomapp.liquefied.features.catalog.data.datasources.remote.dto.CategoryDto
import com.lecomapp.liquefied.features.catalog.data.datasources.remote.dto.ProductDto
import com.lecomapp.liquefied.features.catalog.domain.models.Brand
import com.lecomapp.liquefied.features.catalog.domain.models.Category
import com.lecomapp.liquefied.features.catalog.domain.models.Product

fun CategoryDto.toDomain(): Category = Category(
    uuid = uuid.orEmpty(),
    name = name,
    slug = slug,
    imageUrl = resolveUrl(imageUrl),
    productCount = productCount,
)

fun BrandDto.toDomain(): Brand = Brand(
    uuid = uuid.orEmpty(),
    name = name,
    slug = slug,
    logoUrl = resolveUrl(logoUrl),
    productCount = productCount,
)

fun ProductDto.toDomain(): Product = Product(
    uuid = uuid.orEmpty(),
    name = name,
    slug = slug,
    shortDescription = shortDescription,
    basePrice = basePrice,
    minVariantPrice = minVariantPrice,
    maxVariantPrice = maxVariantPrice,
    isFeatured = isFeatured,
    primaryImage = resolveUrl(primaryImage),
    categoryName = category?.name.orEmpty(),
    brandName = brand?.name.orEmpty(),
    brandLogoUrl = resolveUrl(brand?.logoUrl),
    averageRating = reviewStats?.averageRating ?: 0.0,
    ratingCount = reviewStats?.totalCount ?: 0,
)

fun resolveUrl(url: String?): String? {
    if (url.isNullOrBlank()) return null
    return if (url.startsWith("http://") || url.startsWith("https://")) {
        url
    } else {
        EnvironmentConfig.baseUrl.trimEnd('/') + "/" + url.trimStart('/')
    }
}