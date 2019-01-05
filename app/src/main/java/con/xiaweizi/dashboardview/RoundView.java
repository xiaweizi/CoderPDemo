package con.xiaweizi.dashboardview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.RoundView
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/05
 *     desc   :
 * </pre>
 */
public class RoundView extends View {
    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private static final int RADIUS = (int) DashBoardView.dp2px(120);
    private static final int STROKE = (int) DashBoardView.dp2px(10);
    private RectF rectF = new RectF();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;

    public RoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.set((w >> 1) - RADIUS, (h >> 1) - RADIUS, (w >> 1) + RADIUS, (h >> 1) + RADIUS);
    }

    private Bitmap getAvatar() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.bg, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = 2 * RADIUS;
        return BitmapFactory.decodeResource(getResources(), R.drawable.bg, options);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(rectF, paint);
        int saveLayer = canvas.saveLayer(rectF, paint);
        canvas.drawOval((getWidth() >> 1) - RADIUS + STROKE, (getHeight() >> 1) - RADIUS + STROKE,
                (getWidth() >> 1) + RADIUS - STROKE, (getHeight() >> 1) + RADIUS - STROKE, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(getAvatar(), (getWidth() >> 1) - RADIUS, (getHeight() >> 1) - RADIUS, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
    }
}
