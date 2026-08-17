# AGENTS.md — Liquefied Android User App

Customer-facing Android app (`com.lecomapp.liquefied`, Kotlin + Jetpack Compose) for the Liquefied premium spirits & wine store.

## Project map

| Repo | Path | Role |
|---|---|---|
| `ecommerce-backend` | `E:\Projects\Liquefied\ecommerce-backend` | Spring Boot API — source of truth |
| `admin-panel-fontend` | `E:\Projects\Liquefied\admin-panel-fontend` | React admin panel — **verified reference implementation** of the API |
| This app | `E:\Projects\Liquefied\Liquefied` | Android user app |

- Backend docs live in `ecommerce-backend/docs/` (`api-overview.md` is partially stale — it lists old ID-based routes; the live API uses **UUID** routes as implemented in the admin panel).
- This repo's own docs: `docs/API_ENDPOINTS.md` (contains placeholder/outdated values — trust the tables below instead), `docs/THEME_GUIDE.md` (brand: rose `#DB4460`, secondary indigo `#3B3A5A`, display font Lobster), `docs/ARCHITECTURE.md`, `docs/SETUP_GUIDE.md`.
- Networking wiring: `app/src/main/java/com/lecomapp/liquefied/core/network/` — `ApiConstants.kt` (`BASE_URL`), `EnvironmentConfig.kt` (`BuildConfig.BASE_URL`), `NetworkModule.kt` (Retrofit). Cleartext hosts whitelisted in `network_security_config.xml`.

## Build & verify conventions

- Build: `.\gradlew.bat :app:assembleDebug`. Run lint before finishing work.
- Dev backend runs locally on port **8083**. Emulator: `http://10.0.2.2:8083/api/v1`; physical device: use the dev machine's LAN IP (e.g. `http://10.102.226.33:8083/api/v1`) and add it to `network_security_config.xml`.
- When an endpoint must be exercised end-to-end, hit the **live production API** (below) with the test super-admin account rather than guessing from docs.
- Commit style used across the ecosystem: `feat:`, `fix:` prefixes, short summary line.

## API facts (verified from the working admin panel + live backend)

### Base URLs

- Production: `https://liquefied-backend.onrender.com/api/v1`
- Dev (local backend on 8083): `http://10.0.2.2:8083/api/v1` (emulator) or LAN IP (device)

### Response envelope

```
{ "statusCode": 200, "message": "...", "response": <payload>, "pagination": { ... }? }
```

- Success payload is always under `response` (or `response.content` for Spring-page wrappers — chat rooms/messages and notifications).
- Pagination object: `{ currentPage, pageSize, totalElements, totalPages, hasNext, hasPrevious }`; paginated lists take `page` (1-based) + `size` params.
- Errors: `{ statusCode, message, errors?: Record<string, string[]> }` with proper HTTP status.
- Image/asset URLs returned by the API may be relative — resolve them against the API base URL.

### Auth

- `POST /auth/login` body `{ emailOrPhone, password }` → `AuthResponse` (`accessToken`, `refreshToken`). **Login field is `emailOrPhone`**, not `email`.
- `POST /auth/refresh` body `{ refreshToken }` → `{ token, refreshToken }` (note: `token`, not `accessToken`).
- `POST /auth/logout` (auth) — revokes the refresh token; ignore network errors on logout.
- `POST /otp/send` `{ emailOrPhone }`, `POST /otp/verify` `{ emailOrPhone, otp }` — used for password change and OTP login flows.
- Refresh pattern (admin): on any `401`, retry the request once with a refreshed token; if refresh fails, force logout. Only the access token goes in `Authorization: Bearer`.
- Test account: `superadmin@example.com` / `Password@1` (login via `emailOrPhone`).

### Endpoint catalog (user-app relevant, verified)

