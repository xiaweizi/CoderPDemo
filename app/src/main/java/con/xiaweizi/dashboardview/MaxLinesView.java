package con.xiaweizi.dashboardview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.MaxLinesView
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/05
 *     desc   :
 * </pre>
 */
public class MaxLinesView extends View {
    private static final String TAG = "MaxLinesView::";
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int bitmapWidth;
    private TextPaint textPaint = new TextPaint();
    private static final String content = "这个值和透明度通道有关,这个值和透明度通道有关,这个值和透明度通道有关,这个值和透明度通道有关,这个值和透明度通道有关,这个值和透明度通道有关,这个值和透明度通道有关,这个值和透明度通道有关,这个值和透明度通道有关,";
    private float[] measureWidth = new float[1];

    public MaxLinesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Bitmap getAvatar() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.bg, options);
        options.inJustDecodeBounds = false;
        options.inDensity = 4;
        options.inTargetDensity = 1;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg, options);
        bitmapWidth = options.outWidth;
        return bitmap;
    }

    private float textSize;

    {
        textSize = DashBoardView.dp2px(15);
        paint.setTextSize(textSize);
        textPaint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getAvatar() == null) return;
        int culIndex = 0;
        int totalIndex = 0;
        canvas.drawBitmap(getAvatar(), getWidth() - bitmapWidth, 80, paint);
        culIndex = paint.breakText(content, true, getWidth(), measureWidth);
        canvas.drawText(content, 0, totalIndex += culIndex, 0, textSize, paint);
        Log.i(TAG, "measureWidth: " + Arrays.toString(measureWidth));

        culIndex = paint.breakText(content, totalIndex, content.length(), true, getWidth(), measureWidth);
        canvas.drawText(content, totalIndex, totalIndex += culIndex, 0, textSize + paint.getFontSpacing(), paint);
        culIndex = paint.breakText(content, totalIndex, content.length(), true, getWidth() - bitmapWidth, measureWidth);
        canvas.drawText(content, totalIndex, totalIndex += culIndex, 0, textSize + paint.getFontSpacing() * 2, paint);
        culIndex = paint.breakText(content, totalIndex, content.length(), true, getWidth() - bitmapWidth, measureWidth);
        canvas.drawText(content, totalIndex, totalIndex += culIndex, 0, textSize + paint.getFontSpacing() * 3, paint);
        Log.i(TAG, "measureWidth: " + Arrays.toString(measureWidth));
    }
}
