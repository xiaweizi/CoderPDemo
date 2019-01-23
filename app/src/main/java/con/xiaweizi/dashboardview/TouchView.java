package con.xiaweizi.dashboardview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.TouchView
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/01/22
 *     desc   :
 * </pre>
 */
public class TouchView extends View {
    private static final String TAG = "TouchView::";
    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "getActionMasked: " + event.getActionMasked());
        Log.i(TAG, "getAction: " + event.getAction());
        boolean b = super.onTouchEvent(event);
        Log.i(TAG, "onTouchEvent: " + b);
        return b;
    }

}
