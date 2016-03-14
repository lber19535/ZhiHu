package com.bill.zhihu.login;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bill.zhihu.R;
import com.bill.zhihu.api.ZhihuApi;
import com.bill.zhihu.api.bean.PeopleBasicResponse;
import com.bill.zhihu.api.factory.ApiFactory;
import com.bill.zhihu.home.ActivityHome;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * 登录
 *
 * @author Bill Lv
 */

public class FragmentLogin extends Fragment {

    private static final String TAG = "FragmentLogin";
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.login_account)
    EditText accountEdt;
    @Bind(R.id.login_pwd)
    EditText pwdEdt;
    // 输入账号的密码的layout
    @Bind(R.id.login_layout)
    View loginLayout;
    @Bind(R.id.captcha_layout)
    View captchaLayout;

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {

        loginBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                loginBtn.setClickable(false);

                String account = accountEdt.getEditableText().toString();
                String pwd = pwdEdt.getEditableText().toString();

                if (account.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.account_empty),
                            Toast.LENGTH_SHORT).show();
                    loginBtn.setClickable(true);
                    return;
                }
                login(account, pwd);
                loginSuccessAnime();

            }
        });
    }

    // 登陆
    private void login(String account, String pwd) {

        ApiFactory.createLoginApi().captcha();
        ZhihuApi.login(account, pwd).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                Logger.d("log in complete");
                loginBtn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("log in failed");
                Logger.d(e.toString());
            }

            @Override
            public void onNext(Boolean bool) {
                Logger.d("log in " + bool);
                ZhihuApi.getPeopleSelfBasic();
                Intent intent = new Intent(getActivity(), ActivityHome.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void loginSuccessAnime() {
        // loginLayout
        // loginBtn
    }

}
