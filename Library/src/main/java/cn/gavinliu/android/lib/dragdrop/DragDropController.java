package cn.gavinliu.android.lib.dragdrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.List;

import cn.gavinliu.android.lib.dragdrop.widget.DragOrDroppable;
import cn.gavinliu.android.lib.dragdrop.widget.DragView;
import cn.gavinliu.android.lib.dragdrop.widget.MenuZone;

/**
 * Created by GavinLiu on 2015-5-15
 */
class DragDropController {

    private static final String TAG = "DragDropController";

    private Context mContext;

    private boolean mIsDragging;

    private DragView mDragView;

    private MenuZone mMenuZone;

    private DragOrDroppable mDragOrDroppable;

    private AbsListView mAbsListView;

    private List<MenuZone> mMenus;

    private float mMotionDownX, mMotionDownY;

    public DragDropController(Context ctx) {
        mContext = ctx;
    }

    void addMenuZone(MenuZone menuZone) {
        if (mMenus == null) {
            mMenus = new ArrayList<MenuZone>();
        }
        mMenus.add(menuZone);
    }

    void startDrag(View v, int itemPosition, long itemId) {

        Bitmap bitmap = getViewBitmap(v);

        if (bitmap == null) {
            return;
        }

        mDragView = new DragView(mContext, v, itemPosition, itemId);
        mDragView.setImageBitmap(bitmap);
        mDragView.dragStart();

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
                            zone.dragEnter();
                            mDragView.dragEnter();

                            mMenuZone = zone;
                        } else {
                            zone.dragExit();
                            mDragView.dragExit();

                            mMenuZone = null;
                        }
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mDragView != null) {
                    if (mMenuZone != null) {
                        mMenuZone.drop(mMenuZone.getId(), mDragView.itemPosition, mDragView.itemId);
                        mDragView.drop(mMenuZone.getId(), mDragView.itemPosition, mDragView.itemId);
                        mDragOrDroppable.drop(mMenuZone.getId(), mDragView.itemPosition, mDragView.itemId);
                    } else {
                        mDragView.dragEnd();
                    }
                }

                if (mMenuZone != null) {
                    mMenuZone.dragEnd();
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

    public void setDragOrDroppable(DragOrDroppable dragOrDroppable) {
        this.mDragOrDroppable = dragOrDroppable;
        if (dragOrDroppable instanceof AbsListView) {
            mAbsListView = (AbsListView) dragOrDroppable;
        }
    }
}
