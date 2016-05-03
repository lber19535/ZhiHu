# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in create.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# realm
#-keep class io.realm.annotations.RealmModule
#-keep @io.realm.annotations.RealmModule class *
#-dontwarn javax.**
#-dontwarn io.realm.**

# bugly
-keep public class com.tencent.bugly.**{*;}
-dontwarn com.tencent.bugly.**

# app
-keep public class com.bill.zhihu.R$*{
    public static final int *;
}
-keep public class com.bill.zhihu.api.bean.**{
    *;
}
#-dontwarn android.support.**
#-keep class android.support.** { *; }
#-keep interface android.support.** { *; }

# retrofit2
-dontwarn java.lang.invoke.**
-dontwarn rx.internal.util.unsafe.**
-dontwarn java.lang.reflect.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

-keepattributes EnclosingMethod

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-keepclasseswithmembers interface * {
    @retrofit2.http.* <methods>;
}

# rxjava
#-keep class rx.** {*;}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
   long producerNode;
   long consumerNode;
}

# okio
-dontwarn okio.**
-dontwarn com.squareup.**
#-keep class com.squareup.** { *; }
#-keep interface com.squareup.** { *; }

# jackson
-keepattributes *Annotation*,EnclosingMethod,Signature
-keepnames class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.databind.**
-keep class org.codehaus.** { *; }
-keepclassmembers public final enum org.codehaus.jackson.annotate.JsonAutoDetect$Visibility {
    public static final org.codehaus.jackson.annotate.JsonAutoDetect$Visibility *;
}
-keep public class com.bill.zhihu.api.bean.** {
    public void set*(***);
    public *** get*();
}

# js
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# remove log
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
-assumenosideeffects class com.orhanobut.logger.Logger {
     public static *** init(...);
     public static void clear();
     public static *** t(...);
     public static void d(...);
     public static void e(...);
     public static void i(...);
     public static void v(...);
     public static void w(...);
     public static void wtf(...);
     public static void json(...);
     public static void xml(...);
}
-assumenosideeffects class com.orhanobut.logger.Printer {
    public *** ***(...);
}
-assumenosideeffects class com.orhanobut.logger.Settings {
    public *** logLevel(...);
}
