package cn.gavinliu.android.lib.dragdrop;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by gavin on 15-5-15.
 */
public class DragView extends ImageView implements DragDropController.DragDropListener {

    private int mCreateX;
    private int mCreateY;
    private int mWidth;
    private int mHeight;

    private int layoutX;
    private int layoutY;

    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    private View mTargetView;

    @Deprecated
    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, View targetView) {
        super(context);
        mTargetView = targetView;
        init();
    }

    private void init() {
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        int[] position = new int[2];
        mTargetView.getLocationOnScreen(position);
        int x = position[0];
        int y = position[1];

        mCreateX = x;
        mCreateY = y;
        mWidth = mTargetView.getWidth();
        mHeight = mTargetView.getHeight();
    }

    @Override
    public void onDragStart() {
        show();
        setBackgroundColor(0xDCFFFFFF);
    }

    @Override
    public void onDragEnter() {
        setBackgroundColor(0xDCF8BBD0);
    }

    @Override
    public void onDragExit() {
        setBackgroundColor(0xDCFFFFFF);
    }

    @Override
    public void onDragEnd() {
        ObjectAnimator animatorX = ObjectAnimator.ofInt(this, "layoutX", layoutX, mCreateX);
        ObjectAnimator animatorY = ObjectAnimator.ofInt(this, "layoutY", layoutY, mCreateY);
        animatorX.addUpdateListener(updateListener);
        animatorY.addUpdateListener(updateListener);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(550);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                remove();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            move();
        }
    };

    @Override
    public void onDrop() {
        remove();
    }

    private void show() {
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL;
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.flags = mLayoutParams.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        mLayoutParams.flags = mLayoutParams.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;

        mLayoutParams.x = mCreateX;
        mLayoutParams.y = mCreateY;

        mLayoutParams.width = mWidth;
        mLayoutParams.height = mHeight;

        mWindowManager.addView(this, mLayoutParams);
        mTargetView.setVisibility(INVISIBLE);
    }

    public void move(int x, int y) {
        layoutX = mCreateX - x;
        layoutY = mCreateY - y;

        move();
    }

    private void move() {
        mLayoutParams.x = layoutX;
        mLayoutParams.y = layoutY;

        mWindowManager.updateViewLayout(this, mLayoutParams);
    }

    private void remove() {
        mWindowManager.removeView(this);
        recycleBitmap();

        mTargetView.setVisibility(VISIBLE);
    }

    public void setLayoutX(int layoutX) {
        this.layoutX = layoutX;
    }

    public void setLayoutY(int layoutY) {
        this.layoutY = layoutY;
    }

    private void recycleBitmap() {
        Drawable drawable = getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

}
