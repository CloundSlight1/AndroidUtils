package com.wuyz.androidutils.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.wuyz.androidutils.Log2;
import com.wuyz.androidutils.R;
import com.wuyz.androidutils.manager.ViewUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wuyz on 2016/9/29.
 *
 */

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.setStatusBarTransparent(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recyclerView:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.daoDemo:
                startActivity(new Intent(this, DaoDemoActivity.class));
                break;
            case R.id.okHttp:
                Request.Builder builder = new Request.Builder();
                builder.get().url("http://www.codeceo.com/");
                Request request = builder.build();
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log2.e(TAG, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log2.d(TAG, "onResponse %s", response.body().string());
                    }
                });
                break;
        }
    }
}
