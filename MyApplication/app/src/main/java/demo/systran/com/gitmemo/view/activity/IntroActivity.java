package demo.systran.com.gitmemo.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;

import demo.systran.com.gitmemo.R;
import demo.systran.com.gitmemo.callback.CallBackInterface;
import demo.systran.com.gitmemo.utility.MyLog;

public class IntroActivity extends Activity {
    private String TAG = "IntroActivity";
    private CallBackInterface dbCallBack = null;

    public String[] PERMISSION_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,	//SD 읽기 권한
            Manifest.permission.WRITE_EXTERNAL_STORAGE,	//SD 쓰기 권한
            Manifest.permission.CAMERA
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        MyLog.d(TAG, "onCreate()");
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) { // Over 5.1 version (Lollipop - SDK:22)
            //권한 획득 여부 확인
            if(!checkPermissions(this)) {
                //권한 획득이 되지 않은 경우 권한 획득을 위한 액티비티로 이동
                Intent intent = new Intent(this, PermissionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {// else 문은 Permission 모두 승인되었으니 pass하는 동작
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(IntroActivity.this, MainFragmentActivity.class);
                        startActivity(intent);
                        finish();
                    }
                };
                Handler handler = new Handler();
                handler.postDelayed(runnable, 2000);
            }
        } else {// else 문은 AOS 5.1 이하는 설치시 권한 획득
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(IntroActivity.this, MainFragmentActivity.class);
                    startActivity(intent);
                    finish();
                }
            };

            Handler handler = new Handler();
            handler.postDelayed(runnable, 2000);
        }
    }


    public boolean checkPermissions(Activity activity) {
        for (int cnt = 0; cnt < PERMISSION_LIST.length; cnt++){
            int permission = ActivityCompat.checkSelfPermission(activity, PERMISSION_LIST[cnt]);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                //ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
                return false; // permission 갯수 하나라도 부족하면 false 리턴.
            }
        }
        return true;
    }
}
