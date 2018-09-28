package com.uibenglish.aslan.mvpmindorkssample.utils;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/**
 * Created by Paranoid on 17/10/17.
 */

public class AnimatorUtility {

    private static Interpolator fastOutSlowIn;
//    private static Interpolator fastOutLinearIn;
//    private static Interpolator linearOutSlowIn;
//    private static Interpolator linear;

    public static Interpolator getFastOutSlowInInterpolator(Context context) {
        if (fastOutSlowIn == null) {
            fastOutSlowIn = AnimationUtils.loadInterpolator(context,
                    android.R.interpolator.fast_out_slow_in);
        }
        return fastOutSlowIn;
    }

//    public static Interpolator getFastOutLinearInInterpolator(Context context) {
//        if (fastOutLinearIn == null) {
//            fastOutLinearIn = AnimationUtils.loadInterpolator(context,
//                    android.R.interpolator.fast_out_linear_in);
//        }
//        return fastOutLinearIn;
//    }
//
//    public static Interpolator getLinearOutSlowInInterpolator(Context context) {
//        if (linearOutSlowIn == null) {
//            linearOutSlowIn = AnimationUtils.loadInterpolator(context,
//                    android.R.interpolator.linear_out_slow_in);
//        }
//        return linearOutSlowIn;
//    }
//
//    public static Interpolator getLinearInterpolator() {
//        if (linear == null) {
//            linear = new LinearInterpolator();
//        }
//        return linear;
//    }
//
//    public static void animateOpen(ViewGroup view) {
//        view.setVisibility(View.VISIBLE);
//        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        int toHeight = view.getMeasuredHeight();
//        ValueAnimator animator = createDropAnim(view, 0, toHeight);
//        animator.start();
//    }
//
//    public static void animateClose(final ViewGroup view) {
//        int orgHeight = view.getHeight();
//        ValueAnimator animator = createDropAnim(view, orgHeight, 0);
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                view.setVisibility(View.GONE);
//            }
//        });
//        animator.start();
//    }
//
//    private static ValueAnimator createDropAnim(final ViewGroup view, int from, int to) {
//        ValueAnimator animator = ValueAnimator.ofInt(from, to);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = (int) animation.getAnimatedValue();
//                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                layoutParams.height = value;
//                view.setLayoutParams(layoutParams);
//            }
//        });
//        return animator;
//    }
}
