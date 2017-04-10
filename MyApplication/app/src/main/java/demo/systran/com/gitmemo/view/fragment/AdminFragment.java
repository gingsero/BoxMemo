package demo.systran.com.gitmemo.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import demo.systran.com.gitmemo.R;
import demo.systran.com.gitmemo.service.ServiceController;
import demo.systran.com.gitmemo.service.db.DBOpenHelper;
import demo.systran.com.gitmemo.utility.MyLog;
import demo.systran.com.gitmemo.utility.SharedPreferenceData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AdminFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment extends Fragment {
    private String TAG = "AdminFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Context mContext = null;
    private SharedPreferenceData mSharedPreference;

    private Button new_db_button = null;
    private Button drop_db_button = null;
    private Button move_db_button = null;
//    private Button cnt_button = null;

    private Button insert_button = null;
    private Button update_button = null;
    private Button select_button = null;
    private Button cnt_button = null;

    private Button load_button = null;
    private Button save_button = null;
    private Button cancel_button = null;

    private EditText title_editbox = null;
    private EditText desc_editbox = null;
    private TextView load_image_name = null;
    private TextView db_result_editbox = null;

    ServiceController serviceController = null;

    DBOpenHelper openHelper = null;
    SQLiteDatabase mDatabase = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminFragment newInstance(String param1, String param2) {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin, container, false);

        new_db_button = (Button) v.findViewById(R.id.fragment_admin_db_new);
        drop_db_button = (Button) v.findViewById(R.id.fragment_admin_db_drop);
        move_db_button = (Button) v.findViewById(R.id.fragment_admin_db_trans);

        insert_button = (Button) v.findViewById(R.id.fragment_admin_db_insert_button);
        update_button = (Button) v.findViewById(R.id.fragment_admin_db_update_button);
        select_button = (Button) v.findViewById(R.id.fragment_admin_db_select_button);
        cnt_button = (Button) v.findViewById(R.id.fragment_admin_db_count_button);

        load_button = (Button) v.findViewById(R.id.fragment_admin_load_button);
        save_button = (Button) v.findViewById(R.id.fragment_admin_save_button);
        cancel_button = (Button) v.findViewById(R.id.fragment_admin_cancel_button);

        title_editbox = (EditText) v.findViewById(R.id.fragment_admin_title);
        desc_editbox = (EditText) v.findViewById(R.id.fragment_admin_description);
        load_image_name = (TextView) v.findViewById(R.id.fragment_admin_image_name);
        db_result_editbox = (TextView) v.findViewById(R.id.fragment_admin_db_result);

        serviceController = new ServiceController(mContext);


        new_db_button.setOnClickListener(mClickListener);
        drop_db_button.setOnClickListener(mClickListener);
        move_db_button.setOnClickListener(mClickListener);
        load_button.setOnClickListener(mClickListener);
        save_button.setOnClickListener(mClickListener);
        cancel_button.setOnClickListener(mClickListener);
        insert_button.setOnClickListener(mClickListener);
        update_button.setOnClickListener(mClickListener);
        select_button.setOnClickListener(mClickListener);
        cnt_button.setOnClickListener(mClickListener);


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mSharedPreference = new SharedPreferenceData(getContext());
        String admin_title = mSharedPreference.getDataFromSharedPreferences("ADMIN_FRAGMENT_TITLE", "");
        String admin_description = mSharedPreference.getDataFromSharedPreferences("ADMIN_FRAGMENT_DESCRIPTION", "");

        title_editbox.setText(admin_title);
        desc_editbox.setText(admin_description);

    }


    @Override
    public void onPause() {
        super.onPause();
        mSharedPreference.putDataToSharedPreferences("ADMIN_FRAGMENT_TITLE", title_editbox.getText().toString());
        mSharedPreference.putDataToSharedPreferences("ADMIN_FRAGMENT_DESCRIPTION", desc_editbox.getText().toString());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.fragment_admin_load_button:
                    DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doTakePhotoAction();
                        }
                    };
                    DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doTakeAlbumAction();
                        }
                    };
                    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    };

                    new AlertDialog.Builder(getContext())
                            .setTitle("업로드 이미지 선택")
                            .setPositiveButton("사진촬영", cameraListener)
                            .setNeutralButton("앨범선택", albumListener)
                            .setNegativeButton("취소", cancelListener)
                            .show();
                    break;
                case R.id.fragment_admin_save_button:
                    break;
                case R.id.fragment_admin_cancel_button:
                    title_editbox.setText("");
                    desc_editbox.setText("");
                    load_image_name.setText("");
                    Toast.makeText(getContext(), "작성된 내용을 모두 삭제하였습니다.", Toast.LENGTH_SHORT).show();
                    break;

