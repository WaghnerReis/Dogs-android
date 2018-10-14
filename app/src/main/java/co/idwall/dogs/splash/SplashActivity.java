package co.idwall.dogs.splash;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import co.idwall.dogs.R;
import co.idwall.dogs.behavior.user.preferences.Constants;
import co.idwall.dogs.behavior.user.preferences.UserPreferences;
import co.idwall.dogs.dogs.view.DogsActivity;
import co.idwall.dogs.log.in.LogInActivity;

public class SplashActivity extends AppCompatActivity implements Animator.AnimatorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startAnimation();
    }

    private void startAnimation() {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);

        ImageView logo = findViewById(R.id.logo);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(logo, alpha, x, y);
        animator.setDuration(1000);
        animator.addListener(this);
        animator.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        nextActivity();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    public void nextActivity() {
        Intent intent = new Intent();

        UserPreferences userPreferences = new UserPreferences(this);
        if (userPreferences.getToken().equals(Constants.ERROR)) {
            intent.setClass(this, LogInActivity.class);
        } else {
            intent.setClass(this, DogsActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
