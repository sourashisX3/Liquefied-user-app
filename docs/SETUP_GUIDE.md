# Setup Guide

## Build Variants

Three flavors via `flavorDimensions = "environment"`:

| Flavor | Base URL | WS URL |
|---|---|---|
| `dev` | `http://10.0.2.2:8083/api/v1` | `ws://10.0.2.2:8083/api/v1/ws` |
| `staging` | `https://staging-api.example.com/api/v1` | `wss://staging-api.example.com/api/v1/ws` |
| `production` | `https://api.example.com/api/v1` | `wss://api.example.com/api/v1/ws` |

Select build variant in Android Studio: **Build > Select Build Variant > devDebug**

## Running Locally

1. Start backend API server on port 8083
2. Select `devDebug` build variant
3. Run on emulator (uses `10.0.2.2` to access host machine)

## Required SDK

- compileSdk: 35
- minSdk: 26
- Java 17

## Dependencies

Managed through version catalog: `gradle/libs.versions.toml`

Key libraries:
- Jetpack Compose (BOM 2024.12.01)
- Hilt 2.53.1
- Retrofit 2.11.0 + OkHttp 4.12.0
- Room 2.6.1
- DataStore 1.1.1
- Coil 2.7.0
- Kotlinx Serialization 1.7.3
- Lottie 6.6.2
