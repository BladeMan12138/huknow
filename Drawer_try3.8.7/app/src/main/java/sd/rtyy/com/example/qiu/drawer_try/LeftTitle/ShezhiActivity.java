package sd.rtyy.com.example.qiu.drawer_try.LeftTitle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.fragment.About;
import sd.rtyy.com.example.qiu.drawer_try.login.MainActivity;

import static sd.rtyy.com.example.qiu.drawer_try.Collectors.ActivityCollector.finishAllActs;
import static sd.rtyy.com.example.qiu.drawer_try.Collectors.FragCollector.finishAllFrags;

public class ShezhiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shezhi);
        TextView textView=(TextView)findViewById(R.id.list_text_title);
        textView.setText("设置");
        ImageButton btn_back=(ImageButton)findViewById(R.id.list_back_arrow);
        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        /*ImageView about = (ImageView) findViewById(R.id.setting_about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShezhiActivity.this, About.class);
                startActivity(intent);
            }
        });

        ImageView advice = (ImageView) findViewById(R.id.setting_advice);
        advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShezhiActivity.this, AdviceActivity.class);
                startActivity(intent);
            }
        });*/

        ToggleButton mTogBtn = (ToggleButton) findViewById(R.id.mTogBtn); // 获取到控件
        mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //选中
                }else{
                    //未选中
                }
            }
        });

        Button layoff = (Button)findViewById(R.id.logoff);
        layoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAllFrags();
                finishAllActs();
                finish();
                Intent intent = new Intent(ShezhiActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }

    public void startAbout(View v){
        Intent intent = new Intent(ShezhiActivity.this, About.class);
        startActivity(intent);
    }

    public void startAdvice(View v){
        Intent intent = new Intent(ShezhiActivity.this, AdviceActivity.class);
        startActivity(intent);
    }

    public void startMemory(View v){
        Toast.makeText(this, "您已成功清除缓存", Toast.LENGTH_SHORT).show();
    }
}
