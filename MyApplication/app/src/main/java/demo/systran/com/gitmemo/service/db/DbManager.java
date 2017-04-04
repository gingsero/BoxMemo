package demo.systran.com.gitmemo.service.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import demo.systran.com.gitmemo.R;

/**
 * Created by dh on 2017-04-04.
 */

public class DbManager {

    private static final String DATABASE_NAME = "product.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private Context mCtx;

//    Singleton
    private static DbManager mDbManager = null;

    public DbManager(Context context){
        this.mCtx = context;
        mDB = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        mDB.execSQL(mCtx.getResources().getString(R.string.CREATE_TABLE));
    }

    public static DbManager getInstance(Context context){
        if(mDbManager == null){
            mDbManager = new DbManager(context);
        }
        return mDbManager;
    }

}


