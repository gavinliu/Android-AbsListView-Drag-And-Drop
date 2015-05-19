package cn.gavinliu.android.lib.dragdrop;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by gavin on 15-5-13.
 */
public class DDListView extends ListView implements AdapterView.OnItemLongClickListener, DragDropController.DragDropListener {

    public DragDropController mDDController;
    protected OnDragDropListener onDragDropListener;

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
        mDDController.setDragDropListener(this);
        setOnItemLongClickListener(this);
    }

    public interface OnDragDropListener {

        void onDragStart();

        void onDragEnter();

        void onDragExit();

        void onDragEnd();

        /**
         * @param id The id is ListView item id
         */
        void onSelect(long id);

        /**
         * @param id Menu View id
         */
        void onDrop(int id);
    }

    public void addMenu(View v, MenuType menuType) {
        int[] position = new int[2];
        v.getLocationOnScreen(position);
        int x = position[0];
        int y = position[1];
        MenuZone menuZone = new MenuZone(v, menuType, x, y, v.getWidth(), v.getHeight());
        mDDController.addMenuZone(menuZone);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        mDDController.startDrag(view);

        if (onDragDropListener != null) {
            onDragDropListener.onSelect(id);
        }

        return true;
    }

    @Override
    public void setMultiChoiceModeListener(MultiChoiceModeListener listener) {

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
        Log.d("DDListView", ev.getRawX() + "," + ev.getRawY());

        mDDController.onTouchEvent(ev);

        return super.onTouchEvent(ev);
    }

    // ============
    // >> DragDropController.DragDropListener
    // ============

    @Override
    public void onDragStart() {

    }

    @Override
    public void onDragEnter() {

    }

    @Override
    public void onDragExit() {

    }

    @Override
    public void onDragEnd() {

    }

    @Override
    public void onDrop(int id) {
        if (onDragDropListener != null) {
            onDragDropListener.onDrop(id);
        }
    }

    // ============
    // << DragDropController.DragDropListener
    // ============

    public void setOnDragDropListener(OnDragDropListener onDragDropListener) {
        this.onDragDropListener = onDragDropListener;
    }
}
