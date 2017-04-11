package demo.systran.com.gitmemo.view.fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import demo.systran.com.gitmemo.R;
import demo.systran.com.gitmemo.service.ServiceController;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserDbFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserDbFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDbFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String TAG = "UserDbFragment";

    private Button deleteAllDb = null;
    private Button deleteTableData = null;
    private Button createDb = null;
    private Button somethingButton = null;
    private Button selectAlltable = null;
    private Button selectTableCnt = null;
    private Button insertData = null;
    private Button somethingButton2 = null;

    private ServiceController serviceController = null;

    private Spinner dbNameSpinner = null;
    private EditText dbColumnOne = null;
    private EditText dbColumnTwo = null;

    private EditText studentNameEdit = null;
    private EditText studentClassEdit = null;
    private TextView studentSelectResult = null;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserDbFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserDbFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDbFragment newInstance(String param1, String param2) {
        UserDbFragment fragment = new UserDbFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_user_db, container, false);

        serviceController = new ServiceController(getContext());

        deleteAllDb = (Button) v.findViewById(R.id.fragment_user_delete_all_db);
        deleteTableData = (Button) v.findViewById(R.id.fragment_user_delete_all_table_data);
        createDb = (Button) v.findViewById(R.id.fragment_user_create_db);
        somethingButton = (Button) v.findViewById(R.id.fragment_user_trans_db);
        selectAlltable = (Button) v.findViewById(R.id.fragment_user_select_all);
        selectTableCnt = (Button) v.findViewById(R.id.fragment_user_select_cnt);
        insertData = (Button) v.findViewById(R.id.fragment_user_insert);
        somethingButton2 = (Button) v.findViewById(R.id.fragment_user_something2);

        dbNameSpinner = (Spinner) v.findViewById(R.id.fragment_db_name_spinner);
        dbNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                tv.setText("position : " + position +
//                        parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dbColumnOne = (EditText) v.findViewById(R.id.fragment_user_data_name);
        dbColumnTwo = (EditText) v.findViewById(R.id.fragment_user_data_class);

        studentNameEdit = (EditText) v.findViewById(R.id.fragment_user_data_name);
        studentClassEdit = (EditText) v.findViewById(R.id.fragment_user_data_class);
        studentSelectResult = (TextView) v.findViewById(R.id.fragment_user_select_result);

//        createDb.setText("취소선");
        createDb.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        deleteAllDb.setOnClickListener(mOnClickListener);
        deleteTableData.setOnClickListener(mOnClickListener);
        createDb.setOnClickListener(mOnClickListener);
        somethingButton.setOnClickListener(mOnClickListener);
        selectAlltable.setOnClickListener(mOnClickListener);
        selectTableCnt.setOnClickListener(mOnClickListener);
        insertData.setOnClickListener(mOnClickListener);
        somethingButton2.setOnClickListener(mOnClickListener);


//        serviceController.initializeDb();
        
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
    
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String spinnerValue = dbNameSpinner.getSelectedItem().toString();
            Log.d(TAG, "spinnerValue : " + spinnerValue);
            switch (v.getId()){
                case R.id.fragment_user_delete_all_db :
                    serviceController.deleteDatabase();
                    break;

                case R.id.fragment_user_delete_all_table_data :
                    serviceController.deleteTableData(spinnerValue);
                    break;

                case R.id.fragment_user_create_db :
                    serviceController.createDatabase(spinnerValue);
                    break;

                case R.id.fragment_user_trans_db:
                    Cursor newDbCursor = serviceController.moveDatabase(); // data copy from select
                    while (newDbCursor.moveToNext()){ //
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put();
                        Log.d(TAG, "move DB : " + newDbCursor.getString(0) + ", " + newDbCursor.getString(1));
                        long insertResult = serviceController.insertData(spinnerValue, newDbCursor.getString(0), newDbCursor.getString(1));
                        Log.d(TAG, "insertResult : " + insertResult);
                    }
//                    mContext.deleteDatabase("stuent_tmp.db");
                    break;

                case R.id.fragment_user_select_all :
                    Cursor c = serviceController.selectDataAll(spinnerValue);
                    StringBuffer sBuffer = new StringBuffer();
                    Log.d(TAG, "columnCnt : " + c.getColumnCount());
                    while (c.moveToNext()){
                        Log.d(TAG, c.getString(0) + "\t" + c.getString(1) + "\n");
                        sBuffer.append(c.getString(0) + "\t" + c.getString(1) + "\n");
                        // james 3412
                        // karl 3403
                    }
                    studentSelectResult.setText(sBuffer.toString());
                    break;

                case R.id.fragment_user_select_cnt :
                    int count = serviceController.selectDataCount(spinnerValue);
                    Toast.makeText(getContext(), "select cnt : " + count, Toast.LENGTH_SHORT).show();
                    break;

                case R.id.fragment_user_insert :
                    String stdName = studentNameEdit.getText().toString();
                    String stdClass = studentClassEdit.getText().toString();
                    long insertResultNew = serviceController.insertData(spinnerValue, stdName, stdClass);
                    if(insertResultNew==-1){
                        Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "insertData Success", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.fragment_user_something2 :

                    break;


            }
        }
    };
}
