package com.zbc.gymappointment.widget;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class AutoLayoutAnimation {

    static private boolean sDisableAutoLayoutAnimation = false;
    private final static Interpolator DEFAULT_INTERPOLATOR = new DecelerateInterpolator(2f);

    public interface HostView {
        int getLeft();
        int getTop();
        int getRight();
        int getBottom();
        int getVisibility();
        float getTranslationX();
        float getTranslationY();
        void setTranslationX(float translationX);
        void setTranslationY(float translationY);
        ViewPropertyAnimator animate();
        void setEnableAutoLayoutAnimation(boolean isEnable);
        boolean isEnableAutoLayoutAnimation();
        void setSkipNextAutoLayoutAnimation(boolean isSkip);
        boolean getSkipNextAutoLayoutAnimation();
        boolean superSetFrame(int left, int top, int right, int bottom);
        void setGhostView(GhostView gv);
        GhostView getGhostView();
    }

    public interface GhostView {
        void updateAnimateTarget(View target);
        void updateAnimateTarget(float[] targetLoc);
    }

    public static void setDisableAutoLayoutAnimation(boolean disable) {
        sDisableAutoLayoutAnimation = disable;
    }

    public static boolean setFrame(HostView hv, int left, int top, int right, int bottom, AutoLayoutAnimatorAnimateDelegate delegate) {
        int l = hv.getLeft();
        int t = hv.getTop();
        int r = hv.getRight();
        int b = hv.getBottom();
        boolean changed = hv.superSetFrame(left, top, right, bottom);
        if (hv instanceof ViewGroup){
            Log.e("------","class = "+hv.getClass().getSimpleName());
            ((ViewGroup)hv).setClipChildren(false);
            ((ViewGroup)hv).setClipToPadding(false);
        }
//        if (hv.getSkipNextAutoLayoutAnimation()) {
//            hv.setSkipNextAutoLayoutAnimation(false);
//            return changed;
//        }
//        if (!sDisableAutoLayoutAnimation &&
//                hv.isEnableAutoLayoutAnimation() && (l != hv.getLeft() || t != hv.getTop()) &&
//                b - t == hv.getBottom() - hv.getTop() && r - l == hv.getRight() - hv.getLeft()) {
//            if (hv.getVisibility() == View.VISIBLE) {
//                if (delegate == null) {
//                    hv.setTranslationX(l - hv.getLeft() + hv.getTranslationX());
//                    hv.setTranslationY(t - hv.getTop() + hv.getTranslationY());
//                    hv.animate().setDuration(ItemIcon.MOVEMENT_ANIMATION_DURATION).setStartDelay(0).
//                            setInterpolator(DEFAULT_INTERPOLATOR).translationX(0).translationY(0).start();
//                } else {
//                    delegate.animate(hv, l ,t);
//                }
//            }
//            if (hv.getGhostView() != null) {
//                hv.getGhostView().updateAnimateTarget((View) hv);
//            }
//        }
        return changed;
    }

    public static boolean setFrame(HostView hv, int left, int top, int right, int bottom) {
        return setFrame(hv, left, top, right, bottom, null);
    }

    public interface AutoLayoutAnimatorAnimateDelegate {
        void animate(HostView hv, int oldLeft, int oldTop);
    }
}
