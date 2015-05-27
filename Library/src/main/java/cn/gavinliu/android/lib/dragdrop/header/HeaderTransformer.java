package cn.gavinliu.android.lib.dragdrop.header;

import android.app.Activity;
import android.view.View;

/**
 * Created by GavinLiu on 2015-05-27
 */
public abstract class HeaderTransformer {

    public void onViewCreated(Activity activity, View headerView) {
    }

    public void onReset() {

    }

    public void onSelectionChange(int selectCount) {

    }

    public abstract boolean showHeaderView();

    public abstract boolean hideHeaderView();

}
