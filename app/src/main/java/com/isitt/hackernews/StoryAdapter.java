package com.isitt.hackernews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isitt.hackernews.data.remote.HNService;
import com.isitt.hackernews.utilites.ApiUtils;
import com.isitt.hackernews.data.Item;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryAdapterViewHolder> {

    private static final String TAG = StoryAdapter.class.getSimpleName();

    private int[] mItems;

    final private  Context mContext;
    final private StoryAdapterOnClickHandler mClickHandler;

    public interface StoryAdapterOnClickHandler {
        void onClick(int id);
    }

    private HNService mService;

    public StoryAdapter(@NonNull Context context, StoryAdapterOnClickHandler clickHandler) {
        mItems = new int[] {} ;
        mContext = context;
        mClickHandler = clickHandler;
        mService = ApiUtils.getHNService();
    }


    @Override
    public StoryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.story_list_item, parent, false);

        view.setFocusable(true);

        return new StoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoryAdapterViewHolder holder, int position) {
        holder.setID(mItems[position]);
    }

    @Override
    public int getItemCount() {
        return mItems.length;
    }

    void updateData(int[] newItems) {
        mItems = newItems;
        notifyDataSetChanged();
    }

    class StoryAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final String TAG = StoryAdapter.class.getSimpleName();

        private int mId;
        private Item mItem;

        TextView titleView;

        StoryAdapterViewHolder(View view) {
            super(view);

            titleView = view.findViewById(R.id.story_list_item_title);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.v(TAG, "Clicked!");
        }

        public void setID(int id) {

            if (mId == id) {

            } else {

                mId = id;

                mService.getItem(mId + "").enqueue(new Callback<Item>() {

                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {

                        if (response.isSuccessful()) {
                            setItem(response.body());
                        } else {
                            int statusCode = response.code();
                            Log.e(TAG, "Response: " + statusCode);
                        }
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {
                        // Show error
                        Log.d(TAG, "error loading from API");

                    }
                });
            }
        }

        public void setItem(Item item) {

            mItem = item;

            titleView.setText(mItem.getTitle());
        }
    }
}