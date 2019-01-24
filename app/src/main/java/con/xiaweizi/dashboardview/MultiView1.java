package con.xiaweizi.dashboardview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.MultiView1
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/24
 *     desc   :
 * </pre>
 */
public class MultiView1 extends View {
    private int bitmapWidth;
    private int bitmapHeigth;
    private Bitmap mBitmap;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float originalX;
    private float originalY;
    private float offsetX;
    private float offsetY;
    private float downX;
    private float downY;

    public MultiView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = getAvatar();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float sumX = 0;
        float sumY = 0;
        int pointerCount = event.getPointerCount();
        boolean isPointerUp = event.getAction() == MotionEvent.ACTION_POINTER_UP;
        for (int i = 0; i < pointerCount; i++) {
            if (!(isPointerUp && event.getActionIndex() == i)) {
                sumX += event.getX(i);
                sumY += event.getY(i);
            }
        }
        if (isPointerUp) {
            pointerCount -= 1;
        }
        float totalX = sumX / pointerCount;
        float totalY = sumY / pointerCount;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                downX = totalX;
                downY = totalY;
                originalX = offsetX;
                originalY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                offsetX = originalX + totalX - downX;
                offsetY = originalY + totalY - downY;
                invalidate();
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
        canvas.drawBitmap(mBitmap, offsetX, offsetY, mPaint);
    }
}
