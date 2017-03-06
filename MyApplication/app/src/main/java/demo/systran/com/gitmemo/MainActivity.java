package demo.systran.com.gitmemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId(); // 현재 클릭된 객체의 아이디를 얻어와서
        // 이하에서 처리
        switch(id) {
            case R.id.action_menu:
                Toast.makeText(getApplicationContext(), "Android Alarm", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_setting:
                Toast.makeText(getApplicationContext(), "New Game", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
