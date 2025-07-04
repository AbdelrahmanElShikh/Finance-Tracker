# Consumer ProGuard rules for domain module
# These rules will be applied to any module that depends on this domain module

# Keep domain models to ensure they're available to other modules
-keep class com.abdelrahman.financetracker.domain.model.** { *; }

# Keep use cases
-keep class com.abdelrahman.financetracker.domain.usecase.** { *; }

# Keep repository interfaces
-keep interface com.abdelrahman.financetracker.domain.repository.** { *; }

# Coroutines
-keepclassmembers class kotlinx.coroutines.** {
    public *;
}

# Javax inject
-keep class javax.inject.** { *; }
-dontwarn javax.inject.** 