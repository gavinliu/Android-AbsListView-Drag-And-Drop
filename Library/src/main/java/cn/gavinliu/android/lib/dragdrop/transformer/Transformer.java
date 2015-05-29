package cn.gavinliu.android.lib.dragdrop.transformer;

import android.app.Activity;
import android.view.View;

import cn.gavinliu.android.lib.dragdrop.MultiChoosable;

/**
 * Created by GavinLiu on 2015-05-27
 */
public abstract class Transformer {

    protected MultiChoosable mMultiChoosable;

    public void onViewCreated(Activity activity, View headerView) {
    }

    public void onReset() {
    }

    public void setMultiChoosable(MultiChoosable multiChoosable) {
        this.mMultiChoosable = multiChoosable;
    }

    public abstract boolean showContentView();

    public abstract boolean hideContentView();

}
