package cn.gavinliu.android.lib.dragdrop.transformer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;

/**
 * Created by GavinLiu on 2015-05-27
 */
public class DefaultHeaderTransformer extends HeaderTransformer {

    private View mHeaderView;

    @Override
    public void onViewCreated(Activity activity, View headerView) {
        super.onViewCreated(activity, headerView);
        mHeaderView = headerView;
    }

    @Override
    public boolean showHeaderView() {
        final boolean changeVis = mHeaderView.getVisibility() != View.VISIBLE;

        if (changeVis) {
            mHeaderView.setVisibility(View.VISIBLE);
            AnimatorSet animSet = new AnimatorSet();
            ObjectAnimator transAnim = ObjectAnimator.ofFloat(mHeaderView, "translationY", -mHeaderView.getHeight(), 0f);
            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mHeaderView, "alpha", 0f, 1f);
            animSet.playTogether(transAnim, alphaAnim);
            animSet.setDuration(250);
            animSet.start();
        }

        return changeVis;
    }

    @Override
    public boolean hideHeaderView() {
        return false;
    }


}
