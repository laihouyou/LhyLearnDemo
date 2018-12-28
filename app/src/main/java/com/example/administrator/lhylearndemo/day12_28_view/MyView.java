package com.example.administrator.lhylearndemo.day12_28_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=getMySize(100,widthMeasureSpec);
        int height=getMySize(100,heightMeasureSpec);
        if (width<height){
            height=width;
        }else {
            width=height;
        }
        setMeasuredDimension(width,height);
    }

    private int getMySize(int deftsize,int measureSize){
        int dataSize=deftsize;
        int mode=MeasureSpec.getMode(measureSize);
        int size=MeasureSpec.getSize(measureSize);      //父控件提供给子view的参考大小

        switch (mode){
            /**
             *UNSPECIFIED(未指定),父控件对子控件不加任何束缚，子元素可以得到任意想要的大小，
             * 这种MeasureSpec一般是由父控件自身的特性决定的。比如ScrollView，
             * 它的子View可以随意设置大小，无论多高，都能滚动显示，这个时候，size一般就没什么意义。
             */
            case MeasureSpec.UNSPECIFIED:     //
                dataSize=deftsize;
                break;
            /**
             * 当控件的layout_width或layout_height指定为WRAP_CONTENT时，
             * 控件大小一般随着控件的子空间或内容进行变化，
             * 此时控件尺寸只要不超过父控件允许的最大尺寸即可。
             * 因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
              */
            case MeasureSpec.AT_MOST:
                dataSize=size/2;
                break;
            /**
             * 当我们将控件的layout_width或layout_height指定为具体数值时 或者 match_parent
             */
            case MeasureSpec.EXACTLY:
                dataSize=dataSize;
                break;
        }

        return dataSize;
    }
}
