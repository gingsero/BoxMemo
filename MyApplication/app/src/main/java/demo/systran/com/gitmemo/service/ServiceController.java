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
    private final String tableName = "elementarystudent";
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
    public void createDatabaseNew(){
        openHelper = new DBOpenHelper(mContext, "student_tmp.db", null, 1); // DB생성
        mDatabaseNew = openHelper.getWritableDatabase(); // DB open
    }
    public void createDatabase(){
        openHelper = new DBOpenHelper(mContext, "student.db", null, 1); // DB생성
        mDatabase = openHelper.getWritableDatabase(); // DB open
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

    /*public String updateData(){ // eztalky.db 생성 메소드
        String updateResult = null;
        String dbFilePath = mContext.getFilesDir().getAbsolutePath()+"/eztalky.db";// mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + "/eztalky.db";
//        Log.d("KKKK", "dbFilePath : " + dbFilePath);
        File dbFile = new File(dbFilePath);

        if(!dbFile.isFile()){ // 기존 DB 파일 존재 안 함
            try {
                InputStream inputStream = mContext.getResources().openRawResource(R.raw.eztalky);//mContext.getAssets().open("eztalky.db"); // Inputstream에 입력할 파일로 res/raw/eztalky.sqlite 넣어줌
                FileOutputStream fileOutputStream = new FileOutputStream(dbFilePath); // 파일을 생성할 경로를 FileOutputStream에 넣어줌

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


        }else{ // DB 존재함
            updateResult = "Already exist eztalky.db file";
//            dbFile.delete();//기존 데이터베이스 파일 제거
        }

        return updateResult;
    }*/


}
