package com.wuyz.androidutils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.view.WindowManager;

/**
 * Created by wuyz on 2016/9/29.
 *
 */

public class ViewUtils {

    public static void setStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static Bitmap createCircleBitmap(Bitmap source, boolean recycleSource) {
        int width = source.getWidth();
        int height = source.getHeight();
        int diameter = Math.min(width, height);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap result = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        if(result != null) {
            Canvas canvas = new Canvas(result);
            canvas.drawCircle((float)(diameter / 2), (float)(diameter / 2), (float)(diameter / 2), paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, (float)((diameter - width) / 2), (float)((diameter - height) / 2), paint);
            if(recycleSource) {
                source.recycle();
                source = null;
            }
        } else {
            result = source;
        }

        return result;
    }
}
