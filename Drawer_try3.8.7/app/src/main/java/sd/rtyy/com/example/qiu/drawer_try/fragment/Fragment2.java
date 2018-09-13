package sd.rtyy.com.example.qiu.drawer_try.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import sd.rtyy.com.example.qiu.drawer_try.NewThings;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.SearchInSecondMarket;
import sd.rtyy.com.example.qiu.drawer_try.database.Auction;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;

/**
 * Created by qiu on 2016/8/14.
 */
public class Fragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private List<Merchandise> merchandiseList = new ArrayList<>();
    private List<Auction> auctionlist = new ArrayList<>();

    private Handler mHandler = new Handler(Looper.getMainLooper());

    RecyclerView recyclerView;
    private FruitAdapter_auc adapter;
    private SwipeRefreshLayout refreshLayout;

    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 5;

    /*public int getList_size() {
        return merchandiseList.size();

    }*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main1, container, false);
    }

    //@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().setContentView(R.layout.activity_main1);
        getAuction();
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
        //list_text_search.setText("竞拍专场");
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(),GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter_auc(getDatas(0, PAGE_COUNT), this.getActivity(), getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

    }

//    private void getMerchandise(){
//        BmobQuery<Merchandise> query_merchandise = new BmobQuery<>();
//        query_merchandise.findObjects(new FindListener<Merchandise>() {
//            @Override
//            public void done(List<Merchandise> list, BmobException e) {
//                if(e==null){
//                    merchandiseList = list;
//                }
//            }
//        });
//    }


    private void getAuction(){
        BmobQuery<Auction> query_auction = new BmobQuery<>();
        query_auction.findObjects(new FindListener<Auction>() {
            @Override
            public void done(List<Auction> list, BmobException e) {
                if(e==null){
                    Collections.reverse(list);
                    auctionlist= list;
                }
            }
        });
    }

    private List<Auction> getDatas(final int firstIndex, final int lastIndex) {
        List<Auction> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < auctionlist.size()) {
                resList.add(auctionlist.get(i));
            }
        }
        return resList;
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<Auction> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            adapter.updateList(newDatas, true);
        } else {
            adapter.updateList(null, false);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        adapter.resetDatas();
        getAuction();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }


}
