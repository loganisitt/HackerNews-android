package com.isitt.hackernews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.isitt.hackernews.data.Item;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    private WebView mWebView;

    public static final String DetailBundleKey = "itemBundle";

    private Item mItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        mWebView = findViewById(R.id.wv_item_link);
        
        Bundle detailBundle = getIntent().getBundleExtra(DetailBundleKey);

        mItem = Parcels.unwrap(detailBundle.getParcelable(Item.ItemKey));

        if (mItem != null) {
            setTitle(mItem.getTitle());

            mWebView.loadUrl(mItem.getUrl(), null);
        }
    }
}
