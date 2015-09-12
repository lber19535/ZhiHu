package com.bill.zhihu.home;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;
import com.umeng.message.UmengRegistrar;


public class ActivityHome extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initToolBar();

        toggleFragment(new FragmentHome());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_home_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.token: {
                String deviceToken = UmengRegistrar.getRegistrationId(this);
                AlertDialog tokenDialog = new AlertDialog.Builder(this).setTitle("TOKEN").setMessage(deviceToken).create();
                tokenDialog.show();
                break;
            }
            case R.id.version:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
