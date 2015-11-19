package com.academy.android.starwarsdatabinding;

import android.content.res.AssetManager;
import android.databinding.ObservableField;
import android.view.View;

public class StarWarsViewModel implements CrawlLoader.IOnLoadFinishListener {

    private final AssetManager mAssets;

    public ObservableField<String> episode = new ObservableField<>();

    public ObservableField<String> crawl = new ObservableField<>();

    private StarWasViewModelListener mView;

    private CrawlLoader mCrawlLoader;


    private boolean isCrawlsNotRunning = true;

    public StarWarsViewModel(StarWasViewModelListener view, AssetManager assets) {
        mAssets = assets;
        mView = view;
    }

    public void onStop() {
        if (mCrawlLoader != null) {
            mCrawlLoader.stopCrawlLoader();
        }

    }

    public void onDestroy() {
        mView = null;
    }


    public void onLogoClicked(View view) {
        startCrawls();
    }


    private void startCrawls() {
        if (isCrawlsNotRunning) {
            isCrawlsNotRunning = false;
            mCrawlLoader = new CrawlLoader(mAssets);
            mCrawlLoader.startLoading(this);
        } else {
            if (mView != null) {
                mView.showToast();
            }
        }
    }

    @Override
    public void onLoadFinish(OpeningCrawl openingCrawls) {
        episode.set(openingCrawls.getEpisode());
        crawl.set(openingCrawls.getCrawl());
        if (mView != null) {
            mView.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mView != null) {
                        mView.playAnimation();
                    }
                }
            });
        }
    }


    public interface StarWasViewModelListener {

        void playAnimation();

        void showToast();

        void runOnUiThread(Runnable runnable);
    }
}
