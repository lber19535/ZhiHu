package com.bill.zhihu.login;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.bill.zhihu.api.cmd.CmdLogin;
import com.bill.zhihu.api.utils.ToastUtil;
import com.bill.zhihu.api.utils.ZhihuLog;
import com.bill.zhihu.home.ActivityHome;

/**
 * 登录
 * 
 * @author Bill Lv
 *
 */

public class FragmentLogin extends Fragment {

    private static final String TAG = "FragmentLogin";

    private Button loginBtn;
    private EditText accountEdt;
    private EditText pwdEdt;
    private EditText captchaEdt;
    private ImageView captchaIv;
    // 输入账号的密码的layout
    private View loginLayout;

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        accountEdt = (EditText) rootView.findViewById(R.id.login_account);
        pwdEdt = (EditText) rootView.findViewById(R.id.login_pwd);
        captchaEdt = (EditText) rootView.findViewById(R.id.login_captcha);
        loginBtn = (Button) rootView.findViewById(R.id.login_btn);
        captchaIv = (ImageView) rootView.findViewById(R.id.login_captcha_img);

        loginLayout = rootView.findViewById(R.id.login_layout);

        // 先登录一次以获取提交时所需的cookie
        login("", "", null);

        loginBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                loginBtn.setClickable(false);

                String account = accountEdt.getEditableText().toString();
                String pwd = pwdEdt.getEditableText().toString();
                String captcha = captchaEdt.getEditableText().toString();

                if (account.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.account_empty),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                login(account, pwd, captcha);

            }
        });
    }

    private void login(String account, String pwd, String captcha) {
        CmdLogin login = new CmdLogin(account, pwd, captcha);
        login.setOnCmdCallBack(new CmdLogin.CallbackListener() {

            @Override
            public void callback(int code, Bitmap captcha) {
                ZhihuLog.d(TAG, code);
                switch (code) {
                case CmdLogin.LOGIN_SUCCESS:
                    ToastUtil.showShortToast(getResources().getString(
                            R.string.login_success));
                    Intent intent = new Intent(getActivity(),
                            ActivityHome.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(
                            R.anim.activity_login_home_in_transition,
                            R.anim.activity_login_home_out_transition);
                    getActivity().finish();

                    break;
                case CmdLogin.LOGIN_FAILED:
                    captchaIv.setImageBitmap(captcha);
                    break;

                default:
                    break;
                }
                loginBtn.setClickable(true);
            }
        });
        ZhihuApi.execCmd(login);
    }

    private void loginSuccessAnime() {
        // loginLayout
        // loginBtn
    }

}
