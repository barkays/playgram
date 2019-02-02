package com.example.amirah.playgram2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private LinearLayout ll;
    private TextView[] mDots;
    private Button backBtn;
    private Button nextBtn;
    private int mCurrentPage;

    int number=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.credential_file_key), Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("first_time_use", true)){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        vp = findViewById(R.id.slideViewPager);
        ll = findViewById(R.id.dotsLayout);

        nextBtn = findViewById(R.id.button_next);
        backBtn = findViewById(R.id.button_back);

        SliderAdapter sliderAdapter = new SliderAdapter(this);

        vp.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        vp.addOnPageChangeListener(viewListener);

        //OnCLickListener

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number<2) {
                    vp.setCurrentItem(mCurrentPage + 1);
                    number++;
                }
                else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("first_time_use", false);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vp.setCurrentItem(mCurrentPage - 1);
                number--;

            }
        });


    }

    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        ll.removeAllViews();

        for (int i = 0; i < mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            ll.addView(mDots[i]);
        }
        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.purple));
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);
            mCurrentPage = i ;

            if( i == 0){

                nextBtn.setEnabled(true);
                backBtn.setEnabled(false);
                backBtn.setVisibility(View.INVISIBLE);

                nextBtn.setText("Next");
                backBtn.setText("");

            } else if (i == mDots.length - 1){

                nextBtn.setEnabled(true);
                backBtn.setEnabled(true);
                backBtn.setVisibility(View.VISIBLE);

                nextBtn.setText("Start");
                backBtn.setText("Back");

            } else{

                nextBtn.setEnabled(true);
                backBtn.setEnabled(true);
                backBtn.setVisibility(View.VISIBLE);

                nextBtn.setText("Next");
                backBtn.setText("Back");

            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };




}