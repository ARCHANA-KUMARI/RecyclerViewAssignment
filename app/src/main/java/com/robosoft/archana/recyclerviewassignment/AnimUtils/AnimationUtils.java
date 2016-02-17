package com.robosoft.archana.recyclerviewassignment.AnimUtils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;

import com.robosoft.archana.recyclerviewassignment.adapter.ProductListAdapter;

/**
 * Created by archana on 13/2/16.
 */
public class AnimationUtils {
    public static void animatie(RecyclerView.ViewHolder holder,boolean goesDown){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(holder.itemView,"translationY",goesDown==true?300:-300,0);
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -50, 50, -30, 30, -5, 5, 0);
        animatorSet.setDuration(1000);
        animatorSet.playTogether(objectAnimatorX, objectAnimatorY);
        animatorSet.setInterpolator(new AnticipateInterpolator());
        animatorSet.start();
    }
}
