package sd.rtyy.com.example.qiu.drawer_try.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.map.PoiKeywordSearchActivity;

/**
 * Created by lenovo on 2017/8/22.
 */

public class buyit extends Activity{

    Button faceToface;
    Button online;
    ImageButton back;
    private String telephone;
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.buyit);
        telephone = getIntent().getStringExtra("telephone");
        back = (ImageButton)findViewById(R.id.dispaly_backarrow);
        faceToface = (Button)findViewById(R.id.faceToface);
        online = (Button)findViewById(R.id.online);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        faceToface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(buyit.this,PoiKeywordSearchActivity.class);
                intent.putExtra("telephone",telephone);
                startActivity(intent);
            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }
}
