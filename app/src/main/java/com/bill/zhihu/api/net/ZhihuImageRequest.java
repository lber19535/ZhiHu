package com.bill.zhihu.api.net;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageRequest;
import com.bill.zhihu.api.utils.NetConstants;

import java.util.HashMap;
import java.util.Map;

public class ZhihuImageRequest extends ImageRequest {

    public ZhihuImageRequest(String url, Listener<Bitmap> listener,
                             int maxWidth, int maxHeight, Config decodeConfig,
                             ErrorListener errorListener) {
        super(url, listener, maxWidth, maxHeight, decodeConfig, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> header = new HashMap<String, String>();
        header.put("User-Agent", NetConstants.UA);
        header.put("Accept", "image/webp,*/*;q=0.8");
        header.put("Accept-Encoding", "gzip, deflate");
        return header;
    }

}
