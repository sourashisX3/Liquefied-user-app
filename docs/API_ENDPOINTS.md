# API Endpoints

Base URL: `http://10.0.2.2:8083/api/v1` (dev) / `https://api.example.com/api/v1` (production)

All endpoints require `Authorization: Bearer <token>` except Auth.

## Auth

| Method | Path | Body | Response |
|---|---|---|---|
| POST | `/auth/register` | RegisterRequest | AuthResponse |
| POST | `/auth/login` | LoginRequest | AuthResponse |
| POST | `/auth/refresh` | RefreshTokenRequest | AuthResponse |
| POST | `/auth/send-otp` | SendOtpRequest | OtpResponse |
| POST | `/auth/verify-otp` | VerifyOtpRequest | OtpResponse |
| POST | `/auth/logout` | — | — |

## Home

| Method | Path | Response |
|---|---|---|
| GET | `/home` | HomeResponse (categories, brands, newArrivals, featuredProducts) |

## Products

| Method | Path | Params | Response |
|---|---|---|---|
| GET | `/products` | page, size, categorySlug, search, minPrice, maxPrice, sortBy | Paginated ProductResponse[] |
| GET | `/products/{uuid}` | — | ProductResponse |
| GET | `/products/{uuid}/similar` | — | ProductResponse[] |
| GET | `/products/{uuid}/reviews` | page, size | Paginated ReviewResponse[] |

## Categories

| Method | Path | Response |
|---|---|---|
| GET | `/categories` | CategoryResponse[] |
| GET | `/categories/tree` | CategoryResponse[] (nested) |
| GET | `/categories/{slug}` | CategoryResponse |

## Brands

| Method | Path | Response |
|---|---|---|
| GET | `/brands` | BrandResponse[] |

## Cart

| Method | Path | Body | Response |
|---|---|---|---|
| GET | `/carts` | — | CartItemResponse[] |
| POST | `/carts/{productUuid}` | CartItemRequest | CartItemResponse |
| PATCH | `/carts/{itemUuid}` | CartItemRequest | CartItemResponse |
| DELETE | `/carts/{itemUuid}` | — | — |
| DELETE | `/carts` | — | — |

## Orders

| Method | Path | Body | Response |
|---|---|---|---|
| POST | `/orders/checkout` | OrderRequest | OrderResponse |
| GET | `/orders` | page, size | Paginated OrderResponse[] |
| GET | `/orders/{uuid}` | — | OrderResponse |
| PATCH | `/orders/{uuid}/cancel` | — | OrderResponse |

## Wishlist

| Method | Path | Response |
|---|---|---|
| GET | `/wishlist` | WishlistItemResponse[] |
| POST | `/wishlist/{productUuid}` | WishlistItemResponse |
| DELETE | `/wishlist/{itemUuid}` | — |

## Reviews

| Method | Path | Body | Response |
|---|---|---|---|
| POST | `/reviews` | ReviewRequest | ReviewResponse |
| POST | `/reviews/{id}/vote` | VoteRequest | VoteResponse |

## Addresses

| Method | Path | Body | Response |
|---|---|---|---|
| GET | `/addresses` | — | AddressResponse[] |
| POST | `/addresses` | AddressRequest | AddressResponse |
| PUT | `/addresses/{uuid}` | AddressRequest | AddressResponse |
| DELETE | `/addresses/{uuid}` | — | — |

## Wallet

| Method | Path | Response |
|---|---|---|
| GET | `/wallets/me` | WalletResponse |
| GET | `/wallets/me/transactions` | WalletTransactionResponse[] |

## Profile

| Method | Path | Body | Response |
|---|---|---|---|
| GET | `/users/me` | — | UserResponse |
| PUT | `/users/me` | UpdateUserRequest | UserResponse |
| PUT | `/users/me/password` | ChangePasswordRequest | — |

## Notifications

| Method | Path | Response |
|---|---|---|
| GET | `/notifications` | Paginated NotificationResponse[] |
| GET | `/notifications/unread-count` | Map (unread count) |
| PUT | `/notifications/{uuid}/read` | — |
| PUT | `/notifications/read-all` | — |

## Chat

| Method | Path | Body | Response |
|---|---|---|---|
| GET | `/chat/rooms` | page, size | Paginated ChatRoomResponse[] |
| POST | `/chat/rooms` | CreateRoomRequest | ChatRoomResponse |
| GET | `/chat/rooms/{uuid}/messages` | page, size | Paginated ChatMessageResponse[] |
| POST | `/chat/rooms/{uuid}/messages` | SendMessageRequest | ChatMessageResponse |

## Coupon / Discount

| Method | Path | Body | Response |
|---|---|---|---|
| POST | `/coupons/validate` | CouponValidationRequest | Discount |
| GET | `/discounts/product/{productId}` | — | DiscountResponse[] |

## Standard API Response Wrapper

```json
{
  "statusCode": 200,
  "message": "Success",
  "response": { ... },
  "pagination": { "page": 0, "size": 20, "totalElements": 100, "totalPages": 5 }
}
```
