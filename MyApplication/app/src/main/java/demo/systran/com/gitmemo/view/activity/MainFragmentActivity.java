package demo.systran.com.gitmemo.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import demo.systran.com.gitmemo.R;
import demo.systran.com.gitmemo.view.fragment.ABestFragment;

public class MainFragmentActivity extends FragmentActivity {
    private String TAG= "MainFragmentActivity";

    private Button btn_one = null;
    private Button btn_two = null;
    private Button btn_three = null;

    private FrameLayout frameLayout = null;
    private Fragment fragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 게시판 만들기 */

        btn_one = (Button) findViewById(R.id.fragment_button_one);
        btn_two = (Button) findViewById(R.id.fragment_button_two);
        btn_three = (Button) findViewById(R.id.fragment_button_three);

//        frameLayout = (FrameLayout)findViewById(R.id.frame_layout);

        btn_one.setOnClickListener(mOnClickListener);
        btn_two.setOnClickListener(mOnClickListener);
        btn_three.setOnClickListener(mOnClickListener);

    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.fragment_button_one :
                    fragment= new ABestFragment();
                    break;
                case R.id.fragment_button_two :

                    break;
                case R.id.fragment_button_three :

                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, fragment);
            transaction.commit();
        }
    };


}

