package demo.systran.com.gitmemo.service;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import java.io.File;

import demo.systran.com.gitmemo.callback.CallBackInterface;
import demo.systran.com.gitmemo.service.db.DbManager;

/**
 * Created by dh on 2017-04-04.
 */

public class ServiceController {
    private String TAG = "ServiceController";

    private Context mContext = null;
    private CallBackInterface mCallBackInterface = null;
    private DbManager dbManager = null;

//    DB Controller
    public ServiceController(Context context, CallBackInterface callBackInterface){
        this.mContext = context;
        this.mCallBackInterface = callBackInterface;
    }

    public void initializeDbController(){
        Log.d(TAG, "initializeDbController()");
        String dbFilePath = mContext.getFilesDir().getAbsolutePath()+"/eztalky.db";
        File dbFile = new File(dbFilePath);

        if(!dbFile.isFile()){ // DB 없으면
            Log.d(TAG, "dbFile.isFile() : false");
//            dbManager = new DbManager(mContext);
            try{
                dbManager = DbManager.getInstance(mContext);
                mCallBackInterface.callback();
            } catch (SQLException e){
                Log.d("TAG", "initializeDbController() : " + e.getMessage());
            }
        } else {
//            TODO DB 버전 체크 후 지우고 생성 or 아무작업 안하는 로직
            Log.d(TAG, "dbFile.isFile() : true");
            mCallBackInterface.callback();
        }
    }


}
