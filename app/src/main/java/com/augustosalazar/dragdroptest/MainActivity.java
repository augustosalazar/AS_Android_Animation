package com.augustosalazar.dragdroptest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Outline;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;

public class MainActivity extends AppCompatActivity {

    private ViewOutlineProvider mOutlineProviderCircle;
    private String TAG  = "DragTest";
    private View mFloatingView,mFloatingView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOutlineProviderCircle = new CircleOutlineProvider();

               /* Find the {@link View} to apply z-translation to. */
        mFloatingView = findViewById(R.id.circle);
        mFloatingView2 = findViewById(R.id.circle2);

        /* Define the shape of the {@link View}'s shadow by setting one of the {@link Outline}s. */
        mFloatingView.setOutlineProvider(mOutlineProviderCircle);
        mFloatingView2.setOutlineProvider(mOutlineProviderCircle);

        /* Clip the {@link View} with its outline. */
        mFloatingView.setClipToOutline(true);
        mFloatingView2.setClipToOutline(true);

        DragFrameLayout dragLayout = ((DragFrameLayout) findViewById(R.id.main_layout));

        dragLayout.setDragFrameController(new DragFrameLayout.DragFrameLayoutController() {

            @Override
            public void onDragDrop(View view, boolean captured) {
                /* Animate the translation of the {@link View}. Note that the translation
                 is being modified, not the elevation. */

                // goal X 24 and Y 35, limit y 185 x 260
                //Log.d(TAG, captured ? "Drag" : "Drop");
                    if (view.getY() < 300 && view.getX() < 270  ){
                        Log.d(TAG, "Animating x "+view.getX()+" y "+view.getY());

                        createAnimation(view);
                } else {
                        Log.d(TAG, "x "+view.getX()+" y "+view.getY());
                        view.animate()
                            .translationZ(captured ? 50 : 0)
                            .setDuration(100);
                }
            }
        });

        dragLayout.addDragView(mFloatingView);
        dragLayout.addDragView(mFloatingView2);
    }

    private void createAnimation(View view){
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view,
                        "x", view.getX(), 24f),
                ObjectAnimator.ofFloat(view,
                        "y", view.getY(), 24f)
        );
        animatorSet.setDuration(1000).start();
    }

    public void onClickAnimate(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
        ObjectAnimator.ofFloat(mFloatingView,
                "x", mFloatingView.getX(), 24f),
        ObjectAnimator.ofFloat(mFloatingView,
                "y", mFloatingView.getY(), 24f)
        );
        animatorSet.setDuration(1000).start();

    }

    private class CircleOutlineProvider extends ViewOutlineProvider {
        @Override
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, view.getWidth(), view.getHeight());
        }
    }
}
