package con.xiaweizi.dashboardview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.DashBoardView
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/03
 *     desc   :
 * </pre>
 */
public class DashBoardView extends View {
    private static final String TAG = "DashBoardView::";

    private static final int RADIUS = (int) dp2px(120);
    private static final int RECT_WIDTH = (int) dp2px(2);
    private static final int RECT_HEIGHT = (int) dp2px(8);
    private static final int ANGLE = 120;
    private static final int LENGTH = (int) dp2px(80);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path arc = new Path();
    private PathDashPathEffect pathDashPathEffect;
    private float endX;
    private float endY;

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp2px(2));
        arc.addArc((getWidth() >> 1) - RADIUS,
                (getHeight() >> 1) - RADIUS,
                (getWidth() >> 1) + RADIUS,
                (getHeight() >> 1) + RADIUS,
                90 + (ANGLE >> 1),
                360 - ANGLE);
        Path dash = new Path();
        dash.addRect(0, 0, RECT_WIDTH, RECT_HEIGHT, Path.Direction.CW);
        PathMeasure pathMeasure = new PathMeasure(arc, false);
        float advance = (pathMeasure.getLength() - RECT_WIDTH) * 1.0f / 20;
        pathDashPathEffect = new PathDashPathEffect(dash, advance, 0, PathDashPathEffect.Style.ROTATE);
        Log.i(TAG, "length:\t" + Math.toRadians(60));
        Log.i(TAG, "length:\t" + Math.cos(Math.toRadians(60)));
    }

    private int getAngleFromMark(int mark) {
        return 90 + ANGLE / 2 + (360 - ANGLE / 20 * mark);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc((getWidth() >> 1) - RADIUS,
                (getHeight() >> 1) - RADIUS,
                (getWidth() >> 1) + RADIUS,
                (getHeight() >> 1) + RADIUS,
                90 + (ANGLE >> 1),
                360 - ANGLE, false, mPaint);
        mPaint.setPathEffect(pathDashPathEffect);
        canvas.drawArc((getWidth() >> 1) - RADIUS,
                (getHeight() >> 1) - RADIUS,
                (getWidth() >> 1) + RADIUS,
                (getHeight() >> 1) + RADIUS,
                90 + (ANGLE >> 1),
                360 - ANGLE, false, mPaint);
        mPaint.setPathEffect(null);
        canvas.drawLine(getWidth() >> 1, getHeight() >> 1,
                endX,
                endY, mPaint);
    }

    private static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();
            float r = (float) Math.sqrt((x - (getWidth() >> 1)) * (x - (getWidth() >> 1)) + (y - (getHeight() >> 1)) * (y - (getHeight() >> 1)));
            Log.i(TAG, "x: " + x + " y: " + y + " r: " + r + " w: " + (getWidth() >> 1) + " h: " + (getHeight() >> 1));
            float x1 = x - (getWidth() >> 1);
            float y1 = y - (getHeight() >> 1);
            if (y1 > 0) {
                Log.w(TAG, "x1: " + x1 + " y1: " + y1 + " tan: " + (x1 / y1));
                if (x1 / y1 > -Math.sqrt(3f) && x1 / y1 < Math.sqrt(3f)) return true;
            }
            endX = x1 * LENGTH / r + (getWidth() >> 1);
            endY = y1 * LENGTH / r + (getHeight() >> 1);
            float r1 = (float) Math.sqrt(endX * endX + endY * endY);
            Log.i(TAG, "endX: " + endX + " endY: " + endY + " r1: " + r1 + " length: " + LENGTH);
            invalidate();
        }
        return true;
    }
}
