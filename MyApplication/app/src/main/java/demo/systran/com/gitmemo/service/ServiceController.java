package demo.systran.com.gitmemo.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import demo.systran.com.gitmemo.callback.CallBackInterface;
import demo.systran.com.gitmemo.service.db.DBOpenHelper;

/**
 * Created by dh on 2017-04-04.
 */

public class ServiceController {
    private String TAG = "ServiceController";

    private Context mContext = null;
    private CallBackInterface mCallBackInterface = null;

    private DBOpenHelper openHelper = null;
    private static SQLiteDatabase mDatabase = null;

    public ServiceController(Context context){
        this.mContext = context;

    }

    public void initializeDb(CallBackInterface callBackInterface){
        Log.d(TAG, "initializeDb()");
        this.mCallBackInterface = callBackInterface;

        Log.d("KKKK", "1");
        openHelper = new DBOpenHelper(mContext, "product.db", null, 1); // DB생성
        Log.d("KKKK", "4");
        mDatabase = openHelper.getWritableDatabase(); // DB open
        Log.d("KKKK", "5");
        if(mDatabase!=null){ // DB가 존재함
            Log.d("KKKK", "6-1");
//            openHelper = new DBOpenHelper(mContext, "product.db", null, 2); // DB upGrade
            openHelper.onUpgrade(mDatabase, 1, 2);
            mCallBackInterface.callback();

        } else { // DB가 없음
            Log.d("KKKK", "6-2");

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

    public void updateData(){

    }

    public Cursor selectDataAll(){
        String sql = "SELECT * FROM bestproduct";
        Cursor c = mDatabase.rawQuery(sql, null);

        return c;
    }

    public int selectDataCount(){
        Log.d(TAG, "selectDataCount()");
        int count = 0;

        String sql = "SELECT count(*) FROM bestproduct;";

//        mDatabase = openHelper.getReadableDatabase();
        Cursor c = mDatabase.rawQuery(sql, null);
        count = c.getCount();
        c.close();
        return count;
    }
}
