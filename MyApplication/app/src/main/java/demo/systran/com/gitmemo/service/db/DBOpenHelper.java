package demo.systran.com.gitmemo.service.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dh on 2017-04-04.
 */
//DB 생성 및 Upgrade만 관리
public class DBOpenHelper extends SQLiteOpenHelper {
    private String TAG = "DBOpenHelper";
    private final String tableName = "classA";
    private Context mContext = null;

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
        this.mContext = context;
    }
    // 생성된 DB가 없을 경우에 한번만 호출됨
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate()");
        db.execSQL("CREATE TABLE " + tableName + "(ename text not null, eclass text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade()");

        if(oldVersion==newVersion){
            Log.d(TAG, "oldVersion==newVersion : true");
        } else {
            Log.d(TAG, "oldVersion==newVersion : false");
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
            onCreate(db);
        }


    }


}

