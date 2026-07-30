# Liquefied E-Commerce — Architecture

## Overview

Modern Android e-commerce app built with **MVVM + Clean Architecture**, targeting mobile and tablet. Uses **Jetpack Compose** with **Material Design 3**.

## Tech Stack

| Layer | Technology |
|---|---|
| UI | Jetpack Compose, Material3 |
| DI | Hilt |
| Network | Retrofit + OkHttp + Kotlin Serialization |
| Database | Room + DataStore |
| Images | Coil |
| Navigation | Navigation Compose (type-safe @Serializable routes) |
| Async | Coroutines + StateFlow |

## Project Structure

```
com.lecomapp.liquefied/
├── core/                  # Shared across features
│   ├── config/
│   │   ├── navigation/    # Route, NavGraph, BottomNavItem
│   │   └── network/       # ApiConstants, EnvironmentConfig
│   ├── database/          # AppDatabase, Converters
│   ├── di/                # Hilt modules
│   ├── network/           # ApiResponse, AuthInterceptor
│   ├── theme/             # MD3 theme tokens
│   ├── ui/components/     # Shared composables
│   └── utils/             # Extensions, helpers
└── feature/               # Feature modules (auth, home, product, cart...)
    ├── data/              # DTOs, mappers, repos, data sources
    ├── domain/            # Models, repo interfaces, use cases
    └── presentation/      # Screens, ViewModels, States, Events
```

## MVVM Data Flow

```
User Action → UI Event → ViewModel → UseCase → Repository → API / DB
                  ↑                                        │
                  └──────── StateFlow ─────────────────────┘
```

Each layer is单向依赖: `presentation → domain → data`.

## Key Patterns

- **State**: `data class FeatureState` with `isLoading`, `error`, data fields
- **Events**: `sealed interface FeatureEvent` for all user actions
- **ViewModel**: `@HiltViewModel`, exposes `state: StateFlow<State>`, handles `onEvent()`
- **UseCase**: Single-responsibility, injects repository, returns `Result<T>`
- **Repository**: Interface in domain, implementation in data
- **Mappers**: Extension functions for DTO ↔ Domain ↔ Entity conversions