| Method | Path | Notes |
|---|---|---|
| GET | `/home` | User home feed: categories, brands, newArrivals, featuredProducts (admin dashboard KPI uses `/home/dashboard`) |
| GET | `/products` | Filters: `page, size, search, categorySlugs[], brandSlugs[], tagSlugs[], minPrice, maxPrice, active, isFeatured, sortBy, sortDir(asc/desc)` |
| GET | `/products/{uuid}` | Detail |
| GET | `/products/{uuid}/similar?limit=5` | Similar products |
| GET | `/categories` / `/brands` / `/tags` | `page, size, search, active, sortBy, sortDir` |
| GET | `/coupons?active=true&global=true` | Active coupons |
| POST | `/coupons/validate` | `{ code, userId, orderSubtotal }` → validated total as number |
| GET | `/users/me` · PUT `/users/me` | Profile; PUT fields: `firstName, lastName, dialCode, phoneNumber, profilePictureUrl, addressLine1, addressLine2, streetAddress, city, state, country, zipCode` |
| PUT | `/users/me/password` | `{ emailOrPhone, otp, currentPassword, newPassword }` |
| POST | `/users/me/profile-picture` | multipart field `file` |
| POST | `/users/me/deactivate` | Self-deactivation |
| POST | `/files/upload` | multipart field `file` → `{ uuid, originalName, contentType, size, url }`; use the returned `url` for product images/reviews etc. |
| GET | `/orders` | `page, size, search, status, from, to, sortBy, sortDir` |
| GET | `/orders/{uuid}` · PATCH `/orders/{uuid}/cancel` (`{ reason }`) | Detail / cancel |
| GET | `/order-statuses` | `{ code, name }` list |
| GET | `/reviews?page&size` | Reviews (admin: PATCH `/reviews/{uuid}/status`) |
| GET | `/returns?page&size` | Return requests (admin: PATCH `/returns/{uuid}/status` `{ status, resolutionNotes }`) |
| GET | `/notifications` · `/notifications/unread-count` | `response.content` = array; PATCH `/notifications/{uuid}/read`, `/notifications/read-all` |
| GET | `/chat/rooms/all?size=100` | `response.content` = `ChatRoom[]` (user-scoped for customers) |
| GET | `/chat/rooms/{uuid}/messages?size=100` | `response.content` = `ChatMessage[]` |
| PATCH | `/chat/rooms/{uuid}/assign` / `/close` | Agent actions |

### ⚠ Unverified — confirm before building on these

The following exist in backend spec docs (`api-overview.md`, `user-app-android.md`) but were **never exercised by the admin panel** — verify against the backend controllers or live API before relying on shapes:

- `POST /auth/register`, cart (`/carts`), wishlist (`/wishlists`), addresses (`/addresses`), `POST /orders/checkout`, deliveries/tracking (`/deliveries/{orderId}/tracking`), payments (`/payments/pay`, `/payments/{id}`), wallet user endpoints (`GET /wallet`, `GET /wallet/transactions`), `POST /returns` (create), `POST /products/{uuid}/reviews`.

## Chat & notifications (STOMP/WebSocket — verified from admin panel)

- Endpoint: same host as REST, path `/ws` → `ws://…/api/v1/ws` (or `wss` on prod).
- Connect header: `token` (raw access token; **not** `Authorization`).
- Subscribe `/topic/chat/room/{roomUuid}` → frames are JSON `ChatMessage`.
- Send message: publish to `/app/chat/rooms/{roomUuid}/send` with body `{ "content": "..." }`.
- Notifications: subscribe `/user/queue/notifications` → frames are JSON `Notification`.
- Settings that work: reconnect delay 5s, heartbeat 10s.

```kotlin
// ChatRoom
uuid, userId, agentId?, status, topic, createdAt, assignedAt?, closedAt?,
customerName?, customerEmail?, customerProfilePictureUrl?, agentName?,
lastMessageContent?, lastMessageSenderType?, lastMessageAt?

// ChatMessage
uuid, roomId, senderType, senderId, content, messageType, metadata?, createdAt

// Notification
uuid, type, title, body, deepLink?, createdAt, readAt?, read
```

- Room status values: `BOT_ACTIVE`, `AWAITING_AGENT`, `ACTIVE`, `CLOSED`. A room is closed when `status == CLOSED`; awaiting a human when `AWAITING_AGENT`.
- Sender types seen: `AGENT`, `BOT`, `CUSTOMER`.

## Domain quirks

- Public identifiers are **UUID strings** (`uuid`), not numeric IDs — except internal fields like `userId`, `roomId`, `senderId`.
- Dates/timestamps are ISO-8601 strings (`createdAt`, `lastMessageAt`, …).
- Review author names come back as `userFirstName` / `userLastName` (there is no `customerName` on reviews).
- Wallet `currency` may be `null` — don't assume a currency code on wallet payloads.
- Order statuses are managed via `/order-statuses`; see `ecommerce-backend/docs/order-status-machine.md` for the state machine.