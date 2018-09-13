package sd.rtyy.com.example.qiu.drawer_try;

/**
 * Created by qiu on 2016/8/14.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sd.rtyy.com.example.qiu.drawer_try.LeftTitle.FocusActivity;
import sd.rtyy.com.example.qiu.drawer_try.LeftTitle.HomesActivity;
import sd.rtyy.com.example.qiu.drawer_try.LeftTitle.MessageActivity;
import sd.rtyy.com.example.qiu.drawer_try.LeftTitle.MoonActivity;
import sd.rtyy.com.example.qiu.drawer_try.LeftTitle.ShezhiActivity;
import sd.rtyy.com.example.qiu.drawer_try.LeftTitle.TreasuresActivity;
import sd.rtyy.com.example.qiu.drawer_try.global_variable.variable_quantity;


public class MenuLeftFragment extends Fragment
{

    private ListViewAdapter adapter;
    private TextView users;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.layout_menu, container, false);

        users = (TextView)view.findViewById(R.id.users);
        users.setText(variable_quantity.whoLogin);

        ListView list=(ListView)view.findViewById(R.id.listView_left);
        //ListViewAdapter adapter=new ListViewAdapter();
        adapter=new ListViewAdapter();
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){Intent intent = new Intent(getActivity(), HomesActivity.class);
                    startActivity(intent);}
                if(position==1){Intent intent = new Intent(getActivity(), TreasuresActivity.class);
                    Log.d("MenuLeftFragment", "onItemClick:mawenqitiaozhuanle ");
                    startActivity(intent);}
                if(position==2){Intent intent = new Intent(getActivity(), MessageActivity.class);
                    Log.d("MenuLeftFragment", "onItemClick:mawenqitiaozhuanle ");
                    startActivity(intent);}
                if(position==3){Intent intent = new Intent(getActivity(), FocusActivity.class);
                    startActivity(intent);}
                if(position==4){Intent intent = new Intent(getActivity(), MoonActivity.class);
                    startActivity(intent);}
                if(position==5){
                    Intent intent = new Intent(getActivity(), ShezhiActivity.class);
                    startActivity(intent);;                }

            }

        });
        return view;
    }



    public class ListViewAdapter extends BaseAdapter {
        View[] itemViews;
        List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;//=new HashMap<String, Object>();

        public ListViewAdapter() {
            String[] list_text=new String[6];
            int[] itemImageRes=new int[6];
            list_text[0]="我的主页";
            list_text[1]="我的收藏";
            list_text[2]="我的消息";
            list_text[3]="我的关注";
            list_text[4]="我的发布";


            itemImageRes[0]=R.drawable.img_1;
            itemImageRes[1]=R.drawable.img_2;
            itemImageRes[2]=R.drawable.img_3;
            itemImageRes[3]=R.drawable.img_4;
            itemImageRes[4]=R.drawable.img_5;


            for(int i=0;i<5;i++){
                map = new HashMap<String, Object>();

                map.put("text", list_text[i]);
                map.put("IMG", itemImageRes[i]);
                arrayList.add(map);


            }
            itemViews = new View[6];
            for (int i = 0; i < itemViews.length; i++) {
                if(i==5){
                    itemViews[5] = makeItemView_2();
                }
                else
                {itemViews[i] = makeItemView(arrayList.get(i));}

            }
        }

        public int getCount() {
            return itemViews.length;
        }

        public View getItem(int position) {
            return itemViews[position];
        }

        public long getItemId(int position) {
            return position;
        }

        private View makeItemView(Map<String,Object> map) {
            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.list_item, null);

            map.get("text");
            map.get("IMG");
            // 通过findViewById()方法实例R.layout.item内各组件
            TextView list_text = (TextView) itemView.findViewById(R.id.list_text);
            list_text.setText(map.get("text").toString());

            ImageView image = (ImageView) itemView.findViewById(R.id.list_img);
            image.setImageResource(Integer.parseInt(map.get("IMG").toString()));

            return itemView;
        }
        private View makeItemView_2() {
            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.list_item_setting, null);
            map.get("text");
            map.get("IMG");
            // 通过findViewById()方法实例R.layout.item内各组件
            TextView list_text = (TextView) itemView.findViewById(R.id.list_text_2);
            list_text.setText("设置");

            ImageView image = (ImageView) itemView.findViewById(R.id.list_img_2);
            image.setImageResource(R.drawable.img_6);

            return itemView;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                return itemViews[position];
            return convertView;
        }
}
}

