package com.example.anna_.hotpotato;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;
import android.os.Vibrator;


import static android.graphics.Color.*;

public class DisplayWords extends AppCompatActivity implements GestureDetector.OnGestureListener {



    final Vector<String> dictionary = new Vector<String>();
    final Vector<String> Scored =  new Vector<String>();

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;

    TextView timerTextView;
    long startTime = 0;

    private void endGame(ArrayList<String> dictionary, int[] count){
        String points = String.valueOf(count[0]);

        Intent intent = new Intent(this, GameScore.class);
        intent.putStringArrayListExtra("wordlist", dictionary);
        intent.putExtra("points",points);
        startActivity(intent);
        finish();
    }

    private void changeBackground(long milliseconds){
        View background = findViewById(R.id.space1);

        if(milliseconds == 50000){
            background.setBackgroundColor(WHITE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_display_words);



        Intent intent =getIntent();
        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this,this);

        final ArrayList<String> dictionary = intent.getStringArrayListExtra("wordlist");
        final TextView text = (TextView)findViewById(R.id.word);
        //initdictionary(dictionary);

        //shuffling the worslist to be displayed
        long seed = System.nanoTime();
        Collections.shuffle(dictionary, new Random(seed));

        text.setText(dictionary.get(0));
        final int[] count = {0};

        final Button bRight = (Button)findViewById(R.id.right);
        final Button bPass = (Button)findViewById(R.id.pass);




        bRight.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    //final RelativeLayout background  = (RelativeLayout) findViewById(R.id.activity_display_words);
                    //background.setBackgroundColor(Color.GREEN);
                    Scored.add(dictionary.get(0));
                    dictionary.remove(0);

                    if (dictionary.size() != 0) {
                        //Delay for the background to be green for .5 seconds
                        new CountDownTimer(500, 1000) {
                            public void onTick(long millisUntilFinished) {

                            }

                            public void onFinish() {
                                //background.setBackgroundColor(Color.WHITE);
                                text.setText(dictionary.get(0));

                            }
                        }.start();

                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(500);
                        count[0]=count[0] +1;
                    }

                    else {
                        text.setText("BOOOM");
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(2000);
                        String point = String.valueOf(count[0]);
                        //initdictionary(dictionary);
                        count[0] = 0;
                    }

                }
        });

        bPass.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(final View view) {
                    dictionary.remove(0);
                    //final RelativeLayout background  = (RelativeLayout) findViewById(R.id.activity_display_words);
                    //background.setBackgroundColor(Color.RED);

                    if (dictionary.size() != 0) {
                        //background.setBackgroundColor(Color.RED);
                        //garantees that the RED background is up for 0.5 seconds
                        new CountDownTimer(500, 1000) {

                            public void onTick(long millisUntilFinished) {

                            }

                            public void onFinish() {
                                //background.setBackgroundColor(Color.WHITE);
                                text.setText(dictionary.get(0));
                            }
                        }.start();

                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(500);

                    } else {
                        onPause();
                        text.setText("BOOOM");
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(2000);
                        //initdictionary(dictionary);
                        count[0] = 0;
                    }

                }
        });

        new CountDownTimer(70000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish(){

                endGame(dictionary,count);
            }
        }.start();


    }

    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
