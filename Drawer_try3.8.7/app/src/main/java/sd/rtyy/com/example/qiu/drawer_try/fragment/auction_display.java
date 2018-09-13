package sd.rtyy.com.example.qiu.drawer_try.fragment;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Auction;
import sd.rtyy.com.example.qiu.drawer_try.global_variable.variable_quantity;

/**
 * Created by lenovo on 2017/7/30.
 */

public class auction_display extends Activity{
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
    private String [] urll=new String[3];
    private ImageButton back,auction;
    private ToggleButton collect;
    private long time;
    private String goods_name;
    private float  currentprice;
    private String description;
    private String objectId;
    private float moneyRange;
    private float startPrice;
    private int goods_id;
    private String isUsername;
    private TextView price,name,tv_description,startP_text;
    private TextView tvtime0,tvtime1,tvtime2,tvtime3;
    private TextView bejingtime;
    private  long  timelimit;
    private String lastupdata;
    private static final String TAG = "auction_display";
    private static String lastupdateTime;
    private Auction auc;
    private PopupWindow mPopWindow;

    long currentTime;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_display);
        //新添加
        ImageButton btn = (ImageButton) findViewById(R.id.auction_buy);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });



        tvtime0=(TextView)findViewById(R.id.tvtime0);
        tvtime1=(TextView)findViewById(R.id.tvtime1);
        tvtime2=(TextView) findViewById(R.id.tvtime2);
        tvtime3=(TextView) findViewById(R.id.tvtime3);

        startP_text = (TextView)findViewById(R.id.auc_display_startprice);
        price = (TextView)findViewById(R.id.auc_display_currentprice);
        name = (TextView)findViewById(R.id.auc_display_goods_name);
        tv_description = (TextView)findViewById(R.id.auc_dispaly_description);

        back = (ImageButton) findViewById(R.id.dispaly_backarrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        collect = (ToggleButton) findViewById(R.id.collect);
        collect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //收藏
                }else{
                    //取消收藏
                }
            }
        });
        images = new ArrayList<ImageView>();
        image1 = new ImageView(this);
        image2 = new ImageView(this);
        image3 = new ImageView(this);

        urll[0] = getIntent().getStringExtra("imagepath1");
        urll[1] = getIntent().getStringExtra("imagepath2");
        urll[2] = getIntent().getStringExtra("imagepath3");
        //auc = getIntent().getParcelableExtra("auction");

        goods_name = getIntent().getStringExtra("goods_name");
        lastupdata = getIntent().getStringExtra("lastupdata_time");
        isUsername = getIntent().getStringExtra("isUsername");
        description = getIntent().getStringExtra("goods_description");
        objectId = getIntent().getStringExtra("objectId");
        currentprice = getIntent().getFloatExtra("current_price",123);
        startPrice = getIntent().getFloatExtra("auction_startPrice",-1);
        moneyRange = getIntent().getFloatExtra("moneyRange",-1);
        goods_id = getIntent().getIntExtra("goods_id",-1);
        try{
            timelimit = Long.parseLong(getIntent().getStringExtra("timelimit"));
        }catch (Exception e){
            e.printStackTrace();
        }

        //倒计时。。。。
        new Thread(new Runnable() {
            @Override
            public void run() {
                getNetTime();
                Log.d(TAG, "onCreate: currentTime  "+currentTime);
                Log.d(TAG, "onCreate: lastupdate "+lastupdata);
                //do{}while (currentTime!=0);
                long lastup = getLastUpdateTime(lastupdata);
                Log.d(TAG, "onCreate: lastup_long  "+lastup);
                time = getChazhi(currentTime,timelimit,getLastUpdateTime(lastupdata));
                Log.d(TAG, "onCreate: time  "+time);

                    handler.postDelayed(runnable, 1000);

            }
        }).start();



        name.setText("物品名称："+goods_name);
        price.setText("¥"+currentprice);
        tv_description.setText(description);
        startP_text.setText("¥"+startPrice);
        init();

    }
    public void init() {
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        viewPager = (ViewPager) findViewById(R.id.vp2);
        data = getData();
        imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.loading) // 在ImageView加载过程中显示图片
                .showImageForEmptyUri(R.drawable.empty_adress) // image连接地址为空时
                .showImageOnFail(R.drawable.failload) // image加载失败
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

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time--;
            String formatLongToTimeStr = formatLongToTimeStr(time);
            String[] split = formatLongToTimeStr.split("：");
            for (int i = 0; i < split.length; i++) {
//                if(i==0){
//                    tvtime1.setText(split[0]+"小时");
//                }
//                if(i==1){
//                    tvtime2.setText(split[1]+"分钟");
//                }
//                if(i==2){
//                    tvtime3.setText(split[2]+"秒");
//                }

                if(i==0){
                    tvtime0.setText(split[0]+"天");
                }
                if(i==1){
                    tvtime1.setText(split[1]+"时");
                }
                if(i==2){
                    tvtime2.setText(split[2]+"分");
                }
                if(i==3){
                    tvtime3.setText(split[3]+"秒");
                }


            }
            if(time>0){
                handler.postDelayed(this, 1000);
            }
        }
    };
    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(auction_display.this).inflate(R.layout.popuplayout, null);
        mPopWindow = new PopupWindow(contentView,
                ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());



        //设置各个控件的点击响应
        final EditText tv1 = (EditText)contentView.findViewById(R.id.auc_currentP);
        Button tv2 = (Button)contentView.findViewById(R.id.auc_currentYes);

        tv2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //判断是否高于原价
                float f=Float.parseFloat(tv1.getText().toString());
                float g=Float.parseFloat(price.getText().toString().substring(1));
                if (f <= g) {
                    Toast.makeText(v.getContext(), "不能低于原价！",
                            Toast.LENGTH_SHORT).show();

                } else {
                    //修改后台价格
                    Auction auc = new Auction();
                    auc.setValue("whoWants", variable_quantity.whoLogin);
                    auc.setValue("current_price",f);
                    //不变的属性
                    auc.setValue("money_range",moneyRange);
                    auc.setValue("auction_startPrice",startPrice);
                    auc.setValue("timeLimit",timelimit);
                    auc.setValue("auction_goodsId",goods_id);

                    auc.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                //更新成功
                                Toast.makeText(auction_display.this, "更新成功",
                                        Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(auction_display.this, "更新失败",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    //修改前端
                    price.setText(tv1.getText());
                    mPopWindow.dismiss();
                    Toast.makeText(v.getContext(), "您已参与竞拍！ ",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        //显示PopupWindow
        View rootview = LayoutInflater.from(auction_display.this).inflate(R.layout.auction_display, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }


    private long getChazhi(long currenttime,long limittime,long lastupdate){
        //return (currenttime-lastupdate)/1000;
//
//        long t = (limittime*1000 - (currenttime - lastupdate))/1000;
//        if(t>0)
//            return t;
//        else
//            return 0;
        return(lastupdate+limittime*1000-currenttime)/1000;
    }

    private long getLastUpdateTime(String date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date nowDate=null;
        try {

            nowDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nowDate.getTime();
    }
    private void getNetTime() {
        URL url = null;//取得资源对象
        try {
            url = new URL("http://www.baidu.com");
            //url = new URL("http://www.ntsc.ac.cn");//中国科学院国家授时中心
            //url = new URL("http://www.bjtime.cn");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
            currentTime = calendar.getTime().getTime();

            final String format = formatter.format(calendar.getTime());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(MainActivity.this, "当前网络时间为: \n" + format, Toast.LENGTH_SHORT).show();
                    //bejingtime.setText("当前网络时间为:" + format);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  String formatLongToTimeStr(Long l) {
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second =0;

        second = l.intValue() ;
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        if(hour>24){
            day = hour / 24;
            hour = hour % 24;
        }
        String strtime = day+"："+hour+"："+minute+"："+second;
        return strtime;

    }


    public  List<Map<String, Object>> getData() {
        List<Map<String, Object>> mdata = new ArrayList<Map<String, Object>>();
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

        return  mdata;
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
