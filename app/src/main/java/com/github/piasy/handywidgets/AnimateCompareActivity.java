package com.github.piasy.handywidgets;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/1.
 */
public class AnimateCompareActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_compare);
        ObjectAnimator loadAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this,
                R.animator.loading_animator);
        ImageView loadingView = (ImageView) findViewById(R.id.mIvLoading);
        loadAnimator.setTarget(loadingView);
        loadAnimator.start();
    }
}
