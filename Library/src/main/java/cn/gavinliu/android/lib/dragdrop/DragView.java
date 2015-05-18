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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by gavin on 15-5-15.
 */
public class DragView extends ImageView implements DragDropController.DragDropListener {

    private int mTouchX, mTouchY;
    private int mCreateX, mCreateY;

    private int layoutX, layoutY;
    private int layoutWidth, layoutHeight;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;

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

    @Override
    public void setScaleX(float scaleX) {
        super.setScaleX(scaleX);
    }

    private void init() {
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        int[] position = new int[2];
        mTargetView.getLocationOnScreen(position);
        int x = position[0];
        int y = position[1];

        mCreateX = x;
        mCreateY = y;
        layoutWidth = mTargetView.getWidth();
        layoutHeight = mTargetView.getHeight();
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
        ObjectAnimator animatorX = ObjectAnimator.ofInt(this, "layoutX", layoutX, mTouchX - layoutWidth / 2);
        ObjectAnimator animatorY = ObjectAnimator.ofInt(this, "layoutY", layoutY, mTouchY - layoutHeight / 2);
        ObjectAnimator animatorWidth = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0f);
        ObjectAnimator animatorHeight = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0f);

        animatorX.addUpdateListener(updateListener);
        animatorY.addUpdateListener(updateListener);
//        animatorWidth.addUpdateListener(updateListener);
//        animatorHeight.addUpdateListener(updateListener);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(450);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(animatorX).with(animatorY).with(animatorWidth).with(animatorHeight);
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

        mLayoutParams.width = layoutWidth;
        mLayoutParams.height = layoutHeight;

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
        mLayoutParams.width = layoutWidth;
        mLayoutParams.height = layoutHeight;

        Log.d("move", "width:" + layoutWidth);
        Log.d("move", "height:" + layoutHeight);

        mWindowManager.updateViewLayout(this, mLayoutParams);
    }

    private void remove() {
        this.setVisibility(View.GONE);
        mWindowManager.removeView(this);
        recycleBitmap();

        mTargetView.setVisibility(VISIBLE);
    }

    public void setTouchPosition(int touchX, int touchY) {
        mTouchX = touchX;
        mTouchY = touchY;
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

    public void setLayoutX(int layoutX) {
        this.layoutX = layoutX;
    }

    public void setLayoutY(int layoutY) {
        this.layoutY = layoutY;
    }

}
