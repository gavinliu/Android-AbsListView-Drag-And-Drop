package cn.gavinliu.android.lib.dragdrop;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by gavin on 15-5-15.
 */
public class DragView extends ImageView implements DragDropController.DragDropListener {

    private int createX;
    private int createY;
    private int width;
    private int height;

    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    public DragView(Context context, int x, int y, int width, int height) {
        super(context);
        init();

        createX = x;
        createY = y;
        this.width = width;
        this.height = height;
    }

    private void init() {
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    }

    private void show() {
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL;
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.flags = mLayoutParams.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        mLayoutParams.flags = mLayoutParams.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;

        mLayoutParams.x = createX;
        mLayoutParams.y = createY;

        mLayoutParams.width = width;
        mLayoutParams.height = height;

        mWindowManager.addView(this, mLayoutParams);
    }

    public void move(int x, int y) {
        mLayoutParams.x = createX - x;
        mLayoutParams.y = createY - y;

        mWindowManager.updateViewLayout(this, mLayoutParams);
    }

    private void remove() {
        mWindowManager.removeView(this);
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
        remove();
    }

    @Override
    public void onDrop() {

    }
}
