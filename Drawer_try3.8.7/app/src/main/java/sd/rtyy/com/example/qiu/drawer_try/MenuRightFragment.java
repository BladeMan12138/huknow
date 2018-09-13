package sd.rtyy.com.example.qiu.drawer_try;

/**
 * Created by qiu on 2016/8/14.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import sd.rtyy.com.example.qiu.drawer_try.LeftTitle.HomesActivity;
import sd.rtyy.com.example.qiu.drawer_try.Left_title_second.XiaoxiActivity;

public class MenuRightFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.menu_layout_right, container, false);
        ImageButton shoucang=(ImageButton)view.findViewById(R.id.shoucang);
        ImageButton xiaoxi=(ImageButton)view.findViewById(R.id.xiaoxi);
        ImageButton guanzhu=(ImageButton)view.findViewById(R.id.guanzhu);
        ImageButton zhuye=(ImageButton)view.findViewById(R.id.zhuye);
        shoucang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "已添加到“我的收藏”", Toast.LENGTH_LONG).show();


            }
        });
        xiaoxi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),XiaoxiActivity.class);
                startActivity(intent);

            }
        });
        guanzhu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "已添加到“我关注的”", Toast.LENGTH_LONG).show();


            }
        });
        zhuye.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),HomesActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
