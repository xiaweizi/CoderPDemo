package con.xiaweizi.dashboardview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.TouchLayout
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/23
 *     desc   :
 * </pre>
 */
public class TouchLayout extends ViewGroup {
    private static final String TAG = "TouchLayout";

    public TouchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        Log.i(TAG, "onInterceptTouchEvent: " + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = super.onTouchEvent(event);
        Log.i(TAG, "onTouchEvent: " + b);
        return b;
    }
}
