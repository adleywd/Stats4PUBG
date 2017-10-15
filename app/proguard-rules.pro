# Add project specific ProGuard rules here.
## TODO STILL NO WORKING ##
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

-dontwarn org.ini4j.** # Ignore warning for missing classes in ini4j
-dontwarn javax.**
-keep class android.support.v7.widget.SearchView { *; }

# Retrofit
-dontwarn okhttp3.**
-dontwarn okio.**
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class br.com.adley.ipubg.data.models.MatchHistory.** { *; }
-keep class br.com.adley.ipubg.data.models.Player.** { *; }
-keep class br.com.adley.ipubg.data.models.Season.** { *; }
-keep class br.com.adley.ipubg.data.models.Stats.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

##---------------End: proguard configuration for Gson  ----------

