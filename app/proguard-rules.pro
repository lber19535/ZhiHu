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
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-dontwarn javax.**
-dontwarn io.realm.**

# bugly
-keep public class com.tencent.bugly.**{*;}

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
-dontwarn retrofit2.**
-dontwarn java.lang.invoke.**
-dontwarn rx.internal.util.unsafe.**
-dontwarn java.lang.reflect.**
-dontwarn retrofit.**
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keepattributes Signature
-keepattributes Exceptions

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

