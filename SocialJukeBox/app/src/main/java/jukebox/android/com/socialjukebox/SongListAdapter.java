package jukebox.android.com.socialjukebox;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by f1vyer1 on 4/16/16.
 */
public class SongListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FeedItem> mFeedItemList;
    private Context mContext;
    LayoutInflater mLayoutInflater;
    public SongListAdapter(Context context, List<FeedItem> feedItemList) {
        this.mFeedItemList = feedItemList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.activity_list_item, parent, false);
        return  new SongListItemHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  SongListItemHolder) {
            FeedItem feedItem = mFeedItemList.get(position);
            SongListItemHolder listItemHolder = (SongListItemHolder) holder;
            listItemHolder.textView1.setText(feedItem.getSongName());
            listItemHolder.textView2.setText(feedItem.getUserName());
        }
    }


    @Override
    public int getItemCount() {
        return (null != mFeedItemList ? mFeedItemList.size() : 0);
    }

    public class SongListItemHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;



        public SongListItemHolder(final Context context, final View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.songName);
            textView2 = (TextView) itemView.findViewById(R.id.userName);


        }
    }
}