//              =========================================
                case R.id.fragment_admin_db_new : //
                    serviceController.createDatabase();
                    serviceController.createDatabaseNew();
//                    Toast.makeText(getContext(), "student_tmp.db & elementarystudent table이 생성되었습니다.", Toast.LENGTH_SHORT).show();
//                    mContext.deleteDatabase("student.db");
//                    mContext.deleteDatabase("student_tmp.db");
                    break;
                case R.id.fragment_admin_db_drop : //
                    mContext.deleteDatabase("student.db");
                    mContext.deleteDatabase("student_tmp.db");
                    break;
                case R.id.fragment_admin_db_trans :
                    Cursor newDbCursor = serviceController.moveDatabase(); // data copy from select
                    while (newDbCursor.moveToNext()){ //
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put();
                        Log.d(TAG, "move DB : " + newDbCursor.getString(0) + ", " + newDbCursor.getString(1));
                        long insertResult = serviceController.insertData(newDbCursor.getString(0), newDbCursor.getString(1));
                        Log.d(TAG, "insertResult : " + insertResult);
                    }
//                    mContext.deleteDatabase("stuent_tmp.db");

                    break;

                case R.id.fragment_admin_db_insert_button:
//                    serviceController.createDatabase();
//                    serviceController.createDatabaseNew();
//                    Toast.makeText(getContext(), "student.db & student_tmp.db이 생성되었습니다.", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.fragment_admin_db_update_button:
                    String prdTitle = title_editbox.getText().toString();
                    String prdDescription = desc_editbox.getText().toString();
                    long insertResult = serviceController.insertData(prdTitle, prdDescription);
                    if(insertResult==-1){
                        Toast.makeText(mContext, "Fail", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "insertData Success", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.fragment_admin_db_select_button:
                    Cursor c = serviceController.selectDataAll();
                    StringBuffer sBuffer = new StringBuffer();
                    Log.d(TAG, "columnCnt : " + c.getColumnCount());

                    while (c.moveToNext()){
                        sBuffer.append(c.getString(0) + "\t" + c.getString(1) + "\n");
                        // james 3412
                        // karl 3403
                    }

                    db_result_editbox.setText(sBuffer.toString());
                    break;
                case R.id.fragment_admin_db_count_button:
                    int count = serviceController.selectDataCount();
                    Toast.makeText(mContext, "select cnt : " + count, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void doTakePhotoAction(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String imageUrl = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".png";
        Uri mImageFile = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), imageUrl));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageFile);
        getActivity().startActivityForResult(intent, 0);
    }

    public void doTakeAlbumAction(){
        Log.d(TAG, "doTakeAlbumAction()");
        Intent intent = new Intent(Intent.ACTION_PICK);
        Log.d(TAG, "1");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        Log.d(TAG, "2");
        startActivityForResult(intent, 1);


//        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyLog.d(TAG, "onActivityResult()");
        String path = data.getData().getPath();
        MyLog.d(TAG, "path : " + path);
        switch (requestCode){
            case 0 :
                break;
            case 1 :
                MyLog.d(TAG, "data.getData().getPath() : " + path);

                load_image_name.setText(path);
                break;
        }
    }
}
