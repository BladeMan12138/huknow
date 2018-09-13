package sd.rtyy.com.example.qiu.drawer_try.LeftTitle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
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

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;
import sd.rtyy.com.example.qiu.drawer_try.fragment.buyit;

/**
 * Created by lenovo on 2017/8/27.
 */

public class myPublic_display extends Activity {
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
    private Button deletegoods;
    private String goods_name;
    private String  goods_price;
    private String description;
    private int goods_id;
    private String objectId;
    private TextView price,name,tv_description;


    private static final String TAG = "displaytest2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypublic_item_display);

        back = (ImageButton) findViewById(R.id.dispaly_backarrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        images = new ArrayList<ImageView>();
        url = new ArrayList<String>();
        image1 = new ImageView(this);
        image2 = new ImageView(this);
        image3 = new ImageView(this);

        price = (TextView)findViewById(R.id.display_price);
        name = (TextView)findViewById(R.id.display_goodsname);
        tv_description = (TextView)findViewById(R.id.dispaly_description);

        urll[0] = getIntent().getStringExtra("url1");
        urll[1] = getIntent().getStringExtra("url2");
        urll[2] = getIntent().getStringExtra("url3");

        objectId = getIntent().getStringExtra("objectId");
        goods_name = getIntent().getStringExtra("goods_name");
        description = getIntent().getStringExtra("description");
        goods_price = getIntent().getStringExtra("goods_price");

        price.setText("¥"+goods_price);
        name.setText(goods_name);
        tv_description.setText("商品介绍：   "+description);

        deletegoods = (Button)findViewById(R.id.delete_goods);
        deletegoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(myPublic_display.this);
                dialog.setTitle("是否删除该商品");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Merchandise m = new Merchandise();
                        m.setObjectId(objectId);
                        m.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(myPublic_display.this,"删除成功",Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                }else{
                                    Toast.makeText(myPublic_display.this,"删除失败",Toast.LENGTH_LONG).show();
                                 }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();

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
