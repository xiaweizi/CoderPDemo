package con.xiaweizi.dashboardview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.OneHandView
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/08
 *     desc   :
 * </pre>
 */
public class OneHandView extends View {

    private static final String TAG = "OneHandView::";
    public OneHandView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: width:\t" + MeasureSpec.getSize(widthMeasureSpec));
        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "onMeasure: EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, "onMeasure: UNSPECIFIED");
                break;
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "onMeasure: AT_MOST");
                break;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: ");
    }
}
