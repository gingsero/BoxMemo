package demo.systran.com.gitmemo.view.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import demo.systran.com.gitmemo.R;
import demo.systran.com.gitmemo.utility.MyLog;

public class PermissionActivity extends AppCompatActivity {
    private String TAG = "PermissionActivity";
    private static final int REQUEST_PERMISSION = 1;
    private final int REQUEST_CODE_SETTING_ACTIVITY = 0;

    public String[] PERMISSION_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,    //SD 읽기 권한
            Manifest.permission.WRITE_EXTERNAL_STORAGE,    //SD 쓰기 권한
            Manifest.permission.CAMERA
    };


    AlertDialog.Builder permissionDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            boolean result = verifyPermissions(this);
            if (!result) {
                return;
            } else {
                return;
            }
        } else {
            //롤리팝 이상이 아니면 액티비티 종료 : IntroActivity를 재시작
            Intent intent = new Intent(this, IntroActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SETTING_ACTIVITY) {
            //boolean result = verifyStoragePermissions(this);
            if (checkPermissions(PermissionActivity.this)) {
                Intent intent = new Intent(PermissionActivity.this, IntroActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(0, 0);
                startActivity(intent);
                finish();
                return;
            } else {
                Intent intent = new Intent(PermissionActivity.this, PermissionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(0, 0);
                startActivity(intent);
                finish();
            }
        }
    }


    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public boolean verifyPermissions(Activity activity) {
        for (int cnt = 0; cnt < PERMISSION_LIST.length; cnt++) {
            MyLog.d(TAG, "cnt : " + cnt);
            int permission = ActivityCompat.checkSelfPermission(activity, PERMISSION_LIST[cnt]);

            if (permission != PackageManager.PERMISSION_GRANTED) { // PackageManager 값은 0이고 permision이 0이 아니면 권한 요청함
                MyLog.d(TAG, "permission : " + permission);
                // We don't have permission so prompt the user
                //ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
                ActivityCompat.requestPermissions(activity, PERMISSION_LIST, REQUEST_PERMISSION);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MyLog.d(TAG, "requestCode : " + requestCode);
        MyLog.d(TAG, "REQUEST_PERMISSION : " + REQUEST_PERMISSION);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                boolean result = true;
                MyLog.d(TAG, "grantResults.length : " + grantResults.length);
                for(int i = 0; i < grantResults.length; i++){
                    MyLog.d(TAG, "i : " + i + " / " + "grantResults[i] : " + grantResults[i]);
                    if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                        result=false;
                    }
                }

                MyLog.d(TAG, "boolean result : " + result);
                if (result) {
                    MyLog.d(TAG, "퍼미션이 모두 허가된 상태, 액티비티 종료 코드 호출");

                    Intent intent = new Intent(PermissionActivity.this, IntroActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    overridePendingTransition(0,0);
                    startActivity(intent);
                } else {
                    //MyMyLog.d(TAG, "Permission always deny");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    //MyMyLog.d(TAG, "스낵바 호출 코드");

                    RelativeLayout bs_rootView = (RelativeLayout) findViewById(R.id.permission_rootview);
                    Snackbar sys_snackbar = Snackbar.make(bs_rootView, "어플리케이션의 원활한 사용을 위하여 권한을 허용하셔야합니다.", Snackbar.LENGTH_LONG);

                    sys_snackbar.setAction("권한 허용하기", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyLog.d("스낵바의 권한 허용하기 눌렀음");

                            if (ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                MyLog.d("다시 물어보지 않기를 체크하지 않은 경우 퍼미션 확인 팝업 다시 띄우기");

                                verifyPermissions(PermissionActivity.this);
                            } else {
                                MyLog.d("다시 물어보지 않기를 체크한 경우 어플리케이션 관리자로 이동하는지 물어본 후 이동");
                                permissionDialog = new AlertDialog.Builder(PermissionActivity.this);

                                //permissionDialog.setTitle("권한 설정");
                                permissionDialog.setMessage("어플리케이션 관리 화면의 권한 메뉴를 선택하시면 권한을 재설정하실 수 있습니다. 권한 설정을 위하여 어플리케이션의 관리 화면으로 이동하시겠습니까?");
                                permissionDialog.setNegativeButton("취소", cancelPermissionListener);
                                permissionDialog.setPositiveButton("이동", confirmPermissionListener);
                                permissionDialog.show();
                                //permissionDialog = new BS_Common_Dialog(PermissionActivity.this, "권한 설정을 위하여 어플리케이션 관리 화면으로 이동하시겠습니까?", "취소", "이동", bs_CancelPermissionListener, bs_OKPermissionListener);
                                //permissionDialog.show();
                            }
                        }
                    });

                    sys_snackbar.setCallback(new Snackbar.Callback() {

                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                // Snackbar closed on its own
                                MyLog.d("스낵바 사라져서 로딩 화면으로 복귀");

                                Intent intent = new Intent(PermissionActivity.this, PermissionActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                overridePendingTransition(0,0);
                                startActivity(intent);
                            }
                            super.onDismissed(snackbar, event);
                        }

                        @Override
                        public void onShown(Snackbar snackbar) {
                            if(checkPermissions(PermissionActivity.this)){
                                MyLog.d("권한 승인되었으므로 로딩 화면으로 복귀");

                                Intent intent = new Intent(PermissionActivity.this, IntroActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                overridePendingTransition(0,0);
                                startActivity(intent);
                                finish();
                                return;
                            }

                            super.onShown(snackbar);
                        }
                    });

                    View bs_SnackBarView = sys_snackbar.getView(); //get your snackbar view
                    TextView textView = (TextView) bs_SnackBarView.findViewById(android.support.design.R.id.snackbar_text); //Get reference of snackbar textview
                    textView.setMaxLines(3); // Change your max lines
                    sys_snackbar.show();
                    MyLog.d("스낵바 실행");

                }
                break;
        }
    }

    public boolean checkPermissions(Activity activity) {
        for (int cnt = 0; cnt < PERMISSION_LIST.length; cnt++) {
            int permission = ActivityCompat.checkSelfPermission(activity, PERMISSION_LIST[cnt]);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                //ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
                return false; // permission 갯수 하나라도 부족하면 false 리턴.
            }
        }
        return true;
    }

    public DialogInterface.OnClickListener confirmPermissionListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface DialogInterface, int i) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
            //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            PermissionActivity.this.startActivityForResult(intent, REQUEST_CODE_SETTING_ACTIVITY);
        }
    };
    public DialogInterface.OnClickListener cancelPermissionListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface DialogInterface, int i) {
            //permissionDialog.dismiss();
            Toast.makeText(PermissionActivity.this, "사용 권한을 획득하지 못하여 어플리케이션을 종료합니다.", Toast.LENGTH_LONG).show();
            finish();
        }
    };


}
