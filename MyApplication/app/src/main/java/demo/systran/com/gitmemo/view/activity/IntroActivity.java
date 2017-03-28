package demo.systran.com.gitmemo.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import demo.systran.com.gitmemo.R;

public class IntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroActivity.this, MainFragmentActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(runnable, 3000);

    }
}
