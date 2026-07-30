# Theme Guide

## Color Palette

Primary brand color is rose/pink (`#DB4460`). Secondary is deep indigo (`#3B3A5A`).

Refer to `Color.kt` for the full `@Composable` helper functions or use `MaterialTheme.colorScheme` directly in composables.

## Typography

- **Display/Headline**: Lobster (decorative serif)  
- **Title/Body/Label**: Roboto / system sans-serif  

All standard MD3 type scales are defined in `LiquefiedTypography`.

## Spacing

Use `AppSpacing` object (`Token.kt`):

| Token | Value |
|---|---|
| xxxs | 2dp |
| xxs | 4dp |
| xs | 8dp |
| sm | 12dp |
| md | 16dp |
| lg | 20dp |
| xl | 24dp |
| xxl | 32dp |
| xxxl | 40dp |
| huge | 48dp |

## Elevation

Use `AppElevation` object:

| Token | Value |
|---|---|
| none | 0dp |
| xs | 2dp |
| sm | 4dp |
| md | 8dp |
| lg | 12dp |
| xl | 16dp |
| xxl | 24dp |

## Corner Radius

Use `AppCornerRadius` object:

| Token | Value |
|---|---|
| extraSmall | 4dp |
| small | 8dp |
| medium | 12dp |
| large | 16dp |
| extraLarge | 20dp |
| full | 30dp |
