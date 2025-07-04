# Currency Converter Plugin Architecture

The Currency Converter module now features a powerful **plugin architecture** that allows users to choose between different currency exchange rate providers through dependency injection. This makes the system highly flexible, extensible, and user-configurable.

## 🏗️ Architecture Overview

### Plugin Interface
All currency providers implement the `CurrencyProviderPlugin` interface, which defines:
- **Metadata**: Provider name, description, API requirements
- **Capabilities**: Supported currencies, rate limits, free tier availability
- **Core Methods**: Exchange rate fetching, health checks, usage statistics

### Plugin Registry
The `CurrencyPluginRegistry` manages all available providers:
- **Registration**: Automatic plugin discovery and registration
- **Selection**: Active provider management with fallback logic
- **Health Monitoring**: Plugin availability tracking

### Dependency Injection
Hilt provides seamless integration:
- **Automatic Setup**: Plugins are auto-registered at startup
- **Configuration**: Runtime provider switching
- **Lifecycle Management**: Proper initialization and cleanup

## 🔌 Available Plugins

### 1. Mock Provider (`mock`)
- **Type**: Offline demo provider
- **API Key**: Not required
- **Free Tier**: Yes (unlimited)
- **Features**: 
  - Always available
  - Realistic exchange rates with small variations
  - 35+ supported currencies
  - Simulated network latency

### 2. ExchangeRate-API (`exchangerate-api`)
- **Type**: Real-time API provider
- **API Key**: Optional (free tier available)
- **Free Tier**: Yes (1000 requests/month)
- **Features**:
  - Live exchange rates
  - High accuracy
  - Good coverage of major currencies

### 3. Fixer.io (`fixer-io`)
- **Type**: Professional API provider
- **API Key**: Required
- **Free Tier**: Yes (100 requests/month)
- **Features**:
  - Professional-grade data
  - Historical rates support
  - EUR base currency (free tier)

## 🚀 Usage Guide

### Basic Usage (Default Setup)
The system works out-of-the-box with the **Mock Provider** as the default:
```kotlin
// Currency conversion works immediately
val result = convertCurrencyUseCase("USD", "EUR", 100.0)
```

### Switching Providers
Users can change providers through the settings UI:
```kotlin
// Navigate to provider settings
ProviderSettingsScreen(
    onNavigateBack = { navController.popBackStack() }
)
```

### Programmatic Provider Selection
```kotlin
@Inject
lateinit var setActiveProviderUseCase: SetActiveProviderUseCase

// Switch to ExchangeRate-API
val result = setActiveProviderUseCase(
    providerId = "exchangerate-api",
    config = ProviderConfig()
)

// Configure Fixer.io with API key
val result = setActiveProviderUseCase(
    providerId = "fixer-io",
    config = ProviderConfig(apiKey = "your-api-key")
)
```

### Get Available Providers
```kotlin
@Inject
lateinit var getAvailableProvidersUseCase: GetAvailableProvidersUseCase

val providers = getAvailableProvidersUseCase()
providers.forEach { provider ->
    println("${provider.providerName}: ${provider.description}")
}
```

## 🔧 Adding New Providers

### 1. Implement the Plugin Interface
```kotlin
class YourCustomPlugin @Inject constructor(
    // Your dependencies
) : CurrencyProviderPlugin {
    
    override val providerId = "your-provider"
    override val providerName = "Your Provider"
    override val description = "Description of your provider"
    override val requiresApiKey = true
    override val isFreeTier = false
    override val requestsPerHour = 1000
    override val supportedBaseCurrencies = listOf("USD", "EUR")
    
    override suspend fun initialize(config: ProviderConfig): Result<Unit> {
        // Initialize your provider
    }
    
    override suspend fun getExchangeRates(baseCurrency: String): Result<ExchangeRateResponse> {
        // Implement rate fetching
    }
    
    override suspend fun getExchangeRate(fromCurrency: String, toCurrency: String): Result<ExchangeRate> {
        // Implement single rate fetching
    }
    
    override suspend fun isHealthy(): Boolean {
        // Check provider health
    }
    
    override suspend fun getUsageStats(): ProviderUsageStats {
        // Return usage statistics
    }
}
```

