package con.xiaweizi.dashboardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;


/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.MultiView2
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/24
 *     desc   :
 * </pre>
 */
public class MultiView2 extends View {
    private SparseArray<Path> paths = new SparseArray<>();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    public MultiView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                Path path = new Path();
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex));
                paths.append(pointerId, path);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    pointerId = event.getPointerId(i);
                    path = paths.get(pointerId);
                    path.lineTo(event.getX(i), event.getY(i));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                paths.remove(event.getPointerId(event.getActionIndex()));
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = paths.size();
        for (int i = 0; i < size; i++) {
            Path path = paths.valueAt(i);
            canvas.drawPath(path, mPaint);
        }
    }
}
