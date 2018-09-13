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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import sd.rtyy.com.example.qiu.drawer_try.NEW.Change_alpha;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;
import sd.rtyy.com.example.qiu.drawer_try.fragment.Fruit;
import sd.rtyy.com.example.qiu.drawer_try.fragment.FruitAdapter;
import sd.rtyy.com.example.qiu.drawer_try.fragment.FruitAdapter_search;
import sd.rtyy.com.example.qiu.drawer_try.global_variable.variable_quantity;
import sd.rtyy.com.example.qiu.drawer_try.toolbar_alpha;

public class MoonActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{
    private FruitAdapter_search adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private SwipeRefreshLayout refreshLayout;
    private List<Merchandise> fruitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasures);

        TextView textView=(TextView)findViewById(R.id.list_text_title);
        textView.setText("我的发布");
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
        adapter = new FruitAdapter_search(getBaseContext(), fruitList,"myPublic");
        recyclerView.setAdapter(adapter);

        //initFruits();
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getParent());
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new FruitAdapter_search(getBaseContext(), fruitList,"myPublic");
//        recyclerView.setAdapter(adapter);

    }
    private List<Merchandise> getDatas(){
        fruitList = new ArrayList<>();
        BmobQuery<Merchandise> query = new BmobQuery<Merchandise>();
        query.addWhereEqualTo("isUsername", variable_quantity.whoLogin);
        query.findObjects(new FindListener<Merchandise>() {
            @Override
            public void done(List<Merchandise> list, BmobException e) {
                if(e==null){
                    if(list.size()!=0){
                        fruitList = list;
                        Collections.reverse(fruitList);
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getParent());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new FruitAdapter_search(getBaseContext(), fruitList,"myPublic");
                        recyclerView.setAdapter(adapter);
                    }

                    else
                        Toast.makeText(MoonActivity.this,"暂无发布信息",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MoonActivity.this,"下数据时出错",Toast.LENGTH_LONG).show();
                }
            }
        });
        return fruitList;
    }
    private void initFruits() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getParent());
        recyclerView.setLayoutManager(layoutManager);
        getDatas();
        adapter = new FruitAdapter_search(getBaseContext(), fruitList,"myPublic");
        recyclerView.setAdapter(adapter);


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