### 2. Register in Dependency Injection
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object YourProviderModule {
    
    @Provides
    @Singleton
    fun provideYourCustomPlugin(): YourCustomPlugin {
        return YourCustomPlugin()
    }
}
```

### 3. Update Plugin Registry
Add your plugin to `CurrencyConverterModule`:
```kotlin
@Provides
@Singleton
fun provideCurrencyPluginRegistry(
    mockPlugin: MockCurrencyPlugin,
    exchangeRateApiPlugin: ExchangeRateApiPlugin,
    fixerIoPlugin: FixerIoPlugin,
    yourCustomPlugin: YourCustomPlugin // Add your plugin
): CurrencyPluginRegistry {
    // Register your plugin in the initialization
}
```

## 🛡️ Error Handling & Fallbacks

### Automatic Fallback
The system automatically falls back to alternative providers:
1. Try active provider
2. If failed, find best available provider
3. Prioritize free tier providers
4. Use provider preference order

### Health Monitoring
```kotlin
// Check provider health
val isHealthy = provider.isHealthy()

// Get usage statistics
val stats = provider.getUsageStats()
println("Success rate: ${stats.successRate}")
println("Avg response time: ${stats.averageResponseTime}ms")
```

### Error Recovery
```kotlin
// The repository handles errors gracefully
val result = currencyRepository.getExchangeRates("USD")
if (result.isFailure) {
    // System automatically tries alternative providers
    // Users see meaningful error messages
}
```

## 📊 Configuration Options

### Provider Configuration
```kotlin
val config = ProviderConfig(
    apiKey = "your-api-key",
    baseUrl = "https://custom-api.com/",
    timeout = 30_000L,
    retryCount = 3,
    customHeaders = mapOf("Authorization" to "Bearer token"),
    additionalParams = mapOf("premium" to true)
)
```

### Registry Management
```kotlin
// Get registry statistics
val stats = pluginRegistry.getRegistryStats()
println("Total plugins: ${stats.totalPlugins}")
println("Healthy plugins: ${stats.healthyPlugins}")
println("Free plugins: ${stats.freePlugins}")

// Find best available plugin
val bestPlugin = pluginRegistry.findBestAvailablePlugin(
    requiresFreeTier = true,
    preferredProviders = listOf("exchangerate-api", "mock")
)
```

## 🎨 UI Components

### Provider Settings Screen
A complete settings UI allows users to:
- View all available providers
- See provider details (free/paid, API key requirements, limits)
- Switch between providers
- Configure API keys
- View provider status

### Integration Example
```kotlin
// Add to your navigation
composable("provider-settings") {
    ProviderSettingsScreen(
        onNavigateBack = { navController.popBackStack() }
    )
}

// Link from currency converter
IconButton(onClick = { 
    navController.navigate("provider-settings") 
}) {
    Icon(Icons.Default.Settings, "Provider Settings")
}
```

## 🔄 Plugin Lifecycle

1. **Registration**: Plugins auto-register at app startup
2. **Initialization**: Providers initialize with configuration
3. **Health Checks**: Continuous health monitoring
4. **Usage Tracking**: Statistics collection for each provider
5. **Fallback Logic**: Automatic provider switching on failures

## 🏆 Benefits

- **✅ Flexibility**: Easy provider switching
- **✅ Reliability**: Automatic fallbacks and error handling
- **✅ Extensibility**: Simple to add new providers
- **✅ User Control**: Settings UI for provider management
- **✅ Testability**: Mock provider for development/testing
- **✅ Performance**: Caching and usage optimization
- **✅ Monitoring**: Health checks and usage statistics

## 🔮 Future Enhancements

- **Provider Ratings**: User feedback and automatic quality scoring
- **Load Balancing**: Distribute requests across multiple providers
- **Cost Optimization**: Automatic switching based on usage limits
- **Historical Data**: Provider performance tracking over time
- **A/B Testing**: Compare provider accuracy and performance

This plugin architecture makes the Currency Converter module highly adaptable to different use cases, from development and testing to production deployment with various API providers. 