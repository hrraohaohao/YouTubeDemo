package com.example.youtubedemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private View ItemDecorationView;//间隔视图
    private Drawable ItemDecorationViewDrawble;//间隔视图的drawble
    private int ItemDecorationViewWidth = 0;//间隔视图的宽度
    private int ItemDecorationViewHeight = 0;//间隔视图的高度

    public DividerGridItemDecoration(Context context, int orientation, View ItemDecorationView) {
        //判断传入的view以及传入的view的params是否为空，若其中一个为空则使用默认样式
        if (ItemDecorationView == null || ItemDecorationView.getLayoutParams() == null) {
            ItemDecorationView = new ImageView(context);
            ItemDecorationView.setLayoutParams(new ViewGroup.LayoutParams(5, 5));
            ItemDecorationView.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }

        this.ItemDecorationView = ItemDecorationView;
        this.ItemDecorationView.measure(0, 0);
        this.ItemDecorationViewDrawble = ItemDecorationView.getBackground();
        if (this.ItemDecorationView.getMeasuredHeight() >= 0) {
            ItemDecorationViewHeight = this.ItemDecorationView.getMeasuredHeight();
        }
        if (this.ItemDecorationView.getMeasuredWidth() >= 0) {
            ItemDecorationViewWidth = this.ItemDecorationView.getMeasuredWidth();
        }
        if (ItemDecorationView.getLayoutParams().height >= 0) {
            ItemDecorationViewHeight = ItemDecorationView.getLayoutParams().height;
        }
        if (ItemDecorationView.getLayoutParams().width >= 0) {
            ItemDecorationViewWidth = ItemDecorationView.getLayoutParams().width;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = 1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + ItemDecorationViewWidth;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + ItemDecorationViewHeight;
            ItemDecorationViewDrawble.setBounds(left, top, right, bottom);
            ItemDecorationViewDrawble.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        int spanCount = getSpanCount(parent);
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            int right = left + ItemDecorationViewWidth;
            if ((i + 1) % spanCount == 0) {//最后一列
                right = left;
            }
            ItemDecorationViewDrawble.setBounds(left, top, right, bottom);
            ItemDecorationViewDrawble.draw(c);
        }
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                // 如果是最后一行，则不需要绘制底部
                if (pos + 1 >= childCount)
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                // 如果是最后一行，则不需要绘制底部
                if (pos + 1 >= childCount)
                    return true;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition,
                               RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        if (isLastRaw(parent, itemPosition, spanCount, childCount))// 如果是最后一行，则不需要绘制底部
        {
            outRect.set(0, 0, ItemDecorationViewWidth, 0);
        } else {
            outRect.set(0, 0, ItemDecorationViewWidth,
                    ItemDecorationViewHeight);
        }
    }
}
