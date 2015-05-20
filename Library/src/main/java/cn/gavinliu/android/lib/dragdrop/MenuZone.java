package cn.gavinliu.android.lib.dragdrop;

import android.view.View;

/**
 * Created by gavin on 15-5-15.
 */
public class MenuZone implements DragDropController.DragDropListener {

    public View menuView;
    public MenuType menuType;
    public int x, y, w, h;

    public MenuZone(View menuView, MenuType menuType) {
        this.menuView = menuView;
        this.menuType = menuType;

        int[] position = new int[2];
        menuView.getLocationOnScreen(position);
        this.x = position[0];
        this.y = position[1];
        this.w = menuView.getWidth();
        this.h = menuView.getHeight();
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
    public void onDragStart() {

    }

    @Override
    public void onDragEnter() {
        menuView.setPressed(true);
    }

    @Override
    public void onDragExit() {
        menuView.setPressed(false);
    }

    @Override
    public void onDragEnd() {
        menuView.setPressed(false);
    }

    @Override
    public void onDrop(int id) {

    }
}
