package cn.gavinliu.android.lib.dragdrop;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import cn.gavinliu.android.lib.dragdrop.widget.DragOrDroppable;
import cn.gavinliu.android.lib.dragdrop.widget.MenuZone;

/**
 * Created by GavinLiu on 2015-5-13.
 */
public class DDListView extends ListView implements DragOrDroppable,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, AbsListView.OnScrollListener {

    private DragDropController mDDController;
    protected OnDragDropListener onDragDropListener;
    private ActionMode mActionMode;
    private SelectionMode mSelectionMode;
    private CheckLongClick mCheckLongClick;
    private static final int TOUCH_SLOP = 20;
    private int mLastMotionX, mLastMotionY;

    private boolean isMultChoise;

    public DDListView(Context context) {
        super(context);
        init();
    }

    public DDListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DDListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDDController = new DragDropController(getContext());
        mDDController.setDragOrDroppable(this);
        setOnItemLongClickListener(this);
        setOnScrollListener(this);
    }

    public interface OnDragDropListener {

        void onDragStart();

        void onDragEnter();

        void onDragExit();

        void onDragEnd();

        void onDrop(int menuId, int itemPosition, long itemId);
    }

    public void addMenuZone(View v, MenuZone.Type menuType) {
        MenuZone menuZone = new MenuZone(v, menuType);
        mDDController.addMenuZone(menuZone);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        mDDController.startDrag(view, position, id);

        switch (mSelectionMode) {
            case Custom:
                isMultChoise = true;
                setChoiceMode(DDListView.CHOICE_MODE_MULTIPLE);
                setItemChecked(position, true);
                break;
        }

        return true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void setMultiChoiceModeListener(MultiChoiceModeListener listener) {
        super.setMultiChoiceModeListener(new MultiChoiceModeWrapper(listener));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        mDDController.onInterceptTouchEvent(ev);

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        mDDController.onTouchEvent(ev);

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mLastMotionX = x;
                mLastMotionY = y;

                if (mActionMode != null) {
                    if (mCheckLongClick == null) {
                        mCheckLongClick = new CheckLongClick();
                    }
                    int longPressTimeout = ViewConfiguration.getLongPressTimeout();
                    postDelayed(mCheckLongClick, longPressTimeout);
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if (mCheckLongClick != null && (Math.abs(mLastMotionX - x) > TOUCH_SLOP || Math.abs(mLastMotionY - y) > TOUCH_SLOP)) {
                    removeCallbacks(mCheckLongClick);
                }

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mCheckLongClick != null) {
                    removeCallbacks(mCheckLongClick);
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public void dragStart() {

    }

    @Override
    public void dragEnter() {

    }

    @Override
    public void dragExit() {

    }

    @Override
    public void dragEnd() {

    }

    @Override
    public void drop(int menuId, int itemPosition, long itemId) {
        exitMultiChoiceMode();

        if (onDragDropListener != null) {
            onDragDropListener.onDrop(menuId, itemPosition, itemId);
        }
    }

    public void exitMultiChoiceMode() {
        clearChoices();
        isMultChoise = false;
        switch (mSelectionMode) {
            case Custom:
                // TODO bad code,
                ((BaseAdapter)getAdapter()).notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setChoiceMode(DDListView.CHOICE_MODE_NONE);
                    }
                }, 100);
                break;

            case Official:
                if (mActionMode != null) {
                    mActionMode.finish();
                }
                break;
        }
    }

    private class MultiChoiceModeWrapper implements MultiChoiceModeListener {
        MultiChoiceModeListener mWrapped;
        boolean isDraggable;

        public MultiChoiceModeWrapper(MultiChoiceModeListener listener) {
            this.mWrapped = listener;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            if (mWrapped.onCreateActionMode(mode, menu)) {
                isDraggable = true;
                return true;
            }
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return mWrapped.onActionItemClicked(mode, item);
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mActionMode = mode;
            setLongClickable(true);
            return mWrapped.onPrepareActionMode(mode, menu);
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isDraggable = true;
            mActionMode = null;
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            if (isDraggable) {
                int item = position - getFirstVisiblePosition();
                View v = getChildAt(item);
                mDDController.startDrag(v, position, id);
                isDraggable = false;
            }
        }
    }

    public void setOnDragDropListener(OnDragDropListener onDragDropListener) {
        this.onDragDropListener = onDragDropListener;
    }

    public void setSelectionMode(SelectionMode selectionMode) {
        this.mSelectionMode = selectionMode;

        switch (selectionMode) {
            case Official:
                setChoiceMode(CHOICE_MODE_MULTIPLE_MODAL);
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isMultChoise) {
            exitMultiChoiceMode();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private class CheckLongClick implements Runnable {

        @Override
        public void run() {
            int position = pointToPosition(mLastMotionX, mLastMotionY);
            long id = getAdapter().getItemId(position);
            int itemNum = position - getFirstVisiblePosition();
            View selectedView = getChildAt(itemNum);

            setItemChecked(position, true);
            onItemLongClick(DDListView.this, selectedView, position, id);
        }
    }
}
