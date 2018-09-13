package sd.rtyy.com.example.qiu.drawer_try.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.LogTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import sd.rtyy.com.example.qiu.drawer_try.R;

import sd.rtyy.com.example.qiu.drawer_try.NewThings;
import sd.rtyy.com.example.qiu.drawer_try.SearchInSecondMarket;
import sd.rtyy.com.example.qiu.drawer_try.adapter.ProvinceAdapter;
import sd.rtyy.com.example.qiu.drawer_try.adapter.SchoolAdapter;
import sd.rtyy.com.example.qiu.drawer_try.config.Config;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;
import sd.rtyy.com.example.qiu.drawer_try.database.business;
import sd.rtyy.com.example.qiu.drawer_try.database.marketGoods;
import sd.rtyy.com.example.qiu.drawer_try.database.saler_goods;
import sd.rtyy.com.example.qiu.drawer_try.entity.Province;
import sd.rtyy.com.example.qiu.drawer_try.entity.ProvinceList;
import sd.rtyy.com.example.qiu.drawer_try.entity.School;
import sd.rtyy.com.example.qiu.drawer_try.entity.SchoolList;
import sd.rtyy.com.example.qiu.drawer_try.global_variable.variable_quantity;
import sd.rtyy.com.example.qiu.drawer_try.login.*;
import sd.rtyy.com.example.qiu.drawer_try.login.MainActivity;
import sd.rtyy.com.example.qiu.drawer_try.utils.GsonRequest;

/**
 * Created by qiu on 2016/8/14.
 */
