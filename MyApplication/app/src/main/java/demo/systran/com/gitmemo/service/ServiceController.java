package demo.systran.com.gitmemo.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;

import demo.systran.com.gitmemo.service.db.DBOpenHelper;

/**
 * Created by dh on 2017-04-04.
 */

public class ServiceController {
    private String TAG = "ServiceController";

    private Context mContext = null;

    private DBOpenHelper openHelper = null;
    private final String dbName = "student.db";
    private final String tableName = "classA";
    private String COL_1 = "ename";
    private String COL_2 = "eclass";
    private SQLiteDatabase mDatabase = null;
    private SQLiteDatabase mDatabaseNew = null;

    public ServiceController(Context context){
        this.mContext = context;

        String db_path = mContext.getFilesDir().getAbsolutePath() + "/" + dbName; // System Area DB path
        Log.d(TAG, "db_path : " + db_path);
        File file = new File(db_path);
        if(file.isFile()){
            if(mDatabase==null || !mDatabase.isOpen()) {
                mDatabase = SQLiteDatabase.openDatabase(db_path, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            } else {
                initializeDb();
            }
        } else {

        }


    }

    public void initializeDb(){ // 최초 한번만 실행
        Log.d(TAG, "initializeDb()");

        openHelper = new DBOpenHelper(mContext, dbName, null, 1); // DB생성
        mDatabase = openHelper.getWritableDatabase(); // DB open
//        if(mDatabase!=null){ // DB가 존재함
////            openHelper = new DBOpenHelper(mContext, "product.db", null, 2); // DB upGrade
//            openHelper.onUpgrade(mDatabase, 1, 2);
//            mCallBackInterface.callback();
//
//        } else { // DB가 없음
//
//            Toast.makeText(mContext, "DB가 존재합니다.", Toast.LENGTH_SHORT).show();
//            mCallBackInterface.callback();
//        }
    }

//    ====================================

    public void createDatabase(){
        openHelper = new DBOpenHelper(mContext, "student.db", null, 1); // DB생성
        mDatabase = openHelper.getWritableDatabase(); // DB open
    }

    public void createDatabaseNew(){
        openHelper = new DBOpenHelper(mContext, "student_tmp.db", null, 1); // DB생성
        mDatabaseNew = openHelper.getWritableDatabase(); // DB open
    }

    public Cursor moveDatabase(){
        openHelper = new DBOpenHelper(mContext, "student.db", null, 1); // DB생성
        mDatabase = openHelper.getWritableDatabase(); // DB open
        openHelper = new DBOpenHelper(mContext, "student_tmp.db", null, 1); // DB생성
        mDatabaseNew = openHelper.getWritableDatabase(); // DB open


        mDatabase.execSQL("DELETE FROM " + tableName); // student.db에서 classA 테이블 데이터 전체 삭제

        return mDatabaseNew.rawQuery("SELECT * FROM " + tableName,  null);
    }

    public long insertData(String prdTitle, String prdDescription){
        mDatabase = openHelper.getWritableDatabase();
        long insertResult = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, prdTitle);
        contentValues.put(COL_2, prdDescription);

        insertResult = mDatabase.insert(tableName, null, contentValues);
        return insertResult;
    }

    public Cursor selectDataAll(){
        String sql = "SELECT * FROM " + tableName;
        return mDatabase.rawQuery(sql, null);
    }

    public int selectDataCount(){
        String sql = "SELECT count(*) FROM " + tableName;
        mDatabase = openHelper.getWritableDatabase();

        Cursor c = mDatabase.rawQuery(sql, null);
        c.moveToFirst();
        return c.getInt(0);
    }



}
