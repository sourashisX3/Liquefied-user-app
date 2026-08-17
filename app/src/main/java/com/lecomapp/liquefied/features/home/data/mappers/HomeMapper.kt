package com.lecomapp.liquefied.features.home.data.mappers

import com.lecomapp.liquefied.features.catalog.data.mappers.resolveUrl
import com.lecomapp.liquefied.features.catalog.data.mappers.toDomain
import com.lecomapp.liquefied.features.home.data.datasources.remote.dto.BannerDto
import com.lecomapp.liquefied.features.home.data.datasources.remote.dto.HomeDto
import com.lecomapp.liquefied.features.home.domain.models.Banner
import com.lecomapp.liquefied.features.home.domain.models.HomeData

fun HomeDto.toDomain(): HomeData = HomeData(
    categories = categories.map { it.toDomain() },
    brands = brands.map { it.toDomain() },
    banners = banners.map { it.toDomain() },
    newArrivals = newArrivals.map { it.toDomain() },
    featuredProducts = featuredProducts.map { it.toDomain() },
    bestSellers = bestSellers.map { it.toDomain() },
    trending = trending.map { it.toDomain() },
    deals = deals.map { it.toDomain() },
    cartCount = cartCount,
    wishlistCount = wishlistCount,
    unreadNotificationCount = unreadNotificationCount,
    walletBalance = walletBalance,
)

fun BannerDto.toDomain(): Banner = Banner(
    uuid = uuid.orEmpty(),
    title = title,
    subtitle = subtitle,
    imageUrl = resolveUrl(imageUrl),
    linkType = linkType,
    linkValue = linkValue,
)