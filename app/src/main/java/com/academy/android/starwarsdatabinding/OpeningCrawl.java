package com.academy.android.starwarsdatabinding;

public class OpeningCrawl {

    private String mEpisode;

    private String mCrawl;

    public OpeningCrawl(String episode, String crawl) {
        mEpisode = episode;
        mCrawl = crawl;
    }

    public String getEpisode() {
        return mEpisode;
    }


    public String getCrawl() {
        return mCrawl;
    }
}
