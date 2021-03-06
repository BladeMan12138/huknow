package sd.rtyy.com.example.qiu.drawer_try.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import sd.rtyy.com.example.qiu.drawer_try.NEW.Auction_alpha;
import sd.rtyy.com.example.qiu.drawer_try.NEW.Change_alpha;
import sd.rtyy.com.example.qiu.drawer_try.NewThings;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.SearchInSecondMarket;
import sd.rtyy.com.example.qiu.drawer_try.config.Config;
import sd.rtyy.com.example.qiu.drawer_try.database.Exchange;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;

/**
 * Created by qiu on 2016/8/14.
 */
public class Fragment3 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private List<Exchange> exchangeList = new ArrayList<>();
    private RecyclerView recyclerView;

    private Handler mHandler = new Handler(Looper.getMainLooper());


    private FruitAdapter_exch adapter;
    private SwipeRefreshLayout refreshLayout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main1, container, false);
    }

    //@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().setContentView(R.layout.activity_main1);
        getExchange();
        ImageButton sear=(ImageButton)view.findViewById(R.id.list_search);
        sear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchInSecondMarket.class);
                startActivity(intent);

            }
        });

        ImageButton newThing=(ImageButton)view.findViewById(R.id.list_jiahao);
        newThing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),NewThings.class);
                startActivity(intent);

            }
        });
        //TextView list_text_search = (TextView)view.findViewById(R.id.list_text_search);
        //list_text_search.setText("以物易物");
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2 ,
//                StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        getExchange();
        adapter = new FruitAdapter_exch(this.getContext(),exchangeList);
        recyclerView.setAdapter(adapter);

    }

    private void getExchange(){

        BmobQuery<Exchange> query_exchange = new BmobQuery<>();
        query_exchange.findObjects(new FindListener<Exchange>() {
            @Override
            public void done(List<Exchange> list, BmobException e) {
                if(e==null){
                    Collections.reverse(list);
                    exchangeList = list;
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getExchange();
                    adapter = new FruitAdapter_exch(getContext(),exchangeList);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }


    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(name);
        }
        return builder.toString();
    }
    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        adapter.resetDatas();
        getExchange();
        //updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

}