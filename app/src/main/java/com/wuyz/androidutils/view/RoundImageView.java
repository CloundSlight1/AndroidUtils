package com.wuyz.androidutils.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by wuyz on 04/09/2016.
 */
public class RoundImageView extends ImageView {

    private Paint paint;
    private Bitmap bitmap;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getMeasuredWidth();
        if (bitmap == null) {
            BitmapDrawable drawable = (BitmapDrawable) getDrawable();
            if (drawable != null) {
                Bitmap bitmap = drawable.getBitmap();
                if (bitmap != null) {
                    this.bitmap = Bitmap.createScaledBitmap(bitmap, w, w, true);
                    bitmap.recycle();
                    bitmap = null;
                    paint.setShader(new BitmapShader(this.bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                }
            }
        }
        if (bitmap != null) {
            canvas.drawCircle(w >> 1, w >> 1, w >> 1, paint);
        }
    }
}
