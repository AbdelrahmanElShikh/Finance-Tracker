# Consumer ProGuard rules for data module
# These rules will be applied to any module that depends on this data module

# Keep data entities
-keep class com.abdelrahman.financetracker.data.local.entity.** { *; }

# Keep DAO interfaces
-keep interface com.abdelrahman.financetracker.data.local.dao.** { *; }

# Keep database classes
-keep class com.abdelrahman.financetracker.data.local.database.** { *; }

# Keep repository implementations
-keep class com.abdelrahman.financetracker.data.repository.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *
-dontwarn androidx.room.paging.**

# Hilt
-dontwarn com.google.dagger.hilt.android.internal.**
-keep class com.google.dagger.hilt.android.internal.** { *; }

# Gson (if using for converters)
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer 