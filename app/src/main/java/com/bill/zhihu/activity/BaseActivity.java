package com.bill.zhihu.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.bill.zhihu.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 沉浸式模式兼容5.0之前的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }

    /**
     * custom which layout would be replace
     *
     * @param id
     * @param fragment
     */
    public void toggleFragment(int id, Fragment fragment) {
        toggleFragment(id, fragment, FragmentTransaction.TRANSIT_ENTER_MASK,
                FragmentTransaction.TRANSIT_EXIT_MASK);
    }

    /**
     * use R.id.fragment_container for replace
     *
     * @param fragment target fragment
     */
    public void toggleFragment(Fragment fragment) {
        toggleFragment(R.id.fragment_container, fragment);
    }

    /**
     * custom enter and exit animation
     *
     * @param id
     * @param fragment
     * @param enter
     * @param exit
     */
    public void toggleFragment(int id, Fragment fragment, int enter, int exit) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(id, fragment);
        ft.setCustomAnimations(enter, exit);
        ft.commit();
    }

    /**
     * init the base toolbar
     */
    protected void initToolBar() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.app_name));
    }


}
