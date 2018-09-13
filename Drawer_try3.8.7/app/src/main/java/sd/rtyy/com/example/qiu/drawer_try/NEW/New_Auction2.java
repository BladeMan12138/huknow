package sd.rtyy.com.example.qiu.drawer_try.NEW;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Auction;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;
import sd.rtyy.com.example.qiu.drawer_try.login.MainActivity;

/**
 * Created by lenovo on 2017/7/30.
 */

public class New_Auction2 extends Activity{
    private String auction_name,tab,auction_description,isUsername;
    private float start_price,money_range;
    private int day,hour,min,auction_goods_id;
    private long timelimit;
    private File file_image1;
    private File file_image2;
    private File file_image3;

    private BmobFile image1;
    private BmobFile image2;
    private BmobFile image3;
    private static final String TAG = "NewSecond";

    private GridView gridView1;                      //网格显示缩略图
    private Button buttonPublish;                    //发布按钮
    private final int IMAGE_OPEN = 1;               //打开图片标记
    private String pathImage;                       //选择图片路径
    private Bitmap bmp;                               //导入临时图片
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;     //适配器  
    private List<String> path = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__auction);

        TextView textView = (TextView) findViewById(R.id.list_text_title);
        textView.setText("发布竞拍");
        ImageButton btn_back = (ImageButton) findViewById(R.id.list_back_arrow);
        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        /* 
         * 防止键盘挡住输入框 
         * 不希望遮挡设置activity属性 android:windowSoftInputMode="adjustPan" 
         * 希望动态调整高度 android:windowSoftInputMode="adjustResize" 
         */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_ADJUST_PAN);
        //锁定屏幕 
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        setContentView(R.layout.activity_new_second2);
        //获取控件对象
        gridView1 = (GridView) findViewById(R.id.gridView1);

        /* 
         * 载入默认图片添加图片加号 
         * 通过适配器实现 
         * SimpleAdapter参数imageItem为数据源 R.layout.griditem_addpic为布局 
         */
        //获取资源图片加号
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic);
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this,
                imageItem, R.layout.griditem_addpic,
                new String[] { "itemImage"}, new int[] { R.id.imageView1});
        /* 
         * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如 
         * map.put("itemImage", R.drawable.img); 
         * 解决方法: 
         *              1.自定义继承BaseAdapter实现 
         *              2.ViewBinder()接口实现 
         *  参考 http://blog.csdn.net/admin_/article/details/7257901 
         */

        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gridView1.setAdapter(simpleAdapter);
        /* 
         * 监听GridView点击事件 
         * 报错:该函数必须抽象方法 故需要手动导入import android.view.View; 
         */
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                if( imageItem.size() == 10) { //第一张为默认图片
                    Toast.makeText(New_Auction2.this, "图片数9张已满", Toast.LENGTH_SHORT).show();
                }
                else if(position == 0) { //点击图片位置为+ 0对应0张图片
                    Toast.makeText(New_Auction2.this, "添加图片", Toast.LENGTH_SHORT).show();
                    //选择图片
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE_OPEN);
                    //通过onResume()刷新数据
                }
                else {
                    dialog(position);
                    //Toast.makeText(MainActivity.this, "点击第"+(position + 1)+" 号图片",
                    //      Toast.LENGTH_SHORT).show();  
                }

            }
        });

        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab_up_2);
        fab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(New_Auction2.this, "正在发布，请稍后", Toast.LENGTH_LONG).show();
                fab2.setEnabled(false);
                auction_name = ((EditText) findViewById(R.id.auction_goodsname)).getText().toString();
                try {
                    start_price = Float.parseFloat(((EditText) findViewById(R.id.auction_startprice)).getText().toString());
                    money_range = Float.parseFloat(((EditText) findViewById(R.id.auction_moneyrange)).getText().toString());
                    day = Integer.parseInt(((EditText) findViewById(R.id.auction_Day)).getText().toString());
                    hour = Integer.parseInt(((EditText) findViewById(R.id.auction_Hour)).getText().toString());
                    min = Integer.parseInt(((EditText) findViewById(R.id.auction_Min)).getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(((EditText) findViewById(R.id.auction_Hour)).getText().toString().equals("")){
                    hour = 0;
                }
                if(((EditText) findViewById(R.id.auction_Min)).getText().toString().equals("")){
                    min = 0;
                }
                tab = ((EditText) findViewById(R.id.auction_tab)).getText().toString();
                auction_description = ((EditText) findViewById(R.id.auction_describe)).getText().toString();
                if (auction_name.equals("") || ((EditText) findViewById(R.id.auction_startprice)).
                        getText().toString().equals("") || ((EditText) findViewById(R.id.auction_moneyrange)).getText().toString().equals("") ||
                        ((EditText) findViewById(R.id.auction_Day)).getText().toString().equals("") ||
                        auction_description.equals("")) {
                    Toast.makeText(New_Auction2.this, "发布出错，请完善发布信息", Toast.LENGTH_LONG).show();
                }else{
                String[] filePath = new String[3];
                if (path.size() == 1) {
                    for (int i = 0; i < 1; i++) {
                        filePath[i] = path.get(i);
                    }
                } else if (path.size() == 2) {
                    for (int i = 0; i < 2; i++) {
                        filePath[i] = path.get(i);
                    }
                } else {
                    for (int i = 0; i < 3; i++) {
                        filePath[i] = path.get(i);
                        //Log.d(TAG, "onClick: filepath  "+path.get(i));
                    }
                }

                file_image1 = new File(filePath[0]);
                image1 = new BmobFile(file_image1);
                if (filePath[1] != null) {
                    file_image2 = new File(filePath[1]);
                    image2 = new BmobFile(file_image2);
                }
                if (filePath[2] != null) {
                    file_image3 = new File(filePath[2]);
                    image3 = new BmobFile(file_image3);
                }
                timelimit = day * 24 * 60 * 60 + hour * 60 * 60 + min * 60;
                isUsername = MainActivity.username_key;
                auction_goods_id = (int) System.currentTimeMillis();

                image1.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            if (image2 != null) {
                                image2.uploadblock(new UploadFileListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            if (image3 != null) {
                                                image3.uploadblock(new UploadFileListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        if (e == null) {
                                                            insertObject(new Auction(auction_name, auction_goods_id,
                                                                    isUsername, start_price, tab, timelimit,
                                                                    start_price, money_range, auction_description,
                                                                    image1, image2, image3, false, "null"));
                                                            fab2.setEnabled(true);
                                                        } else {
                                                            Toast.makeText(New_Auction2.this, "图3上传失败", Toast.LENGTH_SHORT).show();
                                                            // fab2.setEnabled(true);
                                                        }

                                                    }
                                                });
                                            } else {
                                                //传两张图
                                                insertObject(new Auction(auction_name, auction_goods_id,
                                                        isUsername, start_price, tab, timelimit,
                                                        start_price, money_range, auction_description,
                                                        image1, image2, false, "null"));
                                            }//
                                        } else {
                                            Toast.makeText(New_Auction2.this, "图2上传失败", Toast.LENGTH_SHORT).show();
                                            fab2.setEnabled(true);

                                        }

                                    }
                                });
                            } else {
                                //传一张图
                                insertObject(new Auction(auction_name, auction_goods_id,
                                        isUsername, start_price, tab, timelimit,
                                        start_price, money_range, auction_description,
                                        image1, false, "null"));
                            }
                        } else {
                            Toast.makeText(New_Auction2.this, "图1上传失败", Toast.LENGTH_SHORT).show();
                            fab2.setEnabled(true);
                        }

                    }
                });
            }
            }
        });






    }
    private void insertObject(final BmobObject obj){
        obj.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(New_Auction2.this,"上传成功",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(New_Auction2.this,"上传失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //获取图片路径 响应startActivityForResult 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //打开图片
        if(resultCode==RESULT_OK && requestCode==IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                //查询选择图片
                Cursor cursor = getContentResolver().query(
                        uri,
                        new String[] { MediaStore.Images.Media.DATA },
                        null,
                        null,
                        null);
                //返回 没找到选择图片
                if (null == cursor) {
                    return;
                }
                //光标移动至开头 获取图片路径
                cursor.moveToFirst();
                pathImage = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }  //end if 打开图片
    }

    //刷新图片
    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(pathImage)){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize=2;
            options.inTempStorage = new byte[5*1024];
            Bitmap addbmp=BitmapFactory.decodeFile(pathImage,options);
            path.add(pathImage);
            Log.d(TAG, "onResume: pathImage"+pathImage);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);

            imageItem.add(map);
            //Bitmap b = (Bitmap)imageItem.get(0).get("itemImage");

            simpleAdapter = new SimpleAdapter(this,
                    imageItem, R.layout.griditem_addpic,
                    new String[] { "itemImage"}, new int[] { R.id.imageView1});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if(view instanceof ImageView && data instanceof Bitmap){
                        ImageView i = (ImageView)view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView1.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            //刷新后释放防止手机休眠后自动添加
            pathImage = null;
        }
    }
    /* 
     * Dialog对话框提示用户删除操作 
     * position为删除图片位置 
     */
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(New_Auction2.this);
        builder.setMessage("确认移除已添加图片吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }



}
