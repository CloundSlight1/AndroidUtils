package com.wuyz.androidutils.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wuyz.androidutils.App;
import com.wuyz.androidutils.DaoSession;
import com.wuyz.androidutils.Log2;
import com.wuyz.androidutils.R;
import com.wuyz.androidutils.User;
import com.wuyz.androidutils.UserDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by wuyz on 2017/1/3.
 * DaoDemoActivity
 */

public class DaoDemoActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "DaoDemoActivity";

    private UserDao userDao;
    private Query<User> userQuery;
    private static long id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao);

        DaoSession daoSession = App.getInstance().getDaoSession();
        userDao = daoSession.getUserDao();
        userQuery = userDao.queryBuilder().orderAsc(UserDao.Properties.Name).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                User user = new User(id++, "wuyz", 30);
                long ret = userDao.insert(user);
                Log2.d(TAG, "ret %d", ret);
                Toast.makeText(this, String.valueOf(ret), Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                List<User> list = userQuery.list();
                StringBuilder builder = new StringBuilder();
                for (User u : list) {
                    Log2.d(TAG, "%s", u);
                    builder.append(u.toString()).append('\n');
                }
                Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();
                break;
        }
    }
}
