package cn.gavinliu.android.lib.dragdrop.widget;

/**
 * Created by GavinLiu on 2015-05-27
 */
public interface DragOrDroppable {

    void dragStart();

    void dragEnter();

    void dragExit();

    void dragEnd();

    void drop(int menuId, int itemPosition, long itemId);
}
