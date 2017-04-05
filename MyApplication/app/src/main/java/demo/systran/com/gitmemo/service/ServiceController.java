package demo.systran.com.gitmemo.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import demo.systran.com.gitmemo.R;
import demo.systran.com.gitmemo.callback.CallBackInterface;
import demo.systran.com.gitmemo.service.db.DBOpenHelper;
import demo.systran.com.gitmemo.utility.MyLog;

/**
 * Created by dh on 2017-04-04.
 */

public class ServiceController {
    private String TAG = "ServiceController";

    private Context mContext = null;
    private CallBackInterface mCallBackInterface = null;

    private DBOpenHelper openHelper = null;
    private static SQLiteDatabase mDatabase = null;
    private static SQLiteDatabase mDatabase_ez = null;

    public ServiceController(Context context){
        this.mContext = context;

    }

    public void initializeDb(CallBackInterface callBackInterface){
        Log.d(TAG, "initializeDb()");
        this.mCallBackInterface = callBackInterface;

        openHelper = new DBOpenHelper(mContext, "product.db", null, 1); // DB생성
        mDatabase = openHelper.getWritableDatabase(); // DB open
        if(mDatabase!=null){ // DB가 존재함
//            openHelper = new DBOpenHelper(mContext, "product.db", null, 2); // DB upGrade
            openHelper.onUpgrade(mDatabase, 1, 2);
            mCallBackInterface.callback();

        } else { // DB가 없음

            Toast.makeText(mContext, "DB가 존재합니다.", Toast.LENGTH_SHORT).show();
            mCallBackInterface.callback();
        }
    }

    public void insertData(String prdTitle, String prdDescription){
        Log.d(TAG, "insertData()");
//        mDatabase = openHelper.getWritableDatabase();
        String sql = "INSERT INTO bestproduct(product_title, product_description, product_reg_date) VALUES"
                + "('" + prdTitle
                + "', '" + prdDescription
                + "', '2017-04-05');";
        mDatabase.execSQL(sql);
    }

    public String updateData(){ // eztalky.db 생성 메소드
        String updateResult = null;
        String dbFilePath = mContext.getFilesDir().getAbsolutePath()+"/eztalky.db";
        File dbFile = new File(dbFilePath);

        if(!dbFile.isFile()){ // 기존 DB 파일 존재 안 함
            try {
                Log.d(TAG, "1");
                InputStream inputStream = mContext.getResources().openRawResource(R.raw.eztalky);//mContext.getAssets().open("eztalky.db"); // Inputstream에 입력할 파일로 res/raw/eztalky.sqlite 넣어줌
                Log.d(TAG, "2");
                FileOutputStream fileOutputStream = new FileOutputStream(dbFilePath); // 파일을 생성할 경로를 FileOutputStream에 넣어줌
                Log.d(TAG, "3");
                byte[] buffer = new byte[4096];
                while (true) {
                    int readSize = inputStream.read(buffer, 0, buffer.length); // InputStream에 데이터를 읽어서 총 길이 측정후 return/ 그리고 buffer 사이즈에 맞춰서 쪼개 저장
                    if (readSize == -1)
                        break;
                    fileOutputStream.write(buffer, 0, readSize); // 4kb 사이즈 데이터를 fos로 삽입(DB 생성)
                }
                fileOutputStream.close();
                inputStream.close();
                updateResult = "Success eztalky.db file";
            } catch (FileNotFoundException e) {
                MyLog.d("FileNotFoundException : " + e.getMessage());
                updateResult = "FileNotFoundException";
            } catch (IOException e) {
                MyLog.e("IOException : " + e.getMessage());
                updateResult = "IOException";
            } finally {

            }

            openHelper = new DBOpenHelper(mContext, "eztalky.db", null, 1); // DB 객체 불러옴 생성
            mDatabase_ez = openHelper.getReadableDatabase();

        }else{ // DB 존재함
            updateResult = "Already exist eztalky.db file";
            dbFile.delete();//기존 데이터베이스 파일 제거
        }

        return updateResult;
    }

    public Cursor selectDataAll(){
        String sql = "SELECT * FROM domain";
        Cursor c = mDatabase_ez.rawQuery(sql, null);

        return c;
    }

    public int selectDataCount(){
        Log.d(TAG, "selectDataCount()");
        int count = 0;

        String sql = "SELECT count(*) FROM bestproduct;";

//        mDatabase = openHelper.getReadableDatabase();
        Cursor c = null;
        c = mDatabase.rawQuery(sql, null);
        count = c.getCount();
        c.close();
        return count;
    }
}
