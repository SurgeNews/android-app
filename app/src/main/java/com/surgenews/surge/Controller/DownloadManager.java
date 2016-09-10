package com.surgenews.surge.Controller;

import android.os.AsyncTask;

import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.DataFormatException;

/**
 * Created by anishhegde on 03/05/16.
 */
public class DownloadManager {

    static DownloadManager mDownloadManager = null;
    Callback mListener;

    public DownloadManager(){
    }

    public static DownloadManager Instance(){
        if(mDownloadManager == null){
            mDownloadManager = new DownloadManager();
        }
        return mDownloadManager;
    }

    public void downloadData(String inUrl, Callback listener){
        mListener = listener;
        new DownloadDataTask().execute(inUrl);
    }

    private class DownloadDataTask extends AsyncTask<Object,Void,Feed> {

        @Override
        protected Feed doInBackground(Object... params) {
            try {
                InputStream inputStream = new URL((String) params[0]).openConnection().getInputStream();
                Feed feed = EarlParser.parseOrThrow(inputStream, 0);
                return feed;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DataFormatException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Feed feed) {
            mListener.onFeed(feed);
        }
    }

    public interface Callback{
        void onFeed(Feed feed);
    }
}