public class Fragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "Fragment1";

    private static List<Merchandise> merchandiseList = new ArrayList<>();
    public  static List<Bitmap> bit = new LinkedList<>();

    private Handler mHandler = new Handler(Looper.getMainLooper());


    public String[] Burl = new String[100];
    public List<String> burl = new LinkedList<>();

    public int getList_size() {
        return merchandiseList.size();

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //onRefresh();

        return inflater.inflate(R.layout.activity_main1, container, false);

    }

    private FragmentManager fm;
    private FragmentTransaction ft;
    public Fragment1 Frament1;
    private RecyclerView recyclerView;
    private FruitAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private GridLayoutManager layoutManager;
    private boolean urlIsFull = false;
    private boolean listIsFull = false;
    String college_f1;
    TextView chooseCollege_f1;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().setContentView(R.layout.activity_main1);
        //getMerchandise();




        ImageButton sear=(ImageButton)view.findViewById(R.id.list_search);
        sear.setOnClickListener(    new View.OnClickListener() {

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
         chooseCollege_f1 = (TextView)view.findViewById(R.id.select_school_f1);

        if(!variable_quantity.whetherChooseCollege) {
            getUsersCollege();
            chooseCollege_f1.setText(college_f1);
        }

        if(variable_quantity.frament1_chooseSchool!=null){
            chooseCollege_f1.setText(variable_quantity.frament1_chooseSchool);
        }
        Log.d(TAG, "systemTime: "+(int)System.currentTimeMillis());
        Log.d(TAG, "college_f1: "+college_f1);
        Log.d(TAG, "variable_quantity.userscollege: "+variable_quantity.usersCollege);
        Log.d(TAG, "variable_quantity.fragment1_chooseSchool: "+variable_quantity.frament1_chooseSchool);
        Log.d(TAG, "whetherchooseCollege(外）: "+ variable_quantity.whetherChooseCollege);


        initPopView();
        chooseCollege_f1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                showPopWindow();

            }
        });

        getMerchandise();

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        initRefreshLayout();
        initRecyclerView();

    }
    private void initRecyclerView(){
        adapter = new FruitAdapter(getDatas(0, PAGE_COUNT), this.getActivity(), getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        //GridLayoutManager.VERTICAL
        layoutManager = new GridLayoutManager(this.getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
        //autoRefresh();
    }

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }


    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;

    private View parent;
    private ListView mProvinceListView;
    private ListView mSchoolListView;
    private TextView mTitle;
    private PopupWindow mPopWindow;
    /**
     * Volley相关
     **/
    private RequestQueue mRequestQueue;
    /**
     * Adapter相关
     **/
    private ProvinceAdapter mProvinceAdapter;
    private SchoolAdapter mSchoolAdapter;
    private String provinceId;
    private void initPopView() {
        parent = this.getActivity().getWindow().getDecorView();
        View popView = View.inflate(this.getActivity(), R.layout.view_select_province_list_main, null);
        mTitle = (TextView) popView.findViewById(R.id.list_title);
        mProvinceListView = (ListView) popView.findViewById(R.id.province);
        mSchoolListView = (ListView) popView.findViewById(R.id.school);
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseCollege_f1.setText("全部商品");
                mPopWindow.dismiss();
                variable_quantity.frament1_chooseSchool = "全部商品";
            }
        });
        mProvinceListView.setOnItemClickListener(itemListener);
        mSchoolListView.setOnItemClickListener(itemListener);

        mProvinceAdapter = new ProvinceAdapter(this.getActivity());
        mProvinceListView.setAdapter(mProvinceAdapter);
        mSchoolAdapter = new SchoolAdapter(this.getActivity());
        mSchoolListView.setAdapter(mSchoolAdapter);

        int width = getResources().getDisplayMetrics().widthPixels * 3 / 4;
        int height = getResources().getDisplayMetrics().heightPixels * 3 / 5;
        mPopWindow = new PopupWindow(popView, width, height);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.setFocusable(true);
        mPopWindow.setTouchable(true);
        mPopWindow.setOutsideTouchable(true);//允许在外侧点击取消

        loadProvince();

        mPopWindow.setOnDismissListener(listener);
    }
    private void showPopWindow() {
        mPopWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }
    private void loadProvince() {
        mRequestQueue = Volley.newRequestQueue(this.getActivity());
        GsonRequest<Province> request = new GsonRequest<Province>(Request.Method.POST, Config.PROVINCE_URL,
                Province.class, new Response.Listener<Province>() {
            @Override
            public void onResponse(Province response) {

                if (response.getData() != null && response.getError_code() == 0) {
                    mProvinceAdapter.setList(response.getData());
                    mProvinceAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }, this.getActivity());
        mRequestQueue.add(request);

    }
    private void loadSchool() {
        mRequestQueue = Volley.newRequestQueue(this.getActivity());
        GsonRequest<School> request = new GsonRequest<>(Request.Method.POST, Config.SCHOOL_URL + provinceId, School.class,
                new Response.Listener<School>() {
                    @Override
                    public void onResponse(School response) {
                        if (response.getData() != null && response.getError_code() == 0){
                            mSchoolAdapter.setList(response.getData());
                            mSchoolAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }, this.getActivity());
        mRequestQueue.add(request);
    }

    AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent == mProvinceListView) {
                ProvinceList provinceName = (ProvinceList) mProvinceListView.getItemAtPosition(position);
                provinceId = provinceName.getProvince_id();
                getUsersCollege();
                //mTitle.setText(college_f1);
                mProvinceListView.setVisibility(View.GONE);
                mSchoolListView.setVisibility(View.VISIBLE);
                loadSchool();
            } else if (parent == mSchoolListView) {
                SchoolList schoolName = (SchoolList) mSchoolListView.getItemAtPosition(position);
                chooseCollege_f1.setText(schoolName.getSchool_name());
                variable_quantity.whetherChooseCollege = true;
                variable_quantity.frament1_chooseSchool = schoolName.getSchool_name();
                Log.d(TAG, "onItemClick: chooseSchool:"+schoolName.getSchool_name());
                Log.d(TAG, "onItemClick: chooseSchool:"+schoolName.getSchool_name()+"whetherChooseCollege "+variable_quantity.whetherChooseCollege);
                mPopWindow.dismiss();
            }
        }
    };

    /**
     * popWindow消失监听事件
     */

    PopupWindow.OnDismissListener listener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            //mTitle.setText("选择地区");
            mProvinceListView.setVisibility(View.VISIBLE);
            mSchoolAdapter.setList(new ArrayList<SchoolList>());
            mSchoolAdapter.notifyDataSetChanged();
            mSchoolListView.setVisibility(View.GONE);
        }
    };

    public void getUsersCollege(){
        if(variable_quantity.identify.equals("学生")){

            BmobQuery<users> query = new BmobQuery<>();
            query.addWhereEqualTo("username", MainActivity.username_key);
            query.findObjects(new FindListener<users>() {
                @Override
                public void done(List<users> list, BmobException e) {
                    college_f1 = list.get(0).getUsersCollege();
                    variable_quantity.usersCollege = college_f1;
                }
            });
        }else{
            BmobQuery<business> query = new BmobQuery<>();
            query.addWhereEqualTo("businessName", MainActivity.username_key);
            query.findObjects(new FindListener<business>() {
                @Override
                public void done(List<business> list, BmobException e) {
                    college_f1 = list.get(0).getBusinessCollege();
                    variable_quantity.usersCollege = college_f1;
                }
            });
        }
    }
    private List<saler_goods> list_saler_goods = new ArrayList<>();

    private void getMerchandise() {

//
//        BmobQuery<saler_goods> saler_goods= new BmobQuery<>();
//        BmobQuery<users> users = new BmobQuery<>();
//        users.addWhereEqualTo("usersCollege",variable_quantity.frament1_chooseSchool);
//        saler_goods.addWhereMatchesQuery("username","users",users);
//        saler_goods.findObjects(new FindListener<saler_goods>() {
//            @Override
//            public void done(List<saler_goods> list, BmobException e) {
//                if(e==null){
//                    list_saler_goods = list;
//                }
//            }
//        });


        BmobQuery<Merchandise> query_merchandise = new BmobQuery<>();
        if (variable_quantity.frament1_chooseSchool != "全部商品"){
            query_merchandise.addWhereEqualTo("goodsBelongToC", variable_quantity.frament1_chooseSchool);
            query_merchandise.findObjects(new FindListener<Merchandise>() {
            @Override
            public void done(List<Merchandise> list, BmobException e) {
                if (e == null) {
                    Collections.reverse(list);
                    merchandiseList = list;
                    if (merchandiseList.size() != 0) {
                        listIsFull = true;
                    }
                } else {
                    Log.d(TAG, e.toString());
                }
            }
        });
        }else{
            query_merchandise.findObjects(new FindListener<Merchandise>() {
                @Override
                public void done(List<Merchandise> list, BmobException e) {
                    if (e == null) {
                        Collections.reverse(list);
                        merchandiseList = list;
                        if (merchandiseList.size() != 0) {
                            listIsFull = true;
                        }
                    } else {
                        Log.d(TAG, e.toString());
                    }
                }
            });
        }


        //}else{
           // query_merchandise.addWhereEqualTo()
       // }
    }

