package com.bill.zhihu.util;

import com.bill.zhihu.BuildConfig;
import com.github.moduth.blockcanary.BlockCanaryContext;

/**
 * Created by bill_lv on 2016/4/19.
 */
public class AppBlockCanaryContext  extends BlockCanaryContext{

    // this is default block threshold, you can set it by phone's performance
    @Override
    public int getConfigBlockThreshold() {
        return 500;
    }

    // if set true, notification will be shown, else only write log file
    @Override
    public boolean isNeedDisplay() {
        return BuildConfig.DEBUG;
    }

    // path to save log file
    @Override
    public String getLogPath() {
        return "/blockcanary/performance";
    }
}
