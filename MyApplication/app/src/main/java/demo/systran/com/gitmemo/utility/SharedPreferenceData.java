package demo.systran.com.gitmemo.utility;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by dh on 2017-04-03.
 */

public class SharedPreferenceData {


    private Context mContext = null;
    private Constants mConstants = null;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor editor;

    public SharedPreferenceData(Context context){
        this.mContext = context;
        initSharedPreferenceData();
    }

    private void initSharedPreferenceData(){
        mConstants = new Constants();
        mSharedPreferences = mContext.getSharedPreferences(mConstants.SHARED_PREFERENCE_DATA_TITLE, MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }



    //값 저장
    public void putDataToSharedPreferences(String keyString, String value) {
        MyLog.d("m_systran_sharedPreferences save data = "+value);
        editor.putString(keyString, value);
        editor.commit();
    }
    public void putDataToSharedPreferences(String keyString, int value) {
        MyLog.d("m_systran_sharedPreferences save data = "+value);
        editor.putInt(keyString, value);
        editor.commit();
    }
    public void putDataToSharedPreferences(String keyString, Long value) {
        MyLog.d("m_systran_sharedPreferences save data = "+value);
        editor.putLong(keyString, value);
        editor.commit();
    }
    public void putDataToSharedPreferences(String keyString, boolean value) {
        MyLog.d("m_systran_sharedPreferences save data = "+value);
        editor.putBoolean(keyString, value);
        editor.commit();
    }

    //값 호출
    public String getDataFromSharedPreferences(String keyString, String defaultValue) {
        String data = mSharedPreferences.getString(keyString, defaultValue);
        MyLog.d("m_systran_sharedPreferences data = "+data);
        return data;
    }
    public int getDataFromSharedPreferences(String keyString, int defaultValue) {
        int data = mSharedPreferences.getInt(keyString, defaultValue);
        MyLog.d("m_systran_sharedPreferences data = "+data);
        return data;
    }
    public Long getDataFromSharedPreferences(String keyString, Long defaultValue) {
        Long data = mSharedPreferences.getLong(keyString, defaultValue);
        MyLog.d("m_systran_sharedPreferences data = "+data);
        return data;
    }
    public boolean getDataFromSharedPreferences(String keyString, boolean defaultValue) {
        boolean data = mSharedPreferences.getBoolean(keyString, defaultValue);
        MyLog.d("m_systran_sharedPreferences data = "+data);
        return data;
    }

    //값 삭제
    public void clearDataInSharedPreferences(String keyString) {
        MyLog.d("Delete Value of "+keyString);
        editor.remove(keyString);
        editor.commit();
    }

}
