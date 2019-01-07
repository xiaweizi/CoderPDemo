package con.xiaweizi.dashboardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.CircleView
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/07
 *     desc   :
 * </pre>
 */
public class CircleView extends View {
    private static final String TAG = "CircleView::";
    private float radius = 100;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, radius, mPaint);
    }
}
