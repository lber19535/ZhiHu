package com.bill.zhihu.answer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bill.zhihu.R;
import com.bill.zhihu.activity.BaseActivity;
import com.bill.zhihu.api.bean.TimeLineItem;
import com.bill.zhihu.home.TimeLineViewHolder;

/**
 * Created by Bill-pc on 5/22/2015.
 */
public class ActivityAnswer extends BaseActivity {


    private TextView questionTv;
    private ImageView moreIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        this.moreIv = (ImageView) findViewById(R.id.more);
        this.questionTv = (TextView) findViewById(R.id.question);

        TimeLineItem item = getIntent().getParcelableExtra(TimeLineViewHolder.EXSTRA_ITEM);
        questionTv.setText(item.getQuestion());

        moreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreMenu();
            }
        });

        toggleFragment(new FragmentAnswer());
    }

    private void showMoreMenu() {
        PopupMenu menu = new PopupMenu(this, moreIv);
        menu.getMenuInflater().inflate(R.menu.activity_answer_menu, menu.getMenu());
        menu.show();
    }
}
