package com.bill.zhihu.home;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

/**
 * recycler view item decoration
 * 
 * 算是一个对item的装饰类，一个recycler可以添加多个，比如这个是画一个简单的divider
 * 需要获取item的位置然后在ondraw中绘制一个divider，当然也可以是各种各样的drawable rect等
 * 
 * @author Bill Lv
 *
 */
public class TimeLineItemDecoration extends ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {
        // TODO Auto-generated method stub
        super.onDraw(c, parent, state);
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = parent.getChildAt(i);
            int left = v.getLeft();
            int right = v.getRight();
            int top = v.getTop();
            c.drawLine(left, top, right, top, new Paint());
        }
    }

}
