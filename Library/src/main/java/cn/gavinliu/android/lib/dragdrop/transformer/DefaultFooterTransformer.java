package cn.gavinliu.android.lib.dragdrop.transformer;

import android.app.Activity;
import android.view.View;

/**
 * Created by GavinLiu on 2015-05-27
 */
public class DefaultFooterTransformer extends FooterTransformer {

    @Override
    public void onViewCreated(Activity activity, View headerView) {
        super.onViewCreated(activity, headerView);
    }

    @Override
    public boolean showContentView() {
        return false;
    }

    @Override
    public boolean hideContentView() {
        return false;
    }
}
