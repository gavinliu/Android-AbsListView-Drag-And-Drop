package cn.gavinliu.android.lib.dragdrop;

import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;

/**
 * Created by GavinLiu on 2015-06-02
 */
class SelectionController {

    private AbsListView absListView;

    private int mLastMotionX, mLastMotionY;

    private boolean isHandleItem;

    private int targetPosition = -1, tempPosition = -1;

    private CheckLongClick mCheckLongClick;

    private ActionMode mActionMode;

    private boolean isSwipeChoise;

    private SelectionAttacher selectionAttacher;

    private static final int TOUCH_SLOP = 10;


    public void setSelectionAttacher(SelectionAttacher selectionAttacher) {
        this.selectionAttacher = selectionAttacher;
    }

    public void setActionMode(ActionMode actionMode) {
        this.mActionMode = actionMode;
    }

    public void setIsSwipeChoise(boolean isSwipeChoise) {
        this.isSwipeChoise = isSwipeChoise;
    }


    public SelectionController(AbsListView absListView) {
        this.absListView = absListView;
    }

    void startSelection(int position) {
        isHandleItem = true;
        tempPosition = position;
        targetPosition = position;
    }

    boolean onTouchEvent(MotionEvent ev) {

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mLastMotionX = x;
                mLastMotionY = y;

                if (mActionMode != null && !isSwipeChoise) {
                    if (mCheckLongClick == null) {
                        mCheckLongClick = new CheckLongClick();
                    }
                    int longPressTimeout = ViewConfiguration.getLongPressTimeout();
                    absListView.postDelayed(mCheckLongClick, longPressTimeout);
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if (mCheckLongClick != null && (Math.abs(mLastMotionX - x) > TOUCH_SLOP || Math.abs(mLastMotionY - y) > TOUCH_SLOP)) {
                    absListView.removeCallbacks(mCheckLongClick);
                }
                if (isHandleItem && isSwipeChoise) {

                    int position = absListView.pointToPosition(x, y);
                    if (position >= 0 && position < absListView.getAdapter().getCount() && position != tempPosition) {

                        int i, j;

                        if (targetPosition > position) {
                            i = position;
                            j = targetPosition;
                        } else {
                            i = targetPosition;
                            j = position;
                        }

                        if (tempPosition > j) {
                            for (int temp = tempPosition; temp > j; temp--) {
                                absListView.setItemChecked(temp, false);
                            }
                        }

                        if (tempPosition < i) {
                            for (int temp = tempPosition; temp < i; temp++) {
                                absListView.setItemChecked(temp, false);
                            }
                        }

                        for (; i <= j; i++) {
                            absListView.setItemChecked(i, true);
                            if (selectionAttacher != null) {
                                selectionAttacher.updateChooseCount(absListView.getCheckedItemCount());
                            }
                        }

                        tempPosition = position;
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mCheckLongClick != null) {
                    absListView.removeCallbacks(mCheckLongClick);
                }
                isHandleItem = false;
                tempPosition = -1;
                targetPosition = -1;
                break;
        }

        return false;
    }

    private class CheckLongClick implements Runnable {

        @Override
        public void run() {

            int position = absListView.pointToPosition(mLastMotionX, mLastMotionY);
            long id = absListView.getAdapter().getItemId(position);
            int itemNum = position - absListView.getFirstVisiblePosition();
            View selectedView = absListView.getChildAt(itemNum);

            absListView.setItemChecked(position, true);

            if (absListView instanceof DDListView) {
                ((DDListView) absListView).onItemLongClick(absListView, selectedView, position, id);
            } else if (absListView instanceof DDGridView) {
                ((DDGridView) absListView).onItemLongClick(absListView, selectedView, position, id);
            }

        }
    }



}