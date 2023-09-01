package com.example.youtubedemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.hao.usbproject.R;

public class MyMotionLayout extends MotionLayout {
    public MyMotionLayout(@NonNull Context context) {
        super(context);
    }

    public MyMotionLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMotionLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean hasTouchStarted = false;
    private final Rect viewRect = new Rect();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                hasTouchStarted = false;
                return super.onTouchEvent(event);
        }
        if (!hasTouchStarted) {
            findViewById(R.id.background_view).getHitRect(viewRect);
            hasTouchStarted = viewRect.contains((int) event.getX(), (int) event.getY());
        }
        return hasTouchStarted && super.onTouchEvent(event);
    }
}
