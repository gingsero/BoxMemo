package demo.systran.com.gitmemo.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import demo.systran.com.gitmemo.R;
import demo.systran.com.gitmemo.callback.CallBackInterface;
import demo.systran.com.gitmemo.service.ServiceController;

public class IntroActivity extends Activity {
    private String TAG = "IntroActivity";

    private CallBackInterface dbCallBack = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        dbCallBack = new CallBackInterface(){
            @Override
            public void callback() {

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(IntroActivity.this, MainFragmentActivity.class);
                        startActivity(intent);
                        finish();
                    }
                };

                Handler handler = new Handler();
                handler.postDelayed(runnable, 3000); // Go to MainActivity after 3 seconds
            }
        };

        initializeDb(dbCallBack);

    }

    private void initializeDb(CallBackInterface callBackInterface){
        Log.d(TAG, "initializeDb()");
        ServiceController serviceController = new ServiceController(this, callBackInterface);
        serviceController.initializeDbController();
    }
}
