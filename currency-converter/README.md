# Currency Converter Module

A modular currency converter feature built with clean architecture and modern Android development practices.

## Architecture

This module follows **Clean Architecture** principles with three main layers:

### 📁 Domain Layer (`domain/`)
- **Models**: `Currency`, `ExchangeRate`, `ConversionResult`
- **Repository Interface**: `CurrencyRepository`
- **Use Cases**:
  - `GetSupportedCurrenciesUseCase`
  - `ConvertCurrencyUseCase`
  - `GetExchangeRatesUseCase`

### 📁 Data Layer (`data/`)
- **Remote**: API service and DTOs for fetching live exchange rates
- **Local**: In-memory cache for storing exchange rates
- **Repository Implementation**: `CurrencyRepositoryImpl`

### 📁 Presentation Layer (`presentation/`)
- **UI**: Jetpack Compose screens and components
- **ViewModel**: `CurrencyConverterViewModel` with state management
- **State Management**: Uses MVI pattern with sealed classes

## Features

✅ **Real-time Currency Conversion**: Convert between 35+ supported currencies  
✅ **Plugin Architecture**: Choose between multiple exchange rate providers  
✅ **Smart Caching**: 1-hour cache to reduce API calls and improve performance  
✅ **Offline Support**: Fallback to mock data when network is unavailable  
✅ **Modern UI**: Beautiful Material Design 3 interface  
✅ **Provider Settings**: Complete UI to configure and switch providers  
✅ **Recent Conversions**: Track the last 5 conversions  
✅ **Currency Swap**: Quick swap between from/to currencies  
✅ **Error Handling**: Graceful error handling with user-friendly messages  
✅ **Auto Fallback**: Automatic provider switching when primary fails  

## Supported Currencies

The module supports 35+ major world currencies including:
- 🇺🇸 USD (US Dollar)
- 🇪🇺 EUR (Euro)
- 🇬🇧 GBP (British Pound)
- 🇯🇵 JPY (Japanese Yen)
- 🇨🇳 CNY (Chinese Yuan)
- And many more...

## Technical Stack

- **🏗️ Architecture**: Clean Architecture + MVVM + Plugin System
- **🎨 UI**: Jetpack Compose + Material Design 3
- **⚡ Async**: Kotlin Coroutines + Flow
- **🌐 Networking**: Retrofit + OkHttp
- **🗃️ Caching**: In-memory with thread-safe operations
- **💉 DI**: Hilt (Dagger)
- **🔌 Plugins**: Extensible provider system
- **🧪 Testing**: JUnit + Mockito + Coroutines Test

## 🔌 Plugin Architecture

The module features a sophisticated **plugin architecture** that allows users to choose between different currency exchange rate providers:

### Available Providers:
- **🎭 Mock Provider**: Offline demo provider (default)
- **🌐 ExchangeRate-API**: Free tier with 1000 requests/month
- **💼 Fixer.io**: Professional API with EUR base currency

### Provider Management:
```kotlin
// Switch providers programmatically
setActiveProviderUseCase("exchangerate-api")

// Configure with API key
setActiveProviderUseCase("fixer-io", ProviderConfig(apiKey = "your-key"))

// Provider Settings UI
ProviderSettingsScreen(onNavigateBack = { ... })
```

### Benefits:
- **🔄 Automatic Fallbacks**: If one provider fails, automatically switches to another
- **⚙️ User Control**: Complete settings UI for provider management
- **🔧 Extensible**: Easy to add new providers
- **📊 Monitoring**: Health checks and usage statistics
- **🆓 Free Options**: Multiple free tier providers available

For detailed information, see [PLUGIN_ARCHITECTURE.md](PLUGIN_ARCHITECTURE.md)

## Usage

### 1. Add Module Dependency
```kotlin
implementation(project(":currency-converter"))
```

### 2. Navigate to Currency Converter
```kotlin
CurrencyConverterScreen(
    onNavigateBack = { /* Handle navigation */ }
)
```

### 3. Inject Use Cases (Optional)
```kotlin
@Inject
lateinit var convertCurrencyUseCase: ConvertCurrencyUseCase

// Use in your own features
val result = convertCurrencyUseCase("USD", "EUR", 100.0)
```

## API Integration

The module is designed to work with exchange rate APIs. Currently configured for:
- **Primary**: ExchangeRate-API.com (free tier)
- **Fallback**: Mock data for offline/demo usage

To use with a real API:
1. Get an API key from your preferred exchange rate provider
2. Update the `apiKey` in `CurrencyRepositoryImpl`
3. Modify the API endpoints in `CurrencyApiService` if needed

## Demo Mode

The module includes comprehensive mock data, so it works perfectly without any API setup. This makes it great for:
- Development and testing
- Demos and presentations
- Offline usage

## File Structure

```
currency-converter/
├── src/main/java/com/abdelrahman/financetracker/currencyconverter/
│   ├── data/
│   │   ├── local/CurrencyCache.kt
│   │   ├── remote/CurrencyApiService.kt
│   │   ├── remote/dto/ExchangeRateDto.kt
│   │   └── repository/CurrencyRepositoryImpl.kt
│   ├── domain/
│   │   ├── model/Currency.kt
│   │   ├── model/ExchangeRate.kt
│   │   ├── repository/CurrencyRepository.kt
│   │   └── usecase/
│   ├── presentation/
│   │   ├── ui/CurrencyConverterScreen.kt
│   │   └── viewmodel/CurrencyConverterViewModel.kt
│   └── di/CurrencyConverterModule.kt
├── src/test/java/
└── build.gradle.kts
```

## Contributing

1. Follow the existing architecture patterns
2. Add tests for new features
3. Update this README for significant changes
4. Use conventional commit messages

## License

This module is part of the Finance Tracker application. 