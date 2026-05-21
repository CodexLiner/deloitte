# Product Browser (Kotlin Multiplatform)

## Summary of Business Requirements
Build a Kotlin Multiplatform Mobile app that browses products from the DummyJSON API with shared UI for Android and iOS. The app must show a product list with search, support navigation to a product detail screen, and follow MVVM with Clean Architecture. Networking must use Ktor Client and JSON parsing must use kotlinx.serialization. UI state is managed via StateFlow.

## Architecture Overview
The project is a KMP multi-module setup using Compose Multiplatform for shared UI and MVVM for presentation logic. Clean Architecture is enforced with separate data, domain, and presentation layers.

Dependency direction:
`presentation → domain ← data`

Module responsibilities:
- `composeApp`: Presentation layer. Compose UI, navigation, ViewModels, UI state with `StateFlow`.
- `domain`: Business models, repository interfaces, and use cases (e.g., get product list, search, detail). No dependencies on data or UI.
- `data`: DTOs, Ktor remote data source, repository implementations, and DTO-to-domain mappers.
- `network`: Ktor client configuration.
- `di`: Manual dependency wiring.
- `iosApp`: iOS entry point.

## Build and Run

### Android
From the project root:
```bash
./gradlew :composeApp:assembleDebug
```
To run on a device or emulator, use Android Studio run configuration for `composeApp`.

### iOS
Open `iosApp` in Xcode and run on a simulator or device. The shared UI is provided by `composeApp`.

## Trade-offs and Assumptions
- Manual dependency injection is used to keep the setup simple and avoid DI framework overhead.
- The app targets the DummyJSON API and assumes availability and stable response formats.
- Error handling focuses on user-visible states (Loading/Success/Error); retry/backoff policies are minimal.
- Tests include at least one unit test at the use case layer; full UI or integration testing is out of scope.
