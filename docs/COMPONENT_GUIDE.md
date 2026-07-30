# Shared Component Guide

## AppButton

Multi-variant button with loading state and icon support.

```kotlin
AppButton(
    text = "Login",
    onClick = { /* handle click */ },
    variant = AppButtonVariant.PRIMARY,
    size = AppButtonSize.LARGE,
    isLoading = false,
    enabled = true,
    leadingIcon = { Icon(Icons.Default.Email, null) }
)
```

**Variants:** PRIMARY, SECONDARY, TERTIARY, DANGER, OUTLINE  
**Sizes:** SMALL, MEDIUM, LARGE

## AppTextField

Text input with validation, error display, and icon support.

```kotlin
AppTextField(
    value = email,
    onValueChange = { email = it },
    label = "Email",
    placeholder = "Enter your email",
    leadingIcon = { Icon(Icons.Default.Email, null) },
    isError = hasError,
    errorMessage = errorMsg,
    validator = InputValidators.email
)
```

## AppCard

Card with elevated/filled/outlined variants.

```kotlin
AppCard(
    onClick = { /* navigate */ },
    variant = CardVariant.ELEVATED,
) {
    // content
}
```

## PriceText

Formatted price with currency symbol.

```kotlin
PriceText(amount = 99.99)
PriceText(amount = 99.99, oldAmount = 149.99, isSale = true)
```

## RatingBar

Star rating display and input.

```kotlin
RatingBar(rating = 4.5)  // display only
RatingBar(rating = 3, onRatingChange = { /* input mode */ })
```

## QuantitySelector

Stepper for cart quantity.

```kotlin
QuantitySelector(
    quantity = 2,
    onIncrease = { /* + */ },
    onDecrease = { /* - */ }
)
```

## SearchBar

Search input with clear button.

```kotlin
SearchBar(
    query = searchQuery,
    onQueryChange = { searchQuery = it },
    onClear = { searchQuery = "" }
)
```
