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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.gavinliu.android.lib.dragdrop.R;
import cn.gavinliu.android.lib.dragdrop.Tools;

/**
 * Created by GavinLiu on 2015-05-27
 */
public class DefaultHeaderTransformer extends HeaderTransformer implements View.OnClickListener {

    private View mHeaderView;
    private TextView mInfo;

    private boolean isAll = false;

    public DefaultHeaderTransformer(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        mHeaderView = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.header, null);

        int statusHeight = getInternalDimensionSize(activity.getResources(), "status_bar_height");
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 56 * 3 + statusHeight);
        params.gravity = Gravity.TOP;
        mHeaderView.setLayoutParams(params);
        mHeaderView.setPadding(0, statusHeight, 0, 0);
        decorView.addView(mHeaderView);
        mHeaderView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        mInfo = (TextView) mHeaderView.findViewById(R.id.txt_info);
        Button mAll = (Button) mHeaderView.findViewById(R.id.btn_all);
        Button mCancel = (Button) mHeaderView.findViewById(R.id.btn_cancel);

        mAll.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    @Override
    public boolean showContentView() {
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
    public boolean hideContentView() {
        final boolean changeVis = mHeaderView.getVisibility() != View.GONE;

        if (changeVis) {
            Animator animator;
            if (mHeaderView.getAlpha() >= 0.5f) {
                animator = new AnimatorSet();
                ObjectAnimator transAnim = ObjectAnimator.ofFloat(mHeaderView, "translationY", 0f, -mHeaderView.getHeight());
                ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mHeaderView, "alpha", 1f, 0f);
                ((AnimatorSet) animator).playTogether(transAnim, alphaAnim);
            } else {
                animator = ObjectAnimator.ofFloat(mHeaderView, "alpha", 1f, 0f);
            }
            animator.setDuration(250);
            animator.addListener(new HideAnimationCallback());
            animator.start();
        }

        return false;
    }

    @Override
    public boolean updateChooseCount(int count) {

        if (count == 0) {
            mInfo.setText("请选择");
        } else {
            mInfo.setText("已选:" + count + "个");
        }

        return false;
    }

    @Override
    public void onReset() {
        super.onReset();
        isAll = false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_all) {
            Button button = (Button) v;
            if (!isAll) {
                mMultiChoosable.multiChooseAll();
                button.setText("清空");
            } else {
                mMultiChoosable.clearMultiChoose();
                button.setText("全选");
            }
            isAll = !isAll;
        } else if (v.getId() == R.id.btn_cancel) {
            mMultiChoosable.exitMultiChoiceMode();
            hideContentView();
            mDragDropAttacher.getFooterTransformer().hideContentView();
        }
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
