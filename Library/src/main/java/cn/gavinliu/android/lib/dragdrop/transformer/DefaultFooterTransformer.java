package cn.gavinliu.android.lib.dragdrop.transformer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import cn.gavinliu.android.lib.dragdrop.R;
import cn.gavinliu.android.lib.dragdrop.Tools;

/**
 * Created by GavinLiu on 2015-05-27
 */
public class DefaultFooterTransformer extends FooterTransformer {

    private View mHeaderView;

    public DefaultFooterTransformer(Activity activity) {

        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        mHeaderView = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.footer, null);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 56 * 3);
        params.gravity = Gravity.BOTTOM;
        mHeaderView.setLayoutParams(params);
        decorView.addView(mHeaderView);
        mHeaderView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    @Override
    public boolean showContentView() {
        final boolean changeVis = mHeaderView.getVisibility() != View.VISIBLE;

        if (changeVis) {
            mHeaderView.setVisibility(View.VISIBLE);
            AnimatorSet animSet = new AnimatorSet();
            ObjectAnimator transAnim = ObjectAnimator.ofFloat(mHeaderView, "translationY", mHeaderView.getHeight(), 0f);
            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mHeaderView, "alpha", 0f, 1f);
            animSet.playTogether(transAnim, alphaAnim);
            animSet.setDuration(250);
            animSet.start();
        }

        return changeVis;
    }

    @Override
    public boolean hideContentView() {
        final boolean changeVis = mHeaderView.getVisibility() != View.GONE;

        if (changeVis) {
            Animator animator;
            if (mHeaderView.getAlpha() >= 0.5f) {
                animator = new AnimatorSet();
                ObjectAnimator transAnim = ObjectAnimator.ofFloat(mHeaderView, "translationY", 0f, mHeaderView.getHeight());
                ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mHeaderView, "alpha", 1f, 0f);
                ((AnimatorSet) animator).playTogether(transAnim, alphaAnim);
            } else {
                animator = ObjectAnimator.ofFloat(mHeaderView, "alpha", 1f, 0f);
            }
            animator.setDuration(250);
            animator.addListener(new HideAnimationCallback());
            animator.start();
        }

        return changeVis;
    }


    class HideAnimationCallback extends AnimatorListenerAdapter {

        @Override
        public void onAnimationEnd(Animator animation) {
            if (mHeaderView != null) {
                mHeaderView.setVisibility(View.GONE);
            }
            onReset();
        }
    }
}
