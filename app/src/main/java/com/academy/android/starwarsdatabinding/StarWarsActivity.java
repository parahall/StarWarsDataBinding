package com.academy.android.starwarsdatabinding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class StarWarsActivity extends AppCompatActivity
        implements View.OnClickListener, CrawlLoader.IOnLoadFinishListener {

    private TextView mEpisodeTextView;

    private TextView mCrawlTextView;

    private CrawlLoader mCrawlLoader;

    private Animation mCrawlAnimation;

    private boolean isCrawlsNotRunning = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_wars);

        findViewById(R.id.asw_btn_star_wars_logo).setOnClickListener(this);

        mEpisodeTextView = (TextView) findViewById(R.id.asw_tv_episode);
        mCrawlTextView = (TextView) findViewById(R.id.asw_tv_crawl);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCrawlLoader != null) {
            mCrawlLoader.stopCrawlLoader();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.asw_btn_star_wars_logo:
                startCrawls();
                break;
        }
    }

    private void startCrawls() {
        if (isCrawlsNotRunning) {
            isCrawlsNotRunning = false;
            mCrawlAnimation = AnimationUtils.loadAnimation(this, R.anim.crawl_animations);
            mCrawlLoader = new CrawlLoader(getAssets());
            mCrawlLoader.startLoading(this);
        } else {
            Toast.makeText(this, "Patience you must have my young padawan", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onLoadFinish(final OpeningCrawl openingCrawl) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEpisodeTextView.setText(openingCrawl.getEpisode());
                mCrawlTextView.setText(openingCrawl.getCrawl());
                mCrawlTextView.startAnimation(mCrawlAnimation);
            }
        });
    }
}
