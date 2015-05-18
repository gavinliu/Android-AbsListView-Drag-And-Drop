package cn.gavinliu.android.lib.dragdrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gavin on 15-5-15.
 */
public class DragDropController {

    private static final String TAG = "DragDropController";

    private Context mContext;

    private boolean mIsDragging;

    private DragView mDragView;

    private MenuZone mMenuZone;

    private List<MenuZone> mMenus;

    private float mMotionDownX;
    private float mMotionDownY;

    public DragDropController(Context ctx) {
        mContext = ctx;
    }

    void addMenuZone(MenuZone menuZone) {
        if (mMenus == null) {
            mMenus = new ArrayList<MenuZone>();
        }
        mMenus.add(menuZone);
    }

    interface DragDropListener {

        void onDragStart();

        void onDragEnter();

        void onDragExit();

        void onDragEnd();

        void onDrop();

    }

    void startDrag(View v) {

        Bitmap bitmap = getViewBitmap(v);

        if (bitmap == null) {
            return;
        }

        mDragView = new DragView(mContext, v);
        mDragView.setImageBitmap(bitmap);
        mDragView.onDragStart();

        mIsDragging = true;
    }

    boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMotionDownX = ev.getRawX();
                mMotionDownY = ev.getRawY();
                break;
        }

        return mIsDragging;
    }

    boolean onTouchEvent(MotionEvent ev) {
        if (!mIsDragging) {
            return false;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMotionDownX = ev.getRawX();
                mMotionDownY = ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:

                int moveX = (int) (mMotionDownX - ev.getRawX());
                int moveY = (int) (mMotionDownY - ev.getRawY());

                if (mDragView != null) {
                    mDragView.move(moveX, moveY);
                    mDragView.setTouchPosition((int) ev.getRawX(), (int) ev.getRawY());
                }

                if (mMenus != null) {
                    for (MenuZone zone : mMenus) {
                        if (zone.isContains(ev.getRawX(), ev.getRawY())) {
                            zone.onDragEnter();
                            mDragView.onDragEnter();

                            mMenuZone = zone;
                        } else {
                            zone.onDragExit();
                            mDragView.onDragExit();

                            mMenuZone = null;
                        }
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mDragView != null) {
                    if (mMenuZone != null) {
                        mDragView.onDrop();
                        mMenuZone.onDrop();
                    } else {
                        mDragView.onDragEnd();
                    }
                }

                if (mMenuZone != null) {
                    mMenuZone.onDragEnd();
                    mMenuZone = null;
                }

                mIsDragging = false;

                break;
        }

        return true;
    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e(TAG, "failed getViewBitmap(" + v + ")", new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

}
