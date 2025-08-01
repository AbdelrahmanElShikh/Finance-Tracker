# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep domain models
-keep class com.abdelrahman.financetracker.domain.model.** { *; }

# Keep use cases
-keep class com.abdelrahman.financetracker.domain.usecase.** { *; }

# Keep repository interfaces
-keep class com.abdelrahman.financetracker.domain.repository.** { *; }

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

# Javax inject
-keep class javax.inject.** { *; }
-dontwarn javax.inject.** 