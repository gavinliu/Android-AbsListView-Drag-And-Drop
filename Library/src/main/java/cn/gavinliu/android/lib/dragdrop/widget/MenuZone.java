package cn.gavinliu.android.lib.dragdrop.widget;

import android.view.View;

/**
 * Created by GavinLiu on 2015-5-15.
 */
public class MenuZone implements DragOrDroppable {

    public View menuView;
    public Type menuType;
    public int x, y, w, h;

    public MenuZone(View menuView, Type menuType) {
        this.menuView = menuView;
        this.menuType = menuType;

        int[] position = new int[2];
        menuView.getLocationOnScreen(position);
        this.x = position[0];
        this.y = position[1];
        this.w = menuView.getWidth();
        this.h = menuView.getHeight();
    }

    public enum Type {
        NORMAL, DELETE
    }

    public int getId() {
        return menuView.getId();
    }

    @Override
    public String toString() {
        return "x:" + x + "," + "y:" + y + "," + "w:" + w + ",h:" + h;
    }

    public boolean isContains(float touchX, float touchY) {
        return touchX > x && touchX < x + w && touchY > y && touchY < y + h;
    }

    @Override
    public void dragStart() {

    }

    @Override
    public void dragEnter() {
        menuView.setPressed(true);
    }

    @Override
    public void dragExit() {
        menuView.setPressed(false);
    }

    @Override
    public void dragEnd() {
        menuView.setPressed(false);
    }

    @Override
    public void drop(int menuId, int itemPosition, long itemId) {

    }
}
