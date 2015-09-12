package com.bill.zhihu.api.net;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.net.URLConnection;

//import org.apache.http.client.CookieStore;
//import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HurlStack;

/**
 * 单例，所有request由一个request queue统一管理
 * 
 * @author Bill Lv
 *
 */
public class ZhihuVolley {

    private static final String DEFAULT_CACHE_DIR = "zhihu_cache";
    private static final int DEFAULT_THREAD_POOL_SIZE = 1;

    private RequestQueue queue;
    private static ZhihuVolley zhihuVolley;
    private Context mContext;
    private ConnectivityManager cm;

    private ZhihuVolley(Context mContext) {

        this.mContext = mContext;

//        CookieStore cookieStore = new ZhihuCookieStore();
//
//        DefaultHttpClient client = new DefaultHttpClient();
//        client.setCookieStore(cookieStore);

        // 从 Android M 开始谷歌移除了 HttpClient，这里用更加方便的 net 包中的cookiestore代替
        URLCookiesStore cookiesStore = URLCookiesStore.getInstance();
        CookieManager manager = new CookieManager(cookiesStore, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);

        File cacheDir = new File(mContext.getCacheDir(), DEFAULT_CACHE_DIR);

//        Network network = new BasicNetwork(new HttpClientStack(client));

        Network network = new BasicNetwork(new HurlStack());
        // 由于同一个cookie不能被多线程同时操作，所以把同一时间执行的线程限制为1
        queue = new RequestQueue(new DiskBasedCache(cacheDir), network,
                DEFAULT_THREAD_POOL_SIZE);

        queue.start();

        cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    public static ZhihuVolley getInstance(Context mContext) {
        if (zhihuVolley == null) {
            zhihuVolley = new ZhihuVolley(mContext);
        }
        return zhihuVolley;
    }

    public <T> void addQueue(Request<T> request) {
        if (cm.getActiveNetworkInfo() != null) {
            queue.add(request);
        } else {
            Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    public RequestQueue getQueue() {
        return queue;
    }

}
