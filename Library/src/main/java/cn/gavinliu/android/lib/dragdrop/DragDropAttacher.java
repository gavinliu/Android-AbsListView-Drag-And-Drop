package cn.gavinliu.android.lib.dragdrop;

import android.app.Activity;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import cn.gavinliu.android.lib.dragdrop.header.DefaultHeaderTransformer;
import cn.gavinliu.android.lib.dragdrop.header.HeaderTransformer;

/**
 * Created by GavinLiu on 2015-05-27
 */
public class DragDropAttacher {

    private HeaderTransformer mHeaderTransformer;

    private View mHeaderView;

    public DragDropAttacher(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("activity cannot be null");
        }

        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        mHeaderView = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.header, null);

        int statusHeight = getInternalDimensionSize(activity.getResources(), "status_bar_height");
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 56 * 3 + statusHeight);
        mHeaderView.setLayoutParams(params);
        mHeaderView.setPadding(0, statusHeight, 0, 0);
        decorView.addView(mHeaderView);
        mHeaderView.setVisibility(View.INVISIBLE);

        mHeaderTransformer = new DefaultHeaderTransformer();
        mHeaderTransformer.onViewCreated(activity, mHeaderView);
    }

    public void startDrag() {
        mHeaderTransformer.showHeaderView();
    }

    private int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
