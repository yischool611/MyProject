package com.bigsong.myproject.exercise01;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/6/20.
 */
public class CustomImgContainer extends ViewGroup {
    public CustomImgContainer(Context context) {

        super(context);
    }

    public CustomImgContainer(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public CustomImgContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 计算所有childview的宽度和高度，如果根据计算的结果，设置自己的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //计算所有的childview的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        /**
         * 记录如果是wrap_content时的宽和高
         */
        int width = 0;
        int height = 0;

        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        int lHeight = 0;
        int rHeight = 0;

        int tWidth = 0;
        int bWidth = 0;

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();
            if (i == 0 || i == 1) {
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 2 || i == 3) {
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 0 || i == 2) {
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

            if (i == 1 || i == 3) {
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }
        }

        width = Math.max(tWidth,bWidth);
        height = Math.min(lHeight, rHeight);

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY)?widthSize:width,(heightMode == MeasureSpec.EXACTLY)?heightSize:height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl = 0, ct = 0, cr = 0, cb = 0;

            switch (i) {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = cParams.topMargin;
                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.topMargin - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = getHeight() - cHeight -cParams.topMargin - cParams.bottomMargin;
                    break;
            }
            cr = cl + cWidth;
            cb = ct + cHeight;
            /**
             * layout方法的参数 代表childView 相对于 parentView 的左，上，右，下的距离
             */
            childView.layout(cl, ct, cr, cb);
        }
    }

    /**
     * ViewGroup能够支持margin即可，那么我们直接使用系统的MarginLayoutParams
     *
     * @param attrs
     * @return
     */
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
