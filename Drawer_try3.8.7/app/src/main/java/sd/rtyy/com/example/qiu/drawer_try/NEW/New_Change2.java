package sd.rtyy.com.example.qiu.drawer_try.NEW;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import cn.bmob.v3.datatype.BmobFile;
import sd.rtyy.com.example.qiu.drawer_try.R;

/**
 * 这一部分的代码在New_Second2中
 * 复用了一部分
 */

public class New_Change2 extends Activity{
    private int goods_id;
    private String goods_name;
    private float price;
    private String isUsername;
    private String goodsBelongToWhichCollege;
    int telephone;
    private String decription;
    private EditText edit_goodsname,edit_price,edit_description;
    private File file_image1;
    private File file_image2;
    private File file_image3;

    private BmobFile image1;
    private BmobFile image2;
    private BmobFile image3;

    private GridView gridView1;                      //网格显示缩略图
    private Button buttonPublish;                    //发布按钮
    private final int IMAGE_OPEN = 1;               //打开图片标记
    private String pathImage;                       //选择图片路径
    private Bitmap bmp;                               //导入临时图片
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;     //适配器  
    private List<String> path = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_change);
        TextView textView=(TextView)findViewById(R.id.list_text_title);
        textView.setText("发布物品");
        ImageButton btn_back=(ImageButton)findViewById(R.id.list_back_arrow);
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
                    Toast.makeText(New_Change2.this, "图片数9张已满", Toast.LENGTH_SHORT).show();
                }
                else if(position == 0) { //点击图片位置为+ 0对应0张图片
                    Toast.makeText(New_Change2.this, "添加图片", Toast.LENGTH_SHORT).show();
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



    }
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(New_Change2.this);
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
