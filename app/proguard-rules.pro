# ProGuard rules for TV Authentification app
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-dontwarn okio.**
-keep class retrofit2.** { *; }
-keep class com.google.gson.** { *; }
-keep class androidx.security.** { *; }
-keep class androidx.work.** { *; }
-keep class com.smartgrowthit.tvlogin.** { *; }
#-keep class com.example.tvlogin.** { *; }
