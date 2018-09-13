package sd.rtyy.com.example.qiu.drawer_try.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;
import sd.rtyy.com.example.qiu.drawer_try.database.collect;
import sd.rtyy.com.example.qiu.drawer_try.global_variable.variable_quantity;
import sd.rtyy.com.example.qiu.drawer_try.login.users;

/**
 * Created by lenovo on 2017/7/27.
 */

public class displaytest2 extends Activity{
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    private List<Map<String, Object>> data;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    private List<View> dots;
    private int oldPosition = 0;
    private int currentItem;
    private ImageView image1,image2,image3;
    private List<ImageView> images;
    private List<String> url;
    String [] urll=new String[3];
    private ImageButton back;
    private ImageButton buyit;
    private String goods_name;
    private String  goods_price;
    private String description;
    private String isUsername;
    private String telephone;
    private int goods_id;
    private CheckBox collect_btn;

    private TextView price,name,tv_description;
    private String objectId;
    private Merchandise merc = new Merchandise();
    private String collect_objId;
    private collect collect = new collect();

    private static final String TAG = "displaytest2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        back = (ImageButton) findViewById(R.id.dispaly_backarrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        buyit = (ImageButton)findViewById(R.id.buyit);


        images = new ArrayList<ImageView>();
        url = new ArrayList<String>();
        image1 = new ImageView(this);
        image2 = new ImageView(this);
        image3 = new ImageView(this);

        price = (TextView)findViewById(R.id.display_price);
        name = (TextView)findViewById(R.id.display_goodsname);
        tv_description = (TextView)findViewById(R.id.dispaly_description);
        isUsername = getIntent().getStringExtra("isUsername");
        urll[0] = getIntent().getStringExtra("url1");
        urll[1] = getIntent().getStringExtra("url2");
        urll[2] = getIntent().getStringExtra("url3");
        goods_id = getIntent().getIntExtra("goods_id",-1);
        goods_name = getIntent().getStringExtra("goods_name");
        description = getIntent().getStringExtra("description");
        goods_price = getIntent().getStringExtra("goods_price");
        objectId = getIntent().getStringExtra("objectId");
        Log.d(TAG, "displaytest2_objectId:   "+objectId);
        price.setText("¥"+goods_price);
        name.setText(goods_name);
        tv_description.setText("商品介绍：   "+description);
        //得到当前商品的一行数据
        BmobQuery<Merchandise> query = new BmobQuery<Merchandise>();


        collect_btn = (CheckBox)findViewById(R.id.cherish);
        collect_btn.setChecked(false);
        query.getObject(objectId, new QueryListener<Merchandise>() {
            @Override
            public void done(Merchandise merchandise, BmobException e) {
                if(e==null){
                    merc = merchandise;
                    checkIfcollected();
                }
                else{

                }
            }
        });

