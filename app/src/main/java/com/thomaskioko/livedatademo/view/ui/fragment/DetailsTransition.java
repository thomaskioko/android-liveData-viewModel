package com.thomaskioko.livedatademo.view.ui.fragment;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;

/**
 *
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class DetailsTransition extends TransitionSet {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DetailsTransition() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds())
                .addTransition(new ChangeTransform())
                .setStartDelay(25)
                .setDuration(350)
                .addTransition(new ChangeImageTransform());
    }
}
