package com.bill.zhihu.api;

import android.app.Application;
import android.os.Build;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        System.out.println("&Width=" + Build.MODEL.split(" ")[Build.MODEL.split(" ").length - 1].split("x")[0] +
                "&Height=" + Build.MODEL.split(" ")[Build.MODEL.split(" ").length - 1].split("x")[1]);
        System.out.println(Build.VERSION.SDK_INT);
//        System.out.println(Build.VERSION.BASE_OS);
        System.out.println(Build.VERSION.RELEASE);
    }
}