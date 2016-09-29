package com.wuyz.androidutils.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.wuyz.androidutils.R;
import com.wuyz.androidutils.ViewUtils;

/**
 * Created by wuyz on 2016/9/29.
 *
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.setStatusBarTransparent(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }
}
