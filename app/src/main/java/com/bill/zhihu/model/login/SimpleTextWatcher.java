package com.bill.zhihu.model.login;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by bill_lv on 2016/3/28.
 */
public abstract class SimpleTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextChanged(s.toString());
    }

    public abstract void onTextChanged(String s);

    @Override
    public void afterTextChanged(Editable s) {

    }
}
