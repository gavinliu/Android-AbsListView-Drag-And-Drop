package cn.gavinliu.draganddroplistview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
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
        this(context, attrs, 0);
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyLayout);
        isAnimation = a.getBoolean(R.styleable.MyLayout_isAnimation, false);
        a.recycle();
    }

    View checkable;
    View text;

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

        text = findViewById(R.id.text);
    }

    boolean isAnimation = true;
    boolean activated;

    @Override
    public void setActivated(boolean activated) {
        if (activated && !this.activated && isAnimation) {
            ObjectAnimator animatorWidth = ObjectAnimator.ofFloat(text, "scaleX", 1f, 0.85f);
            ObjectAnimator animatorHeight = ObjectAnimator.ofFloat(text, "scaleY", 1f, 0.85f);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(250);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.playTogether(animatorHeight, animatorWidth);
            animatorSet.start();

        } else if (!activated && isAnimation) {
            if (text.getScaleX() != 1f || text.getScaleY() != 1f) {
                ObjectAnimator animatorWidth = ObjectAnimator.ofFloat(text, "scaleX", text.getScaleX(), 1f);
                ObjectAnimator animatorHeight = ObjectAnimator.ofFloat(text, "scaleY", text.getScaleY(), 1f);

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
