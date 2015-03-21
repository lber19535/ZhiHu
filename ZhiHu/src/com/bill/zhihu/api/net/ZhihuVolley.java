package com.bill.zhihu.api.net;

import java.io.File;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;

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

        CookieStore cookieStore = new ZhihuCookieStore();

        DefaultHttpClient client = new DefaultHttpClient();
        client.setCookieStore(cookieStore);

        File cacheDir = new File(mContext.getCacheDir(), DEFAULT_CACHE_DIR);
        Network network = new BasicNetwork(new ZhihuHttpClientStack(client,
                cookieStore));

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
