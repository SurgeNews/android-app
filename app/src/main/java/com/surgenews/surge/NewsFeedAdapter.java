package com.surgenews.surge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anishhegde on 10/09/16.
 */
public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private List<RssModelItem> mDataset;
    private Context mContext;
    public ReadFragment.Callback mCallback;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitleText;
        public TextView mDesctext;
        public ImageView mImage;

        public ViewHolder(View v) {
            super(v);
            mTitleText = (TextView) v.findViewById(R.id.tv_title);
            mDesctext = (TextView) v.findViewById(R.id.tv_desc);
            mImage = (ImageView) v.findViewById(R.id.iv_feed);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsFeedAdapter(List<RssModelItem> myDataset, Context context, ReadFragment.Callback callback) {
        mDataset = myDataset;
        mContext = context;
        mCallback = callback;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_news, parent, false);
        // set the view's size, margins, paddings and layout parameter
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that elemen
        setText(holder.mTitleText,mDataset.get(position).getTitle());
        setText(holder.mDesctext,mDataset.get(position).getDescription());
        Picasso.with(mContext).load(mDataset.get(position).getImageUrl())
                .placeholder(R.drawable.surge_placeholder)
                .error(R.drawable.surge_placeholder).into(holder.mImage);

        holder.mTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallback!=null && mDataset.get(position).getFeedLink()!= null) {
                    mCallback.onArticleClick(mDataset.get(position).getFeedLink());
                }
            }
        });
    }

    private void setText(TextView tv, String value){
        if(value != null) {
            tv.setText(value);
        } else {
            tv.setText("No Data");
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
