package com.bill.zhihu.api.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * 解压Gzip压缩过的工具类
 * Created by Bill-pc on 2015/6/27.
 */
public class GzipUtils {

    /**
     * 解压Gzip格式的String，解码方式为deflate
     *
     * @param byteArray
     * @return
     */
    public static String decodeString(byte[] byteArray) {
        GZIPInputStream gis = null;
        StringBuffer sb = new StringBuffer();
        try {
            gis = new GZIPInputStream(new ByteArrayInputStream(byteArray));
            InputStreamReader isr = new InputStreamReader(gis);
            BufferedReader br = new BufferedReader(isr);
            String b;
            while ((b = br.readLine()) != null) {
                sb.append(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                gis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
