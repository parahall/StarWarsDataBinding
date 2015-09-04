package com.academy.android.starwarsdatabinding;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CrawlLoader {

    private final AssetManager mAssets;

    private Thread mThread;

    public CrawlLoader(AssetManager assets) {
        mAssets = assets;
    }

    public void startLoading(final IOnLoadFinishListener onLoadFinishListener) {
        if (mThread == null || !mThread.isAlive()) {
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i < 7; i++) {
                        String episode = null;
                        String crawl;
                        BufferedReader reader = null;
                        try {
                            reader = new BufferedReader(
                                    new InputStreamReader(
                                            mAssets.open(String.format("star_wars_crawl%s.txt", i)),
                                            "UTF-8"));

                            String mLine = reader.readLine();
                            while (mLine != null) {
                                //process line
                                if (episode == null) {
                                    episode = mLine;
                                } else {
                                    crawl = mLine;
                                    OpeningCrawl openingCrawl = new OpeningCrawl(episode, crawl);
                                    onLoadFinishListener.onLoadFinish(openingCrawl);
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                        break;
                                    }
                                }
                                mLine = reader.readLine();
                            }
                        } catch (IOException e) {
                            //log the exception
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e) {
                                    //log the exception
                                }
                            }
                        }
                    }
                }
            });
            mThread.start();
        }

    }

    public void stopCrawlLoader() {
        mThread.interrupt();
    }

    interface IOnLoadFinishListener {

        void onLoadFinish(OpeningCrawl openingCrawls);
    }
}
