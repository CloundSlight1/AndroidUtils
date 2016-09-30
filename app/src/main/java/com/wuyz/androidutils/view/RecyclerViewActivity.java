package com.wuyz.androidutils.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wuyz.androidutils.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wuyz on 2016/9/30.
 */

public class RecyclerViewActivity extends Activity {

    private LruCache<Integer, Bitmap> bitmapCaches = new LruCache<>(1024 * 1024 * 2);
    private List<Integer> ids = Arrays.asList(
            R.drawable.img_0
            , R.drawable.img_1
            , R.drawable.img_2
            , R.drawable.img_3
            , R.drawable.img_4
            , R.drawable.img_5
            , R.drawable.img_6
            , R.drawable.img_7
            , R.drawable.img_8
            , R.drawable.img_9
            , R.drawable.img_10
            , R.drawable.img_11
            , R.drawable.img_12
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list1_item, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                int id = ids.get(position);
                Bitmap bitmap = bitmapCaches.get(id);
                if (bitmap == null) {
                    bitmap = BitmapFactory.decodeResource(getResources(), id);
                    bitmapCaches.put(id, bitmap);
                }
                holder.imageView.setImageBitmap(bitmap);
            }

            @Override
            public int getItemCount() {
                return ids.size();
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image1);
        }
    }
}
