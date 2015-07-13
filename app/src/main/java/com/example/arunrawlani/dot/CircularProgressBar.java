package com.example.arunrawlani.dot;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import com.interaxon.muse.museioreceiver.MuseIOReceiver;
import com.pascalwelsch.holocircularprogressbar.HoloCircularProgressBar;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;


public class CircularProgressBar extends Activity implements MuseIOReceiver.MuseDataListener {

    private static final String TAG = CircularProgressBar.class.getSimpleName();

    protected boolean mAnimationHasEnded = false;

    private Switch mAutoAnimateSwitch;

    private MuseIOReceiver museReceiver;

    /**
     * The Switch button.
     */
    private Button mColorSwitchButton;

    private HoloCircularProgressBar mHoloCircularProgressBar;

    private Button mOne;

    private ObjectAnimator mProgressBarAnimator;

    private Button startButton;

    private Button mZero;

    private int animation_counter = 0;

    private int progressColor = 0;

    private float currAlpha = 0f;
    private float currBeta = 0f;
    private float currTheta = 0f;
    private float currDelta = 0f;

    private float thetaBetaRatio = 0f;
    private float thetaAlphaRatio = 0f;

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        if (getIntent() != null) {
            final Bundle extras = getIntent().getExtras();
            if (extras != null) {
                final int theme = extras.getInt("theme");
                if (theme != 0) {
                    setTheme(theme);
                }
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.museReceiver = new MuseIOReceiver(5005, true);
        this.museReceiver.registerMuseDataListener(this);
        mHoloCircularProgressBar = (HoloCircularProgressBar) findViewById(
                R.id.holoCircularProgressBar);

        mColorSwitchButton = (Button) findViewById(R.id.random_color);
        mColorSwitchButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                switchColor();
            }
        });
        progressColor = mHoloCircularProgressBar.getProgressColor();
        startButton = (Button) findViewById(R.id.start_button);

        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startButton.setEnabled(false);
                animate(mHoloCircularProgressBar, new AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        animation_counter++;
                        Log.i("values counter",""+animation_counter+" "+thetaBetaRatio);
                        if(animation_counter==30){
                            if(mHoloCircularProgressBar.getProgress()>0.6f) {
                                Log.i("values counter","animation done");
                                AlertDialog.Builder builder = new AlertDialog.Builder(CircularProgressBar.this);
                                builder.setMessage("Optimal brain activity")
                                        .setTitle("Session Finished")
                                        .setPositiveButton(R.string.restart_session, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                CircularProgressBar.this.recreate();
                                            }
                                        });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            else{
                                AlertDialog.Builder builder= new AlertDialog.Builder(CircularProgressBar.this);
                                builder.setMessage("Sub-optimal brain activity")
                                        .setTitle("Session Finished")
                                        .setPositiveButton(R.string.restart_session, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                CircularProgressBar.this.recreate();
                                            }
                                        });

                                AlertDialog dialog= builder.create();
                                dialog.show();
                            }
                            return;
                        }
                        animate(mHoloCircularProgressBar,this);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });

        mZero = (Button) findViewById(R.id.zero);
        mZero.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mProgressBarAnimator != null) {
                    mProgressBarAnimator.cancel();
                }
                animate(mHoloCircularProgressBar, null, 0f, 1000);
                mHoloCircularProgressBar.setMarkerProgress(0f);

            }
        });

        mOne = (Button) findViewById(R.id.one);
        mOne.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mProgressBarAnimator != null) {
                    mProgressBarAnimator.cancel();
                }
                animate(mHoloCircularProgressBar, null, 1f, 1000);
                mHoloCircularProgressBar.setMarkerProgress(1f);

            }
        });

        mAutoAnimateSwitch = (Switch) findViewById(R.id.auto_animate_switch);
        mAutoAnimateSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    mOne.setEnabled(false);
                    mZero.setEnabled(false);

                    animate(mHoloCircularProgressBar, new AnimatorListener() {

                        @Override
                        public void onAnimationCancel(final Animator animation) {
                            animation.end();
                        }

                        @Override
                        public void onAnimationEnd(final Animator animation) {
                            if (!mAnimationHasEnded) {
                                float tbRatio = thetaBetaRatio/10f;
                                float taRatio = thetaAlphaRatio/10f;
                                Log.i("animation", tbRatio + " " + taRatio);
                                animate(mHoloCircularProgressBar, this, tbRatio, 1000);
                                //animate(mHoloCircularProgressBar2, this, taRatio,500);

                            } else {
                                mAnimationHasEnded = false;
                            }
                        }

                        @Override
                        public void onAnimationRepeat(final Animator animation) {
                        }

                        @Override
                        public void onAnimationStart(final Animator animation) {
                        }
                    });
                } else {
                    mAnimationHasEnded = true;
                    mProgressBarAnimator.cancel();

                    mOne.setEnabled(true);
                    mZero.setEnabled(true);
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            this.museReceiver.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.museReceiver.disconnect();
    }


    @Override
    public void receiveMuseElementsAlpha(MuseIOReceiver.MuseConfig config, final float[] alpha) {
        Log.i("values alpha", Arrays.toString(alpha));
        float average = (alpha[1] + alpha[2])/2f;
        if(Float.isNaN(average)) average =0f;
        currAlpha = (currAlpha+average)/2f;
    }

    @Override
    public void receiveMuseElementsBeta(MuseIOReceiver.MuseConfig config, final float[] beta) {
        float average = (beta[1] + beta[2])/2f;
        if(Float.isNaN(average)) average =0f;
        currBeta = (currBeta+average)/2f;
        Log.i("values beta", Arrays.toString(beta));
    }

    @Override
    public void receiveMuseElementsTheta(MuseIOReceiver.MuseConfig config, final float[] theta) {
        float average = (theta[1] + theta[2])/2f;
        if(Float.isNaN(average)) average =0f;
        currTheta = (currTheta+average)/2f;

        thetaBetaRatio = currTheta/currBeta;
        thetaAlphaRatio = currTheta/currAlpha;
        Log.i("values theta", Arrays.toString(theta));
    }

    @Override
    public void receiveMuseElementsDelta(MuseIOReceiver.MuseConfig config, final float[] delta) {
        float average = (delta[1] + delta[2])/2f;
        if(Float.isNaN(average)) average =0f;
        currDelta = (currDelta+average)/2f;
        Log.i("values delta", Arrays.toString(delta));
    }

    @Override
    public void receiveMuseEeg(MuseIOReceiver.MuseConfig config, float[] eeg) {
        // Do nothing
    }

    @Override
    public void receiveMuseAccel(MuseIOReceiver.MuseConfig config, float[] accel) {
        // Do nothing
    }

    @Override
    public void receiveMuseBattery(MuseIOReceiver.MuseConfig config, int[] battery) {
        // Do nothing
        Log.i("values battery", Arrays.toString(battery));
    }

    /**
     * generates random colors for the ProgressBar
     */
    protected void switchColor() {
        Random r = new Random();
        int randomColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        mHoloCircularProgressBar.setProgressColor(randomColor);

        randomColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        mHoloCircularProgressBar.setProgressBackgroundColor(randomColor);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.circular_progress_bar_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_switch_theme:
                switchTheme();
                break;

            default:
                Log.w(TAG, "couldn't map a click action for " + item);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Switch theme.
     */
    public void switchTheme() {

        final Intent intent = getIntent();
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final int theme = extras.getInt("theme", -1);
            if (theme == R.style.AppThemeLight) {
                getIntent().removeExtra("theme");
            } else {
                intent.putExtra("theme", R.style.AppThemeLight);
            }
        } else {
            intent.putExtra("theme", R.style.AppThemeLight);
        }
        finish();
        startActivity(intent);
    }

    /**
     * Animate.
     *
     * @param progressBar the progress bar
     * @param listener    the listener
     */
    private void animate(final HoloCircularProgressBar progressBar,
                         final AnimatorListener listener) {
        final float progress = (Math.max(0f,(10f - CircularProgressBar.this.thetaBetaRatio)))/10f;
        if(progress>0.6f && progressBar.getProgressColor() == progressColor){
            progressBar.setProgressColor(Color.rgb(0, 255, 0));
        }
        else if(progress<=0.6f && progressBar.getProgressColor() != progressColor){
            progressBar.setProgressColor(progressColor);
        }
        int duration = 500;
        animate(progressBar, listener, progress, duration);
    }

    private void animate(final HoloCircularProgressBar progressBar, final AnimatorListener listener,
                         final float progress, final int duration) {

        mProgressBarAnimator = ObjectAnimator.ofFloat(progressBar, "progress", progress);
        mProgressBarAnimator.setDuration(duration);

        mProgressBarAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationCancel(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }

            @Override
            public void onAnimationStart(final Animator animation) {
            }
        });
        if (listener != null) {
            mProgressBarAnimator.addListener(listener);
        }
        mProgressBarAnimator.reverse();
        mProgressBarAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                progressBar.setProgress((Float) animation.getAnimatedValue());
            }
        });
        progressBar.setMarkerProgress(progress);
        mProgressBarAnimator.start();
    }
}