        collect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(collect_btn.isChecked()){
                    //我要收藏这个商品，往收藏表里添加一行数据

                    collect.setUsername(variable_quantity.whoLogin);
                    collect.setMerchandise_c(merc);
                    collect.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(displaytest2.this,"已添加到我的收藏",Toast.LENGTH_SHORT).show();
                                collect_objId = collect.getObjectId();
                            }
                            else{
                                Toast.makeText(displaytest2.this,"收藏失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    //从收藏表里删除这个数据

                    collect.setObjectId(collect_objId);
                    collect.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(displaytest2.this,"已取消收藏",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(displaytest2.this,"取消收藏时出现错误",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        buyit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(variable_quantity.identify.equals("商家")){
                    Toast.makeText(displaytest2.this,"抱歉，商家账号不能购买商品",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG, "username: "+isUsername);
                    BmobQuery<users> query_user = new BmobQuery<>();
                    query_user.addWhereEqualTo("username",isUsername);
                    query_user.findObjects(new FindListener<users>() {
                        @Override
                        public void done(List<users> list, BmobException e) {
                            if(e==null){
                                telephone = list.get(0).getTelephone();
                                variable_quantity.telephone = list.get(0).getTelephone();
                                Log.d(TAG, "telephone: "+telephone);
                                Intent intent = new Intent(displaytest2.this,buyit.class);
                                intent.putExtra("telephone",telephone);
                                startActivity(intent);
                            }
                        }
                    });

                }
            }
        });

            init();
    }
    public void init() {
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        viewPager = (ViewPager) findViewById(R.id.vp);
        data = getData();
        imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.loading) // 在ImageView加载过程中显示图片
                .showImageForEmptyUri(R.drawable.empty_adress) // image连接地址为空时
                .showImageOnFail(R.drawable.failloading) // image加载失败
                .cacheInMemory(true) // 加载图片时会在内存中加载缓存
                .cacheOnDisc(true) // 加载图片时会在磁盘中加载
                .build();
        adapter = new ViewPagerAdapter(data);
        viewPager.setAdapter(adapter);
        images.add(image1);
        images.add(image2);
        images.add(image3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                //title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);

                images.get(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> mdata = new ArrayList<Map<String, Object>>();
        if(urll[0]!=null){
        Map<String, Object> map = new HashMap<String, Object>();
        //map.put("url", "http://img2.duitang.com/uploads/item/201207/19/20120719132725_UkzCN.jpeg");
        map.put("url", urll[0]);
        map.put("view", image1);
        mdata.add(map);
            if(urll[1]!=null) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            //map1.put("url", "http://img4.duitang.com/uploads/item/201404/24/20140424195028_vtvZu.jpeg");
            map1.put("url", urll[1]);
            map1.put("view", image2);
            mdata.add(map1);
                if(urll[2]!=null) {
                Map<String, Object> map2 = new HashMap<String, Object>();
                //map2.put("url", "http://bmob-cdn-12994.b0.upaiyun.com/2017/07/26/d08aec5a6ac040148e61dd0498eb6c2a.jpg");
                map2.put("url", urll[2]);
                map2.put("view", image3);
                mdata.add(map2);
                }
            }

        }

        return  mdata;
    }
    private void checkIfcollected(){
        BmobQuery<collect> query = new BmobQuery<>();
        query.addWhereEqualTo("username",variable_quantity.whoLogin);
        query.addWhereEqualTo("merchandise",merc);
        query.findObjects(new FindListener<collect>() {
            @Override
            public void done(List<collect> list, BmobException e) {
                if(e==null){

                    if(list.size() == 1){
                        //本用户已经收藏过这个商品
                        collect_objId = list.get(0).getObjectId();
                        //Toast.makeText(displaytest2.this,"检测到有这个收藏",Toast.LENGTH_SHORT).show();
                        collect_btn.setChecked(true);
                    }
                    else{
                        collect_btn.setChecked(false);
                        //Toast.makeText(displaytest2.this,"检测到没有这个收藏",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    //本用户还没有收藏过这个商品
                    collect_btn.setChecked(false);
                }
            }
        });
    }

    public class ViewPagerAdapter extends PagerAdapter {

        List<Map<String,Object>> viewLists;

        public ViewPagerAdapter(List<Map<String,Object>> lists)
        {
            viewLists = lists;
        }

        @Override
        public int getCount() {                                                                 //获得size
            // TODO Auto-generated method stub
            return viewLists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View view, int position, Object object)                       //销毁Item
        {
            ImageView x=(ImageView)viewLists.get(position).get("view");
            x.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ((ViewPager) view).removeView(x);
        }

        @Override
        public Object instantiateItem(View view, int position)                                //实例化Item
        {
            ImageView x=(ImageView)viewLists.get(position).get("view");
            x.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageLoader.displayImage(viewLists.get(position).get("url").toString(), x,options);
            ((ViewPager) view).addView(x, 0);

            return viewLists.get(position).get("view");
        }

    }


}
