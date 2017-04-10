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

    private final String dbName = "student.db";
    private final String dbName_new = "student_tmp.db";
    private final String tableName = "classA";
    private String COL_1 = "ename";
    private String COL_2 = "eclass";

    private Context mContext = null;

    private DBOpenHelper openHelper = null;
    private DBOpenHelper openHelper2 = null;
    private SQLiteDatabase mDatabase = null;
    private SQLiteDatabase mDatabaseNew = null;

    public ServiceController(Context context){
        this.mContext = context;

        String db_path = mContext.getFilesDir().getAbsolutePath() + "/" + dbName; // System Area DB path
        String db_path_new = mContext.getFilesDir().getAbsolutePath() + "/" + dbName_new; // System Area DB path
        Log.d(TAG, "db_path : " + db_path);
        Log.d(TAG, "db_path_new : " + db_path_new);
        File file = new File(db_path);

        if(file.isFile()){ // DB 파일이 있으면
            if(mDatabase==null || !mDatabase.isOpen()) { // DB가 null인지, 열리지 않았는지 확인 후 열기
                mDatabase = SQLiteDatabase.openDatabase(db_path, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS); // openDatabase()는 해당 경로에 파일이 있을때만 사용가능
            } else { // null이 아니거나 열려 있는 상태면 DB 초기화
                initializeDb();
            }
        } else { // DB 파일이 없으면 새로 생성
            initializeDb();
        }

        file = new File(db_path_new);
        if(file.isFile()){ // DB 파일이 있으면
            if(mDatabase==null || !mDatabase.isOpen()) { // DB가 null인지, 열리지 않았는지 확인 후
                mDatabaseNew = SQLiteDatabase.openDatabase(db_path_new, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            } else {
                initializeDb();
            }
        } else {
            initializeDb();
        }
    }

    public void initializeDb(){ // 최초 한번만 실행
        openHelper = new DBOpenHelper(mContext, dbName, null, 1); // DB생성
        mDatabase = openHelper.getWritableDatabase(); // DB open

        openHelper2 = new DBOpenHelper(mContext, dbName_new, null, 1); // DB생성
        mDatabaseNew = openHelper2.getWritableDatabase(); // DB open
    }



    public void deleteDatabase(){
        mContext.deleteDatabase("student.db");
        mContext.deleteDatabase("student_tmp.db");
    }

    public void deleteTableData(String databaseName){
//        openHelper = new DBOpenHelper(mContext, databaseName, null, 1); // DB생성

        if(databaseName.equals("student.db")){
//            mDatabase = openHelper.getWritableDatabase(); // DB open
            mDatabase.execSQL("DELETE FROM " + tableName);
        } else {
//            mDatabaseNew = openHelper.getWritableDatabase(); // DB open
            mDatabaseNew.execSQL("DELETE FROM " + tableName);
        }


    }

    public void createDatabase(String databaseName){
//        openHelper = new DBOpenHelper(mContext, databaseName, null, 1); // DB생성
//        mDatabase = openHelper.getWritableDatabase(); // DB open
    }

    public Cursor moveDatabase(){
//        openHelper = new DBOpenHelper(mContext, "student.db", null, 1); // DB생성
//        mDatabase = openHelper.getWritableDatabase(); // DB open
//        openHelper = new DBOpenHelper(mContext, "student_tmp.db", null, 1); // DB생성
//        mDatabaseNew = openHelper.getWritableDatabase(); // DB open


        mDatabase.execSQL("DELETE FROM " + tableName); // student.db에서 classA 테이블 데이터 전체 삭제

        return mDatabaseNew.rawQuery("SELECT * FROM " + tableName,  null);
    }

    public long insertData(String databaseName, String prdTitle, String prdDescription){
//        openHelper = new DBOpenHelper(mContext, dbName, null, 1); // DB생성
//        mDatabase = openHelper.getWritableDatabase();
//        openHelper = new DBOpenHelper(mContext, dbName_new, null, 1); // DB생성
//        mDatabaseNew = openHelper.getWritableDatabase();

        long insertResult = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, prdTitle);
        contentValues.put(COL_2, prdDescription);

        Log.d(TAG, "insertData, databaseName : " + databaseName);
        if(databaseName.equals("student.db")){
            insertResult = mDatabase.insert(tableName, null, contentValues);
        } else { // student_tmp.db
            insertResult = mDatabaseNew.insert(tableName, null, contentValues);
        }
        return insertResult;
    }

    public Cursor selectDataAll(String databaseName){
        String sql = "SELECT * FROM " + tableName;
        Cursor selectCursor = null;
        if(databaseName.equals("student.db")){
            Log.d(TAG, "student.db");
            selectCursor = mDatabase.rawQuery(sql, null);
        } else { // student_tmp.db
            Log.d(TAG, "student_tmp.db");
            selectCursor = mDatabaseNew.rawQuery(sql, null);
        }
        return selectCursor;
    }

    public int selectDataCount(String databaseName){
        String sql = "SELECT count(*) FROM " + tableName;
        Cursor countCursor = null;
        if(databaseName.equals("student.db")){
            //mDatabase = openHelper.getWritableDatabase();
            countCursor = mDatabase.rawQuery(sql, null);
        } else {
            //mDatabaseNew = openHelper.getWritableDatabase();
            countCursor = mDatabaseNew.rawQuery(sql, null);
        }
        countCursor.moveToFirst();
        return countCursor.getInt(0);
    }

}
