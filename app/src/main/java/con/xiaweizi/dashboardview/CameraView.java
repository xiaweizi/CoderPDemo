package con.xiaweizi.dashboardview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.CameraView
 *     e-mail : 1012126908@qq.com
 *     time   : degrees19/01/05
 *     desc   :
 * </pre>
 */
public class CameraView extends View {
    private static final String TAG = "CameraView::";
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Camera camera = new Camera();
    private float bitmapWidth;
    private float bitmapHeigth;
    private Bitmap avatar;
    private int degrees;

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setColor(Color.RED);
        camera.rotateX(40);
        camera.setLocation(0, 0, -8 * Resources.getSystem().getDisplayMetrics().density);
        avatar = getAvatar();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        long lastTime = System.currentTimeMillis();
        canvas.save();
        canvas.translate(100 + bitmapWidth / 2, 100 + bitmapHeigth / 2);
        canvas.rotate(degrees);
        canvas.clipRect(-getWidth(), -bitmapHeigth, getWidth(), 0);
        canvas.rotate(-degrees);
        canvas.translate(-100 - bitmapWidth / 2, -100 - bitmapHeigth / 2);
        canvas.drawBitmap(avatar, 100, 100, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(100 + bitmapWidth / 2, 100 + bitmapHeigth / 2);
        canvas.rotate(degrees);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-getWidth(), 0, getWidth(), bitmapHeigth);
        canvas.rotate(-degrees);
        canvas.translate(-(100 + bitmapWidth / 2), -(100 + bitmapHeigth / 2));
        canvas.drawBitmap(avatar, 100, 100, paint);
        canvas.restore();
        Log.i(TAG, "onDraw: " + (System.currentTimeMillis() - lastTime));
    }

    private Bitmap getAvatar() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.bg, options);
        options.inJustDecodeBounds = false;
        options.inDensity = 1;
        options.inTargetDensity = 1;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg, options);
        bitmapWidth = options.outWidth;
        bitmapHeigth = options.outHeight;
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();
            float x1 = x - (100 + bitmapWidth / 2);
            float y1 = y - (100 + bitmapHeigth / 2);
            degrees = (int) Math.toDegrees(Math.atan2(y1, x1));
            if (y1 < 0) {
                degrees += 360;
            }
            degrees = 90 - degrees;
            if (degrees < 0) {
                degrees += 360;
            }
            degrees = -degrees;
            invalidate();
            Log.i(TAG, "agree: " + degrees);
        }
        return true;
    }
}
