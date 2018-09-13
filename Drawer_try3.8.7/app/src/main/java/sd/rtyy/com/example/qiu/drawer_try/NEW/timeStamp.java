package sd.rtyy.com.example.qiu.drawer_try.NEW;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;

/**
 * Created by lenovo on 2017/7/24.
 */

public class timeStamp extends Activity{
    TextView textview;
    TextView chazhi;
    TextView date;
    Date timeStamp;
    Date load_time;
    Date current_time;

    private TimeCount time;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timestamp);
        textview = (TextView)findViewById(R.id.time);
        chazhi = (TextView)findViewById(R.id.chazhi);
        date = (TextView)findViewById(R.id.Date);
        Button btn = (Button)findViewById(R.id.shuaxin);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.start();//开始计时
//                BmobQuery<Merchandise> query = new BmobQuery<>();
//                query.findObjects(new FindListener<Merchandise>() {
//                    @Override
//                    public void done(List<Merchandise> list, BmobException e) {
//                        if(e==null){
//                            textview.setText(list.get(0).getUpdatedAt());
//                            date.setText(getDate(list.get(0).getUpdatedAt()));
//                            chazhi.setText(getDateStr(list.get(0).getUpdatedAt(),1));
//
//
//                        }
//                        else
//                            Log.d(TAG, "done: "+e.toString());
//                    }
//                });

            }
        });


    }

    public static String getDateStr(String day,double Num) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
         Date nowDate = null;
        try {

            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //如果需要向后计算日期 -改为+
        Date newDate2 = new Date(nowDate.getTime() - (long)Num * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }
    public static String getDateStr2(Date date,double Num) {

        //如果需要向后计算日期 -改为+
        Date newDate2 = new Date(date.getTime() - (long)Num * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }
    public static String getDate(String day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
            Log.d(TAG, "getDate: 毫秒数"+nowDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //如果需要向后计算日期 -改为+
        Date newDate2 = new Date(nowDate.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }
    //定义一个倒计时内部类
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            date.setText("重新验证");
            //date.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            date.setClickable(false);
            date.setText(millisUntilFinished /1000+"秒");
        }
    }


}
