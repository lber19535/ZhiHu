package com.bill.zhihu.presenter;

import android.app.Activity;

import com.bill.zhihu.R;
import com.bill.zhihu.databinding.StartPageViewBinding;
import com.bill.zhihu.model.StartPageModel;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by bill_lv on 2016/3/24.
 */
public class StartPagePresenter {

    private StartPageViewBinding viewBinding;
    private Activity activity;
    private StartPageModel model;

    private int SLOGAN_ANIME_DURATION = 500;


    public StartPagePresenter(StartPageViewBinding viewBinding, Activity activity) {
        this.viewBinding = viewBinding;
        this.activity = activity;
        this.model = new StartPageModel(activity);
    }

    /**
     * Run start page animation
     *
     * @return
     */
    public Observable<Boolean> initStartPage() {
        return model.initStartPage()
                .delay(SLOGAN_ANIME_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean login) {
                        viewBinding.slogan.animateText("");
                        viewBinding.name.animateText("");
                        viewBinding.slogan.animateText(activity.getText(R.string.start_slogan));
                        return login;
                    }
                })
                .delay(SLOGAN_ANIME_DURATION * 2, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean login) {
                        viewBinding.slogan.animateText("");
                        viewBinding.name.animateText(activity.getText(R.string.start_slogan_name));
                        return login;
                    }
                }).delay(SLOGAN_ANIME_DURATION * 2, TimeUnit.MILLISECONDS);
    }
}