//    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            Toast.makeText(getContext(), "You choose " + province[position], Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }
//    }
    private void init_data(){
        BmobQuery<Merchandise> query_merchandise = new BmobQuery<>();
        query_merchandise.findObjects(new FindListener<Merchandise>() {
            @Override
            public void done(List<Merchandise> list, BmobException e) {
                if(e==null){
                    merchandiseList = list;
                    if(merchandiseList.size()!=0){
                        listIsFull = true;
                    }
                }
                else{
                    Log.d(TAG,e.toString());
                }
            }
        });
    }

    private List<Merchandise> getDatas(final int firstIndex, final int lastIndex) {
        List<Merchandise> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < merchandiseList.size()) {
                resList.add(merchandiseList.get(i));
            }
        }
        return resList;
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<Merchandise> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            adapter.updateList(newDatas, true);
        } else {
            adapter.updateList(null, false);
        }
    }

    public void autoRefresh(){
        if(variable_quantity.frament1_chooseSchool!="全部商品"){
            if(!variable_quantity.whetherChooseCollege) {
                getUsersCollege();
                variable_quantity.frament1_chooseSchool=college_f1;
                chooseCollege_f1.setText(college_f1);
            }
            else if(variable_quantity.frament1_chooseSchool!=null){
                chooseCollege_f1.setText(variable_quantity.frament1_chooseSchool);
            }
        }
        refreshLayout.setRefreshing(true);
        getMerchandise();
        adapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        if(variable_quantity.frament1_chooseSchool!="全部商品"){
        if(!variable_quantity.whetherChooseCollege) {
            getUsersCollege();
            variable_quantity.frament1_chooseSchool=college_f1;
            chooseCollege_f1.setText(college_f1);
        }
        else if(variable_quantity.frament1_chooseSchool!=null){
            chooseCollege_f1.setText(variable_quantity.frament1_chooseSchool);
        }
        }

        adapter.resetDatas();
        getMerchandise();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

}
