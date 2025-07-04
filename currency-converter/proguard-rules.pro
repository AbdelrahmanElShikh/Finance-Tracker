# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Currency Converter Models
-keep class com.abdelrahman.financetracker.currencyconverter.domain.model.** { *; }
-keep class com.abdelrahman.financetracker.currencyconverter.data.remote.dto.** { *; }

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# OkHttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Gson
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Coroutines
-keepclassmembers class kotlinx.coroutines.** {
    public *;
}

# Hilt
-dontwarn com.google.dagger.hilt.android.internal.**
-keep class com.google.dagger.hilt.android.internal.** { *; }
-keep class * extends com.google.dagger.hilt.android.internal.** { *; }
-dontwarn dagger.hilt.android.internal.**
-keep class dagger.hilt.android.internal.** { *; }

# Keep use cases
-keep class com.abdelrahman.financetracker.currencyconverter.domain.usecase.** { *; }

# Keep repository interfaces
-keep interface com.abdelrahman.financetracker.currencyconverter.domain.repository.** { *; } 