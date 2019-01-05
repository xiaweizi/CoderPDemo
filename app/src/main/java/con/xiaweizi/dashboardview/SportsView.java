package con.xiaweizi.dashboardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.SportsView
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/05
 *     desc   :
 * </pre>
 */
public class SportsView extends View {
    private static final String TAG = "SportsView::";
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float angle = 0;
    private Rect rect = new Rect();
    private static final int STROKE_WIDTH = (int) DashBoardView.dp2px(18);
    private static final int RADIUS = (int) DashBoardView.dp2px(120);
    private int mWidth;
    private int mHeight;

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        new MyHandler(this).sendEmptyMessage(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, RADIUS, paint);

        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.ROUND);
        mWidth = getWidth() >> 1;
        mHeight = getHeight() >> 1;
        canvas.drawArc(mWidth - RADIUS, mHeight - RADIUS,
                mWidth + RADIUS, mHeight + RADIUS,
                -90, angle, false, paint);

        paint.setTextSize(DashBoardView.dp2px(30));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        int percent = (int) (angle / 360 * 100);
        String percentStr = percent + "%";
        paint.getTextBounds(percentStr, 0, percentStr.length(), rect);
        float offset = (rect.top + rect.bottom) >> 1;
        canvas.drawText(percentStr, mWidth, mHeight - offset, paint);
    }

    static class MyHandler extends Handler {
        WeakReference<SportsView> mView;

        MyHandler(SportsView view) {
            mView = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            SportsView theView = mView.get();
            if (theView == null || theView.getContext() == null) {
                return;
            }
            if (theView.angle >= 360) theView.angle = 0;
            theView.angle += 2;
            theView.invalidate();
            sendEmptyMessageDelayed(1, 20);
        }
    }
}
