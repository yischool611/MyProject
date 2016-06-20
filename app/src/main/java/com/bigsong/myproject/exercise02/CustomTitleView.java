package com.bigsong.myproject.exercise02;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.bigsong.myproject.R;

/**
 * Created by Administrator on 2016/6/20.
 */
public class CustomTitleView extends View {
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    private Rect mBounds;
    private Paint mPaint;

    public CustomTitleView(Context context) {

        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }


    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取我们所定义的自定义样式属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTitleView_csTitleText:
                    mTitleText = typedArray.getString(attr);
                    break;
                case R.styleable.CustomTitleView_csTitleTextColor:
                    mTitleTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_csTitleTextSize:
                    //需要了解TypedValue的转换
                    mTitleTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        // recycle方法不了解
        typedArray.recycle();

        //获取绘制文本的宽和高
        mPaint = new Paint();
//            mPaint.setColor(mTitleTextColor);
        mPaint.setTextSize(mTitleTextSize);
        mBounds = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBounds);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mTitleTextColor);
        /**
         * 一般在自定义控件的时候getMeasuredWidth/getMeasuredHeight它的赋值在View的setMeasuredDimension中，
         * 所以有时可以在onMeasure方法中看到利用getMeasuredWidth/getMeasuredHeight初始化别的参数。
         * 而getWidth/getHeight一直在onLayout完成后才会被赋值。一般情况下，如果都完成了赋值，两者值是相同的。
         */
        canvas.drawText(mTitleText, getMeasuredWidth() / 2 - mBounds.width() / 2, getMeasuredHeight() / 2 + mBounds.height() / 2, mPaint);
    }
}
