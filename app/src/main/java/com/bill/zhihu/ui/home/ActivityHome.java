package com.bill.zhihu.ui.home;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;
import com.bill.zhihu.constant.ColorConstant;
import com.bill.zhihu.databinding.HomePageViewBinding;
import com.bill.zhihu.presenter.home.HomePresenter;
import com.tencent.bugly.crashreport.CrashReport;

import de.hdodenhof.circleimageview.CircleImageView;


public class ActivityHome extends BaseActivity implements DrawerInfoCallback {
    private DrawerLayout drawerLayout;
    private NavigationView drawer;
    private HomePageViewBinding binding;
    private HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        presenter = new HomePresenter(binding, this);
        binding.setPresenter(presenter);
        presenter.setCallback(this);

        initView();

        presenter.playLoadingAnim();

        presenter.loadHomePage();
        presenter.loadPeopleInfo();

    }

    // 初始化相关的view参数
    private void initView() {

        // init toolbar
        initToolBar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer = (NavigationView) findViewById(R.id.drawer);
        drawer.setClickable(true);
        drawer.setItemIconTintList(null);
        drawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                System.out.println(item.getTitle());
                switch (item.getItemId()) {
                    case R.id.change_style:
                        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    default:
                        break;
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
        // navigation menu icon
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // 设置下拉刷新圆圈的颜色
        binding.swipeToRefresh.setColorSchemeResources(ColorConstant.SWIPE_COLOR_SCHEMA);

        // 设置layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        binding.timeLineList.setLayoutManager(layoutManager);
        // divider
        binding.timeLineList.addItemDecoration(new TimeLineItemDecoration());

        /*
         third party float action button don't have setOnClickListener method, it just extends ImageButton,
         so, in layout, we need view which have own setOnClickListener method, not to use extends, because bind method by attribute
         depends on reflect, and in here the databinding cannot find method in super class by reflect
          */
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickUptoTop(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_home_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.version:
                CrashReport.postCatchedException(new Throwable("test"));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setHeadImage(Drawable drawable) {
        CircleImageView headView = (CircleImageView) drawer.getHeaderView(0).findViewById(R.id.header);
        headView.setImageDrawable(drawable);
    }

    @Override
    public void setHeadName(String name) {
        AppCompatTextView headNameView = (AppCompatTextView) drawer.getHeaderView(0).findViewById(R.id.name);
        headNameView.setText(name);
    }

    @Override
    public void setHeadLine(String headline) {
        AppCompatTextView headLineView = (AppCompatTextView) drawer.getHeaderView(0).findViewById(R.id.headline);
        headLineView.setText(headline);
    }
}
