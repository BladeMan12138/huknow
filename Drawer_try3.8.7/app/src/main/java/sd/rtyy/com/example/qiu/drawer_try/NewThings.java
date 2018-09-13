package sd.rtyy.com.example.qiu.drawer_try;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import sd.rtyy.com.example.qiu.drawer_try.NEW.NewSecond;
import sd.rtyy.com.example.qiu.drawer_try.NEW.NewSecond2;

import sd.rtyy.com.example.qiu.drawer_try.NEW.New_Auction2;
import sd.rtyy.com.example.qiu.drawer_try.NEW.New_Change;
import sd.rtyy.com.example.qiu.drawer_try.NEW.New_Sale;
import sd.rtyy.com.example.qiu.drawer_try.NEW.h_m_s_daojishi;
import sd.rtyy.com.example.qiu.drawer_try.NEW.timeStamp;

public class NewThings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_things);
        TextView textView=(TextView)findViewById(R.id.list_text_title);
        textView.setText("我要发布");

        ImageButton btn_back=(ImageButton)findViewById(R.id.list_back_arrow);
        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        Button button=(Button)findViewById(R.id.id_new_second);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NewSecond2.which = 1;
                Intent intent=new Intent(NewThings.this,NewSecond2.class);
                startActivity(intent);

            }
        });
        Button button2=(Button)findViewById(R.id.id_new_pai);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewThings.this,New_Auction2.class);
                startActivity(intent);

            }
        });
        Button button3=(Button)findViewById(R.id.id_new_change);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NewSecond2.which = 3;
                Intent intent=new Intent(NewThings.this,NewSecond2.class);
                startActivity(intent);

            }
        });
        Button button4=(Button)findViewById(R.id.id_new_sale);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NewSecond2.which = 4;
                Intent intent=new Intent(NewThings.this,NewSecond2.class);
                startActivity(intent);

            }
        });

    }

}
