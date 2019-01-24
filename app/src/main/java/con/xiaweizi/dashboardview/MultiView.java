package con.xiaweizi.dashboardview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.MultiView
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/24
 *     desc   :
 * </pre>
 */
public class MultiView extends View {

    private static final String TAG = "MultiView::";
    private int bitmapWidth;
    private int bitmapHeigth;
    private Bitmap bitmap;
    private float offsetX;
    private float offsetY;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float downX;
    private float downY;
    private float orginalX;
    private float orginalY;
    private int trackingPointerId;


    public MultiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = getAvatar();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                trackingPointerId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                orginalX = offsetX;
                orginalY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(trackingPointerId);
                Log.i(TAG, "index: " + index + "  trackingPointerId: " + trackingPointerId);
                offsetX = orginalX + event.getX(index) - downX;
                offsetY = orginalY + event.getY(index) - downY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                trackingPointerId = event.getPointerId(actionIndex);
                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);
                orginalX = offsetX;
                orginalY = offsetY;
                Log.i(TAG, "ACTION_MOVE: "  + event.getActionIndex());
                break;

            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                if (pointerId == trackingPointerId) {
                    int newIndex;
                    if (actionIndex == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    trackingPointerId = event.getPointerId(newIndex);
                    downX = event.getX(actionIndex);
                    downY = event.getY(actionIndex);
                    orginalX = offsetX;
                    orginalY = offsetY;
                }
                break;
        }
        return true;
    }

    private Bitmap getAvatar() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.bg, options);
        options.inJustDecodeBounds = false;
        options.inDensity = 1;
        options.inTargetDensity = 2;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg, options);
        bitmapWidth = options.outWidth;
        bitmapHeigth = options.outHeight;
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }
}
