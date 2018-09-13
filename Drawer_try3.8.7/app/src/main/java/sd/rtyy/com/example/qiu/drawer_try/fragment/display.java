package sd.rtyy.com.example.qiu.drawer_try.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;

/**
 * Created by lenovo on 2017/7/27.
 *                                    这个类没有用到，转移到dispalytest2
 */

public class display extends Activity{
    private static final String TAG = "display";
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;
    private Bitmap bitmap1,bitmap2,bitmap3;
    private ImageView image1,image2,image3;
    private String [] url = new String[3];
    private ImageButton back;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.book,
            R.drawable.kaxioucamera,
            R.drawable.kin,
            R.drawable.samsung,
            R.drawable.pingfan
    };
    //存放图片的标题

    private List<Merchandise> mMerchandiseList;
    private Context context;

    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.display);
        back = (ImageButton) findViewById(R.id.dispaly_backarrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final int position = getIntent().getIntExtra("position",0);
        Log.d(TAG, "display: position: "+ position);


        mViewPaper = (ViewPager) findViewById(R.id.vp);
        //显示的图片
        images = new ArrayList<ImageView>();

//
        context = this;
        BmobQuery<Merchandise> query = new BmobQuery<>();
        query.findObjects(new FindListener<Merchandise>() {
            @Override
            public void done(List<Merchandise> list, BmobException e) {

                Log.d(TAG, "done: "+list.get(position).getImage().getUrl());
                Glide. with(context)
                        .load(list.get(position).getImage().getUrl())
                        .asBitmap()
                        .error(R.drawable.img_1)
                        .into(image1);
                Glide. with(context)
                        .load(list.get(position).getImage2().getUrl())
                        .asBitmap()
                        .error(R.drawable.img_1)
                        .into(image2);
                Glide. with(context)
                        .load(list.get(position).getImage3().getUrl())
                        .asBitmap()
                        .error(R.drawable.img_1)
                        .into(image3);
                images.add(image1);
                images.add(image2);
                images.add(image3);



                    }
        });






        //显示的小点
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
//        dots.add(findViewById(R.id.dot_3));
//        dots.add(findViewById(R.id.dot_4));


//        if(images.get(0)!=null&& images.get(1)!=null&&images.get(2)!=null){
            adapter = new ViewPagerAdapter();
            mViewPaper.setAdapter(adapter);

            mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


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

    public Bitmap GetImageInputStream(String imageurl){
        URL url;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try{
            url = new URL(imageurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            InputStream inputStream = connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;

    }
    /**
     * 自定义Adapter
     * @author liuyazhuang
     *
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//			super.destroyItem(container, position, object);
//			view.removeView(view.getChildAt(position));
//			view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            if(images!=null){
            view.addView(images.get(position));}
            return images.get(position);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
