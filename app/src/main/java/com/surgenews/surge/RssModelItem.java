package com.surgenews.surge;

/**
 * Created by anishhegde on 10/09/16.
 */
public class RssModelItem {

    private String mImageUrl;
    private String mTitle;
    private String mDescription;
    private String mFeedLink;

    public String getFeedLink() {
        return mFeedLink;
    }

    public void setFeedLink(String mFeedLink) {
        this.mFeedLink = mFeedLink;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }


}
