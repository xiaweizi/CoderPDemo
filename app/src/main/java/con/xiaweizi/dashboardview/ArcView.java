package con.xiaweizi.dashboardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.ArcView
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/04
 *     desc   :
 * </pre>
 */
public class ArcView extends View {
    private static final String TAG = "ArcView::";
    private static final int[] angles = new int[]{60, 120, 80, 100};
    private static final int[] colors = new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.GRAY};
    private static final int LENGTH = angles.length;
    private static final int RADIUS = (int) DashBoardView.dp2px(100);
    private static final int OFFSET = (int) DashBoardView.dp2px(10);
    private RectF arcRect = new RectF();
    private int selected = 1;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        arcRect.set((w >> 1) - RADIUS, (h >> 1) - RADIUS, (w >> 1) + RADIUS, (h >> 1) + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentAngle = 0;
        for (int i = 0; i < LENGTH; i++) {
            canvas.save();
            if (i + 1 == selected) {
                Log.i(TAG, "x: " + (float) (Math.cos(Math.toRadians(currentAngle + (angles[i] >> 1))) * OFFSET));
                Log.i(TAG, "y: " + (float) (Math.sin(Math.toRadians(currentAngle + (angles[i] >> 1))) * OFFSET));
                canvas.translate((float) (Math.cos(Math.toRadians(currentAngle + (angles[i] >> 1))) * OFFSET), (float) (Math.sin(Math.toRadians(currentAngle + angles[i] / 2)) * OFFSET));
            }
            mPaint.setColor(colors[i]);
            canvas.drawArc(arcRect, currentAngle, angles[i], true, mPaint);
            canvas.restore();
            currentAngle += angles[i];
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            float x1 = x - (getWidth() >> 1);
            float y1 = y - (getHeight() >> 1);
            double agree = Math.toDegrees(Math.atan2(y1, x1));
            if (y1 < 0) {
                agree += 360;
            }
            selected = getIndex(agree);
            Log.w(TAG, "x1: " + x1 + " y1: " + y1);
            Log.i(TAG, "agree: " + agree);
            Log.e(TAG, "index: " + selected);
            invalidate();
        }
        return true;
    }

    private int getIndex(double degree) {
        int index = -1;
        int currentDegree = 0;
        for (int i = 0; i < LENGTH; i++) {
            if ((degree - currentDegree) < 0) {
                index = i;
                break;
            }
            index = i + 1;
            currentDegree += angles[i];
        }
        return index;
    }
}
