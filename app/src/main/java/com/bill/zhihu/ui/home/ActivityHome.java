package com.bill.zhihu.ui.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;


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
//                final String deviceToken = UmengRegistrar.getRegistrationId(this);
//                AlertDialog.Builder tokenDialog = new AlertDialog.Builder(this).setTitle("TOKEN").setMessage(deviceToken).setPositiveButton("复制", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//                        clipboard.setText(deviceToken);
//                        ToastUtil.showShortToast("已复制到剪贴板");
//                    }
//                });
//                tokenDialog.show();
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
