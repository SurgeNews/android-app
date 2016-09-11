package com.surgenews.surge.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;
import com.surgenews.surge.Controller.DownloadManager;
import com.surgenews.surge.Adapter.NewsFeedAdapter;
import com.surgenews.surge.R;
import com.surgenews.surge.Model.RssModelItem;
import com.surgenews.surge.Utils.WebserviceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anishhegde on 10/09/16.
 */
public class ReadFragment extends Fragment {

    public static final String ARG_OBJECT = "object";
    private RecyclerView mRecyclerView;
    private NewsFeedAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<RssModelItem> mDataSet;
    private RelativeLayout mRootLayout;
    private Callback mCallback;
    private boolean isActivityCreated = false;

    public void setCallback(Callback callback){
        mCallback = callback;
    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.read_fragment, container, false);
        mRootLayout = (RelativeLayout)  rootView.findViewById(R.id.layout_root);
        Bundle args = getArguments();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_news);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mDataSet = new ArrayList<>();
        mAdapter = new NewsFeedAdapter(mDataSet,getActivity().getApplicationContext(),mCallback);
        mRecyclerView.setAdapter(mAdapter);
        setFeed();

        return rootView;
    }

    void setFeed(){
        if(WebserviceUtils.isInternetAvailable(getActivity().getApplicationContext())) {
            DownloadManager.Instance().downloadData("http://rss.cnn.com/rss/edition.rss", new DownloadManager.Callback() {
                @Override
                public void onFeed(Feed feed) {
                    if(feed != null) {
                        for (Item item : feed.getItems()) {
                            RssModelItem rssItem = new RssModelItem();
                            rssItem.setDescription(item.getDescription());
                            rssItem.setTitle(item.getTitle());
                            rssItem.setImageUrl(item.getImageLink());
                            rssItem.setFeedLink(item.getLink());
                            mDataSet.add(rssItem);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showRetrySnackBar();
                    }
                }
            });
        } else {
            if(isActivityCreated){
                showRetrySnackBar();
            }
        }
    }

    void showRetrySnackBar(){
        if(!WebserviceUtils.isInternetAvailable(getActivity().getApplicationContext())){
            final Snackbar snackbar = Snackbar.make(mRootLayout, "Oops! The network seems wonky", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                    setFeed();
                }
            }).show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isActivityCreated = true;
        showRetrySnackBar();
    }

    public interface Callback{
        void onArticleClick(String url);
    }
}
