package com.lecomapp.liquefied.features.home.data.mappers

import com.lecomapp.liquefied.features.home.data.datasources.remote.dto.BrandDto
import com.lecomapp.liquefied.features.home.data.datasources.remote.dto.CategoryDto
import com.lecomapp.liquefied.features.home.data.datasources.remote.dto.HomeDto
import com.lecomapp.liquefied.features.home.data.datasources.remote.dto.ProductDto
import com.lecomapp.liquefied.features.home.domain.models.Brand
import com.lecomapp.liquefied.features.home.domain.models.Category
import com.lecomapp.liquefied.features.home.domain.models.HomeData
import com.lecomapp.liquefied.features.home.domain.models.Product

fun HomeDto.toDomain(): HomeData = HomeData(
    categories = categories.map { it.toDomain() },
    brands = brands.map { it.toDomain() },
    newArrivals = newArrivals.map { it.toDomain() },
    featuredProducts = featuredProducts.map { it.toDomain() },
)

fun CategoryDto.toDomain(): Category = Category(
    uuid = uuid.orEmpty(),
    name = name,
    slug = slug,
    imageUrl = imageUrl,
    productCount = productCount,
)

fun BrandDto.toDomain(): Brand = Brand(
    uuid = uuid.orEmpty(),
    name = name,
    slug = slug,
    logoUrl = logoUrl,
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
    primaryImage = primaryImage,
    categoryName = category?.name.orEmpty(),
    brandName = brand?.name.orEmpty(),
    brandLogoUrl = brand?.logoUrl,
    averageRating = reviewStats?.averageRating ?: 0.0,
    ratingCount = reviewStats?.totalCount ?: 0,
)
