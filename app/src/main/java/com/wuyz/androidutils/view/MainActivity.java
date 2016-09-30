package com.wuyz.androidutils.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.wuyz.androidutils.R;
import com.wuyz.androidutils.utils.ViewUtils;

/**
 * Created by wuyz on 2016/9/29.
 */

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.setStatusBarTransparent(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        findViewById(R.id.recyclerView).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recyclerView:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
        }
    }
}
