package con.xiaweizi.dashboardview;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private CircleView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.view);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "radius", 100, 200);
        objectAnimator.setDuration(3000);
        objectAnimator.setStartDelay(1000);
        objectAnimator.start();
    }
}
