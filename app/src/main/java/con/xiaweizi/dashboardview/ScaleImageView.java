package con.xiaweizi.dashboardview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.ScaleImageView
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/23
 *     desc   :
 * </pre>
 */
public class ScaleImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable, ScaleGestureDetector.OnScaleGestureListener {
    private static final String TAG = "ScaleImageView::";
    private static final float OVER_RATIO = 1.5f;
    private int bitmapWidth;
    private int bitmapHeigth;
    private int originOffsetX;
    private float originOffsetY;
    private float offsetX;
    private float offsetY;
    private Bitmap mBitmap;
    private Paint paint;
    private float smallScale;
    private float bigScale;
    private boolean big = false;
    private final GestureDetectorCompat detectorCompat;
    private ScaleGestureDetector scaleGestureDetector;
    private float currentScale;
    private ObjectAnimator objectAnimator;
    private OverScroller overScroller;

    public float getScaleFraction() {
        return currentScale;
    }

    public void setScaleFraction(float scaleFraction) {
        this.currentScale = scaleFraction;
        invalidate();
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = getAvatar();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        detectorCompat = new GestureDetectorCompat(context, this);
        detectorCompat.setOnDoubleTapListener(this);
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        overScroller = new OverScroller(context);
    }

    private ObjectAnimator getScaleAnimator() {
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", 0, 1);
        }
        objectAnimator.setFloatValues(smallScale, bigScale);
        return objectAnimator;
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originOffsetX = (w - bitmapWidth) / 2;
        originOffsetY = (h - bitmapHeigth) / 2;
        if ((bitmapWidth / bitmapHeigth) > (w / h)) {
            smallScale = w * 1f / bitmapWidth;
            bigScale = h * 1f / bitmapHeigth * OVER_RATIO;
        } else {
            bigScale = w * 1f / bitmapWidth;
            smallScale = h * 1f / bitmapHeigth * OVER_RATIO;
        }
        currentScale = smallScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float fraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(offsetX * fraction, offsetY * fraction);
        canvas.scale(currentScale, currentScale, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(mBitmap, originOffsetX, originOffsetY, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = scaleGestureDetector.onTouchEvent(event);
        if (!scaleGestureDetector.isInProgress()) {
            result = detectorCompat.onTouchEvent(event);
        }
        return result;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(TAG, "onShowPress: ");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(TAG, "onSingleTapUp: ");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (big) {
            offsetX -= distanceX;
            offsetY -= distanceY;
            offsetX = Math.max(Math.min((bitmapWidth * bigScale - getWidth()) / 2, offsetX), -(bitmapWidth * bigScale - getWidth()) / 2);
            offsetY = Math.max(Math.min((bitmapHeigth * bigScale - getHeight()) / 2, offsetY), -(bitmapHeigth * bigScale - getHeight()) / 2);
            Log.i(TAG, "offsetX: " + offsetX);
            invalidate();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(TAG, "onLongPress: ");
    }

    @Override
    public boolean onFling(MotionEvent down, MotionEvent event, float velocityX, float velocityY) {
        if (big) {
            overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                    -(int) (bitmapWidth * bigScale - getWidth()) / 2,
                    (int) (bitmapWidth * bigScale - getWidth()) / 2,
                    -(int) (bitmapHeigth * bigScale - getHeight()) / 2,
                    (int) (bitmapHeigth * bigScale - getHeight()) / 2);
            postOnAnimation(this);
        }
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.i(TAG, "onSingleTapConfirmed: ");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG, "onDoubleTap: ");
        big = !big;
        if (big) {
            offsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2f) * bigScale / smallScale;
            offsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2f) * bigScale / smallScale;
            offsetX = Math.max(Math.min((bitmapWidth * bigScale - getWidth()) / 2, offsetX), -(bitmapWidth * bigScale - getWidth()) / 2);
            offsetY = Math.max(Math.min((bitmapHeigth * bigScale - getHeight()) / 2, offsetY), -(bitmapHeigth * bigScale - getHeight()) / 2);
            getScaleAnimator().start();
        } else {
            getScaleAnimator().reverse();
        }
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.i(TAG, "onDoubleTapEvent: ");
        return false;
    }

    @Override
    public void run() {
        if (overScroller.computeScrollOffset()) {
            offsetX = overScroller.getCurrX();
            offsetY = overScroller.getCurrY();
            invalidate();
            postOnAnimation(this);
        }
    }

    float initScale;

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.i(TAG, "onScale: " + detector.getScaleFactor());
        currentScale = initScale * detector.getScaleFactor();
        invalidate();
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Log.i(TAG, "onScaleBegin: ");
        initScale = currentScale;
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.i(TAG, "onScaleEnd: ");
    }
}
