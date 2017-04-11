package demo.systran.com.gitmemo.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

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

//        String db_path = mContext.getFilesDir().getAbsolutePath() + "/" + dbName; // System Area DB path
//        String db_path_new = mContext.getFilesDir().getAbsolutePath() + "/" + dbName_new; // System Area DB path

        String db_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + dbName; // System Area DB path
        String db_path_new = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + dbName_new; // System Area DB path

        String state= Environment.getExternalStorageState(); //외부저장소(SDcard)의 상태 얻어오기
        if(!state.equals(Environment.MEDIA_MOUNTED)){ // SDcard 의 상태가 쓰기 가능한 상태로 마운트되었는지 확인
            Toast.makeText(mContext, "SDcard Not Mounted", Toast.LENGTH_SHORT).show();
            return;
        }

//        String db_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + dbName;
//        String db_path_new = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + dbName_new;
        Log.d(TAG, "db_path : " + db_path);
        Log.d(TAG, "db_path_new : " + db_path_new);

        File file = new File(db_path);

        if(file.isFile()){ // DB 파일이 있으면
            if(mDatabase==null || !mDatabase.isOpen()) { // DB가 null인지, 열리지 않았는지 확인 후 열기
                mDatabase = SQLiteDatabase.openDatabase(db_path, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS); // openDatabase()는 해당 경로에 파일이 있을때만 사용가능
            } else { // null이 아니거나 열려 있는 상태면 DB 초기화
                initializeDb(db_path, db_path_new);
            }
        } else { // DB 파일이 없으면 새로 생성
            initializeDb(db_path, db_path_new);
        }

        file = new File(db_path_new);
        if(file.isFile()){ // DB 파일이 있으면
            if(mDatabase==null || !mDatabase.isOpen()) { // DB가 null인지, 열리지 않았는지 확인 후
                mDatabaseNew = SQLiteDatabase.openDatabase(db_path_new, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            } else {
                initializeDb(db_path, db_path_new);
            }
        } else {
            initializeDb(db_path, db_path_new);
        }
    }

    public void initializeDb(String firstDb, String secondDb){ // 최초 한번만 실행
        openHelper = new DBOpenHelper(mContext, firstDb, null, 1); // DB생성
        mDatabase = openHelper.getWritableDatabase(); // DB open

        openHelper2 = new DBOpenHelper(mContext, secondDb, null, 1); // DB생성
        mDatabaseNew = openHelper2.getWritableDatabase(); // DB open
    }

    public void deleteDatabase(){
        mContext.deleteDatabase("student.db");
        mContext.deleteDatabase("student_tmp.db");
    }

    public void deleteTableData(String databaseName){
        if(databaseName.equals("student.db")){
            mDatabase.execSQL("DELETE FROM " + tableName);
        } else {
            mDatabaseNew.execSQL("DELETE FROM " + tableName);
        }
    }

    public void createDatabase(String databaseName){
//        openHelper = new DBOpenHelper(mContext, databaseName, null, 1); // DB생성
//        mDatabase = openHelper.getWritableDatabase(); // DB open
    }

    public Cursor moveDatabase(){
        mDatabase.execSQL("DELETE FROM " + tableName); // student.db에서 classA 테이블 데이터 전체 삭제
        return mDatabaseNew.rawQuery("SELECT * FROM " + tableName,  null);
    }

    public long insertData(String databaseName, String prdTitle, String prdDescription){
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
            countCursor = mDatabase.rawQuery(sql, null);
        } else {
            countCursor = mDatabaseNew.rawQuery(sql, null);
        }
        countCursor.moveToFirst();
        return countCursor.getInt(0);
    }

}
