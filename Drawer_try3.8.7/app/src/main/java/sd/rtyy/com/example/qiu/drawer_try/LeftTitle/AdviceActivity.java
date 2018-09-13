package sd.rtyy.com.example.qiu.drawer_try.LeftTitle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import sd.rtyy.com.example.qiu.drawer_try.R;

public class AdviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_advice);
        TextView textView=(TextView)findViewById(R.id.list_text_title);
        textView.setText("提出意见");
        ImageButton btn_back=(ImageButton)findViewById(R.id.list_back_arrow);
        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        final EditText edit = (EditText) findViewById(R.id.advice);
        Button button = (Button)findViewById(R.id.submit_advice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edit.setText("");
                Toast.makeText(getApplicationContext(), "您已成功提交，谢谢！", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
