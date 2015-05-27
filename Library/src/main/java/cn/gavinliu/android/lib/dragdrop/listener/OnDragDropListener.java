package cn.gavinliu.android.lib.dragdrop.listener;

/**
 * Created by GavinLiu on 2015-05-27
 */
public interface OnDragDropListener {

    void onDragStart();

    void onDragEnter();

    void onDragExit();

    void onDragEnd();

    void onDrop(int menuId, int itemPosition, long itemId);

}
