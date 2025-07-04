# Consumer ProGuard rules for currency converter module
# These rules will be applied to any module that depends on this currency converter module

# Keep domain models to ensure they're available to other modules
-keep class com.abdelrahman.financetracker.currencyconverter.domain.model.** { *; }

# Keep use cases
-keep class com.abdelrahman.financetracker.currencyconverter.domain.usecase.** { *; }

# Keep repository interfaces
-keep interface com.abdelrahman.financetracker.currencyconverter.domain.repository.** { *; }

# Keep presentation models
-keep class com.abdelrahman.financetracker.currencyconverter.presentation.** { *; }

# Coroutines
-keepclassmembers class kotlinx.coroutines.** {
    public *;
}

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Gson
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation* 