package com.example.administrator.lhylearndemo.day1_16to1_29_view_Animation.view动画;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.lhylearndemo.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewAnimationTest extends AppCompatActivity {

    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.imageView4)
    ImageView imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Animation alphaAnimation =
                        AnimationUtils.loadAnimation(
                                this,
                                R.anim.base_animation_alpha);
                alphaAnimation.setFillAfter(true);
                imageView1.startAnimation(alphaAnimation);

                break;
            case R.id.button2:

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(imageView2, "alpha", 1, 0, 1).setDuration(3000),
                        ObjectAnimator.ofFloat(imageView2, "rotation", 0, -360, 0).setDuration(3000),
                        ObjectAnimator.ofFloat(imageView2, "scaleX", 1, 1.5f, 1).setDuration(3000),
                        ObjectAnimator.ofFloat(imageView2, "scaleY", 1, 1.5f, 1).setDuration(3000),
                        ObjectAnimator.ofFloat(imageView2, "translationX", 0, 500).setDuration(3000),
                        ObjectAnimator.ofFloat(imageView2, "translationY", 0, 500).setDuration(3000)

                );
                animatorSet.start();
                break;
            case R.id.button3:
                ViewWrapper viewWrapper = new ViewWrapper(imageView3);
                viewWrapper.view.setPivotX(50);
                viewWrapper.view.setPivotY(50);
                ObjectAnimator objectAnimator= ObjectAnimator.ofInt(viewWrapper, "viewHeight",
                        viewWrapper.getViewHeight(),0,viewWrapper.getViewHeight());
                objectAnimator.setDuration(5000);
                objectAnimator.start();

                break;
            case R.id.button4:

                break;
        }
    }

    @OnClick({R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4})
    public void onViewClicked1(View view) {
        switch (view.getId()) {
            case R.id.imageView1:
                Toast.makeText(this,"点击了第一个图片",Toast.LENGTH_LONG).show();
                break;
            case R.id.imageView2:
                Toast.makeText(this,"点击了第二个图片",Toast.LENGTH_LONG).show();
                break;
            case R.id.imageView3:
                Toast.makeText(this,"点击了第三个图片",Toast.LENGTH_LONG).show();
                break;
            case R.id.imageView4:
                Toast.makeText(this,"点击了第四个图片",Toast.LENGTH_LONG).show();
                break;
        }
    }

    public class ViewWrapper {
        private View view;

        public ViewWrapper (View view){
            this.view=view;
        }

        public void setViewHeight(int height){
            view.getLayoutParams().height=height;
            view.requestLayout();
        }

        public int getViewHeight(){
            return view.getLayoutParams().height;
        }
    }
}
