package sd.rtyy.com.example.qiu.drawer_try.Left_title_second;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import sd.rtyy.com.example.qiu.drawer_try.R;

public class XiaoxiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaoxi2);
        TextView textView=(TextView)findViewById(R.id.list_text_title);
        textView.setText("给TA留言");
        ImageButton btn_back=(ImageButton)findViewById(R.id.list_back_arrow);
        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_XIAOXI);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(XiaoxiActivity.this, "对方已收到您的留言", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }

}
