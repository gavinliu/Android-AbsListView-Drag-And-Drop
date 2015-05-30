package cn.gavinliu.draganddroplistview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * Created by GavinLiu on 2015-05-29
 */
public class MyLayout extends FrameLayout {

    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    View checkable;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View v = getChildAt(i);
            if (v instanceof Checkable) {
                checkable = v;
            }
        }
    }

    boolean isAnimation;
    boolean activated;

    @Override
    public void setActivated(boolean activated) {
        if (activated && !this.activated && isAnimation) {
            ObjectAnimator animatorWidth = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0.8f);
            ObjectAnimator animatorHeight = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0.8f);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(250);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.playTogether(animatorHeight, animatorWidth);
            animatorSet.start();

        } else if (!activated && isAnimation) {
            if (getScaleX() != 1f || getScaleY() != 1f) {
                ObjectAnimator animatorWidth = ObjectAnimator.ofFloat(this, "scaleX", getScaleX(), 1f);
                ObjectAnimator animatorHeight = ObjectAnimator.ofFloat(this, "scaleY", getScaleY(), 1f);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(250);
                animatorSet.setInterpolator(new DecelerateInterpolator());
                animatorSet.playTogether(animatorHeight, animatorWidth);
                animatorSet.start();
            }
        }

        super.setActivated(activated);
        this.activated = activated;
    }

}
