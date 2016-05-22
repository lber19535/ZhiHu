package com.bill.zhihu.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;
import com.bill.zhihu.constant.IntentConstant;
import com.bill.zhihu.transformer.ZhihuSwipeBackTransformer;
import com.bill.zhihu.util.SwipeBackUtils;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by bill_lv on 2016/4/26.
 */
public class ActivityLargeImage extends BaseActivity {

    private static final String TAG = "ActivityLargeImage";

    private PhotoViewAttacher attacher;
    private ArrayList<CharSequence> imgUrls;

    @Bind(R.id.large_img)
    ImageView imageView;
    @Bind(R.id.loading_percentage)
    ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init the swipe back
        SwipeBackUtils.attach(this, R.layout.activity_large_image);

        ButterKnife.bind(this);
        Intent intent = getIntent();

        // init toolbar
        initToolBar();

        if (intent.getAction().equals(IntentConstant.INTENT_ACTION_LARGE_IMAGE)) {
            progressBar.show();
            attacher = new PhotoViewAttacher(imageView);

            String imgUrl = intent.getStringExtra(IntentConstant.INTENT_LARGE_IMAGE_URL);

            // img url array for pager
            imgUrls = intent.getCharSequenceArrayListExtra(IntentConstant.INTENT_LARGE_IMAGE_URL_ARRAY);

            ImageLoader.getInstance().displayImage(imgUrl, imageView, null, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    Logger.t(TAG).d("start load image " + imageUri);
                    progressBar.setIndeterminate(false);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Logger.t(TAG).d("load image complete" + imageUri);
                    progressBar.hide();
                    imageView.setVisibility(View.VISIBLE);
                    attacher.update();
                }
            }, new ImageLoadingProgressListener() {
                @Override
                public void onProgressUpdate(String imageUri, View view, int current, int total) {
                    // if the img have already cached, it will not call this listener
                    progressBar.setProgress(current / total * 100);
                }
            });

        } else {
            Logger.t(TAG).w("intent action is " + intent.getAction() + " not INTENT_ACTION_LARGE_IMAGE");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_large_img_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_img:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
