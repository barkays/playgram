package com.example.amirah.playgram2;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amirah.playgram2.service.PostingService;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private LinearLayout ll;
    private TextView[] mDots;
    private SliderAdapter sliderAdapter;
    private Button backBtn;
    private Button nextBtn;
    private int mCurrentPage;

    private Intent mServiceIntent;
    private PostingService mPostingService;

    int number=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mPostingService = new PostingService();
        mServiceIntent = new Intent(getApplicationContext(), mPostingService.getClass());

        if (!isServicesRunning(mPostingService.getClass())) {
            startService(mServiceIntent);
        }

        vp = (ViewPager) findViewById(R.id.slideViewPager);
        ll = (LinearLayout) findViewById(R.id.dotsLayout) ;

        nextBtn = (Button) findViewById(R.id.button_next);
        backBtn = (Button) findViewById(R.id.button_back);

        sliderAdapter = new SliderAdapter(this);

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

    private boolean isServicesRunning(Class<? extends PostingService> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
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