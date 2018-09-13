package sd.rtyy.com.example.qiu.drawer_try.LeftTitle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;
import sd.rtyy.com.example.qiu.drawer_try.database.collect;
import sd.rtyy.com.example.qiu.drawer_try.fragment.Fruit;
import sd.rtyy.com.example.qiu.drawer_try.fragment.FruitAdapter;
import sd.rtyy.com.example.qiu.drawer_try.fragment.FruitAdapter_search;
import sd.rtyy.com.example.qiu.drawer_try.global_variable.variable_quantity;
import sd.rtyy.com.example.qiu.drawer_try.toolbar_alpha;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

public class TreasuresActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{
    private FruitAdapter_search adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private SwipeRefreshLayout refreshLayout;
    private List<Merchandise> collect_merch;
    private FruitAdapter_search adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_treasures);

        TextView textView=(TextView)findViewById(R.id.list_text_title);
        textView.setText("我的收藏");
        ImageButton btn_back=(ImageButton)findViewById(R.id.list_back_arrow);
        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout1);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getParent());
        recyclerView.setLayoutManager(layoutManager);
        getDatas();
        adapter = new FruitAdapter_search(getApplicationContext(), collect_merch,"heh");
        recyclerView.setAdapter(adapter);

        //initFruits();
    }



    private List<Merchandise> getDatas(){
        collect_merch = new ArrayList<>();
        BmobQuery<collect> query = new BmobQuery<collect>();
        query.addWhereEqualTo("username", variable_quantity.whoLogin);
        query.include("merchandise");
        //Toast.makeText(TreasuresActivity.this,variable_quantity.whoLogin,Toast.LENGTH_SHORT).show();
        query.findObjects(new FindListener<collect>() {
            @Override
            public void done(List<collect> list, BmobException e) {
                if(e==null){
                    if(list.size()!=0){
                        for (int i =0; i < list.size();i++){
                            collect_merch.add(i,list.get(i).getMerchandise_c());
                            Log.d(TAG, "收藏的第"+i+"个"+collect_merch.get(i).getGoods_name());
                        }
                        Collections.reverse(collect_merch);
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getParent());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new FruitAdapter_search(getApplicationContext(), collect_merch,"heh");
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        Toast.makeText(TreasuresActivity.this,"暂无收藏信息",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    e.printStackTrace();
                    Toast.makeText(TreasuresActivity.this,"下载数据时出错",Toast.LENGTH_LONG).show();
                }

            }
        });
        return collect_merch;
    }

    private static final String TAG = "TreasuresActivity";
    private void initFruits() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getParent());
        recyclerView.setLayoutManager(layoutManager);
        getDatas();
        adapter = new FruitAdapter_search(getApplicationContext(), collect_merch,"heh");
        recyclerView.setAdapter(adapter);

//        BmobQuery<collect> query = new BmobQuery<collect>();
//        query.addWhereEqualTo("username", variable_quantity.whoLogin);
//        query.include("merchandise");
//        //Toast.makeText(TreasuresActivity.this,variable_quantity.whoLogin,Toast.LENGTH_SHORT).show();
//        query.findObjects(new FindListener<collect>() {
//            @Override
//            public void done(List<collect> list, BmobException e) {
//                if(e==null){
//                    if(list.size()!=0){
//                        for (int i =0; i < list.size();i++){
//                            collect_merch.add(i,list.get(i).getMerchandise_c());
//                            Log.d(TAG, "收藏的    "+collect_merch.get(i));
//                        }
//
//                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
//                        LinearLayoutManager layoutManager = new LinearLayoutManager(getParent());
//                        recyclerView.setLayoutManager(layoutManager);
//                        adapter = new FruitAdapter_search(getApplicationContext(), collect_merch,"heh");
//                        recyclerView.setAdapter(adapter);
//                    }else{
//                        Toast.makeText(TreasuresActivity.this,"暂无收藏信息",Toast.LENGTH_LONG).show();
//                    }
//
//                }else{
//                    e.printStackTrace();
//                    Toast.makeText(TreasuresActivity.this,"下载数据时出错",Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });


    }


    @Override
    public void onRefresh() {

        refreshLayout.setRefreshing(true);

        adapter.resetDatas();
        initFruits();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
