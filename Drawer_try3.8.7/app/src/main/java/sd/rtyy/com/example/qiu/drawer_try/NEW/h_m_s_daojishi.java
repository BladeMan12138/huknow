package sd.rtyy.com.example.qiu.drawer_try.NEW;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;

/**
 * Created by lenovo on 2017/7/25.
 */

public class h_m_s_daojishi extends Activity {
    private TextView tvtime0,tvtime1,tvtime2,tvtime3;
    private TextView bejingtime;
    private  long  time;
    private static final String TAG = "h_m_s_daojishi";
    private static String lastupdateTime;

    long currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_m_s_daojishi);
        tvtime0=(TextView)findViewById(R.id.tvtime0);
        tvtime1=(TextView)findViewById(R.id.tvtime1);
        tvtime2=(TextView) findViewById(R.id.tvtime2);
        tvtime3=(TextView) findViewById(R.id.tvtime3);
        bejingtime = (TextView)findViewById(R.id.Beijingtime);
        //得到上次更新的时间

        new Thread(new Runnable() {
            @Override
            public void run() {
                getNetTime();
            }
        }).start();


//        Log.d(TAG, "nowTime (毫秒): "+currentTime);
//        Log.d(TAG, "lastupdateTime(String)外面1: "+lastupdateTime);
        BmobQuery<Merchandise> query = new BmobQuery<>();
        query.findObjects(new FindListener<Merchandise>() {
            @Override
            public void done(List<Merchandise> list, BmobException e) {
                if(e==null){
                    lastupdateTime = list.get(0).getUpdatedAt();
                    //Log.d(TAG, "lastupdateTime(String)里面: "+lastupdateTime);
                    time = getChazhi(currentTime,getLastUpdateTime(lastupdateTime));
                    handler.postDelayed(runnable, 1000);
                }
                else
                    Log.d(TAG, "done: "+e.toString());
            }
        });
        //Log.d(TAG, "lastupdateTime(String)外面2: "+lastupdateTime);



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
                    bejingtime.setText("当前网络时间为:" + format);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long getChazhi(long now,long lastupdate){
        return (now-lastupdate)/1000;
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


}
