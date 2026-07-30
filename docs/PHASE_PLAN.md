# Implementation Plan

## ✅ Phase 1: Foundation (Complete)

- Gradle dependency setup (Hilt, Retrofit, Room, Navigation, Coil, etc.)
- Build flavors (dev/staging/production)
- Hilt Application class + AndroidManifest
- Docs folder

## 🔄 Phase 2: Shared UI Components (In Progress)

- `AppButton.kt` — multi-variant with loading state
- `AppTextField.kt` — validation, error, password, multi-line
- `AppCard.kt` — elevated/filled/outlined
- `LoadingIndicator.kt`, `ErrorView.kt`, `EmptyState.kt`
- `PriceText.kt`, `RatingBar.kt`, `QuantitySelector.kt`, `SearchBar.kt`

## 🔜 Phase 3: Network Layer + DI

- `ApiResponse.kt` — generic response wrapper
- `AuthInterceptor.kt` — Bearer token + 401 refresh
- Hilt modules (Network, Database, App, Repository)
- `EnvironmentConfig.kt` — BuildConfig readers
- `AuthLocalDataSource.kt` — DataStore token storage

## 🔜 Phase 4: Navigation

- `Route.kt` — @Serializable sealed routes
- `AppNavGraph.kt` — NavHost with AuthGraph + MainGraph
- `BottomNavItem.kt` — bottom bar tabs
- `MainActivity.kt` — NavHost entry point

## 🔜 Phase 5: Feature — Auth

- Auth API + DTOs + mappers + repository
- Login/Register/Otp/ForgotPassword screens
- State, Event, ViewModel for each

## 🔜 Phase 6: Feature — Home

- Home API + DTOs + mapper + repository
- HomeScreen with carousel, categories, brands

## 🔜 Phase 7+: Remaining Features

Products → Cart → Checkout → Orders → Wishlist → Reviews → Addresses → Wallet → Profile → Notifications → Chat
