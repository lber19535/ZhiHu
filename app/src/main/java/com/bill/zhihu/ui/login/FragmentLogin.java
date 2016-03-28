package com.bill.zhihu.ui.login;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bill.zhihu.R;
import com.bill.zhihu.databinding.LoginViewBinding;
import com.bill.zhihu.model.login.User;
import com.bill.zhihu.vm.LoginVM;

/**
 * 登录
 *
 * @author Bill Lv
 */

public class FragmentLogin extends Fragment {

    private static final String TAG = "FragmentLogin";

    private LoginViewBinding binding;
    private LoginVM vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        vm = new LoginVM(binding, getActivity());
        binding.setUser(new User());
        binding.setVm(vm);

        vm.avoidCaptcha();

        return binding.getRoot();
    }

}
