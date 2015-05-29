package cn.gavinliu.android.lib.dragdrop.transformer;

import android.content.res.Resources;

import cn.gavinliu.android.lib.dragdrop.DragDropAttacher;
import cn.gavinliu.android.lib.dragdrop.MultiChoosable;

/**
 * Created by GavinLiu on 2015-05-27
 */
public abstract class Transformer {

    protected MultiChoosable mMultiChoosable;
    protected DragDropAttacher mDragDropAttacher;

    public void onViewCreated() {
    }

    public void onReset() {
    }

    public void setMultiChoosable(MultiChoosable multiChoosable) {
        this.mMultiChoosable = multiChoosable;
    }

    public void setDragDropAttacher(DragDropAttacher dragDropAttacher) {
        this.mDragDropAttacher = dragDropAttacher;
    }

    public abstract boolean showContentView();

    public abstract boolean hideContentView();

    protected int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
