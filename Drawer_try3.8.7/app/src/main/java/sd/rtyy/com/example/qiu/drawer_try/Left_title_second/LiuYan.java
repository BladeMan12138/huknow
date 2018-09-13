package sd.rtyy.com.example.qiu.drawer_try.Left_title_second;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import sd.rtyy.com.example.qiu.drawer_try.LeftTitle.HomesActivity;
import sd.rtyy.com.example.qiu.drawer_try.R;

public class LiuYan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liu_yan);
        TextView textView=(TextView)findViewById(R.id.list_text_title);
        textView.setText("给我留言");
        ImageButton btn_back=(ImageButton)findViewById(R.id.list_back_arrow);
        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_liuyan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LiuYan.this, XiaoxiActivity.class);
                startActivity(intent);
            }
        });
    }

}
