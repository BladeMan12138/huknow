package sd.rtyy.com.example.qiu.drawer_try.NEW;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;
//import sd.rtyy.com.example.qiu.drawer_try.database.saler_goods;
import sd.rtyy.com.example.qiu.drawer_try.database.saler_goods;
import sd.rtyy.com.example.qiu.drawer_try.login.MainActivity;

public class NewSecond extends AppCompatActivity {
    private ImageButton img_btn;
    private ImageButton img_btn2;
    private ImageButton img_btn3;
    private Button btn;

    private int goods_id;
    private String goods_name;
    private float price;
    private String isUsername;
    int telephone;
    private String decription;
    private BmobFile pic;
    private BmobFile pic2;
    private BmobFile pic3;
    private File outputImage;
    private File outputImage2;
    private File outputImage3;
    private EditText edit_goodsname,edit_price,edit_description;
    private static final String TAG = "NewSecond";


    String Ppath;
    String fabu_picture_path;
    private byte[] mContent;
    private static final int TAKE_PHOTO = 1;
    private static final int CROP_PHOTO = 2;
    private static final int CHOOSE_PHOTO = 3;


    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    // 创建一个以当前时间为名称的文件
   // File tempFile = new File(Environment.getExternalStorageDirectory()+ "/Postcard",getPhotoFileName());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_second);
//        TextView textView=(TextView)findViewById(R.id.list_text_title);
//        textView.setText("发布物品");

//        saler_goods salergood = new saler_goods();
//        salergood.setUsername(isUsername);
//        salergood.setGoods_id(0);
//        salergood.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e==null){
//                    Toast.makeText(NewSecond.this, "添加一条数据", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(NewSecond.this,"失败",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//        ImageButton btn_back=(ImageButton)findViewById(R.id.list_back_arrow);
//        btn_back.setOnClickListener(new View.OnClickListener() {
//
//
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//
//            }
//        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_up);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_goodsname = (EditText)findViewById(R.id.edit_name);
                edit_price = (EditText)findViewById(R.id.edit_price);
                edit_description = (EditText)findViewById(R.id.edit_describe);

                goods_name = edit_goodsname.getText().toString();
                try{
                    price = Float.parseFloat(edit_price.getText().toString());
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                decription = edit_description.getText().toString();
                isUsername = MainActivity.username_key;
                goods_id = (int)(System.currentTimeMillis()%100);

//                Merchandise mc = new Merchandise();
//
//                mc.setGoods_Id(goods_id);
//                mc.setGoods_name(goods_name);
//                mc.setPrice(price);
//                mc.setDescription(decription);
//
//                mc.save(new SaveListener<String>() {
//                    @Override
//                    public void done(String s, BmobException e) {
//                        if(e==null){
//                            Toast.makeText(NewSecond.this,"插入Merchandise一条数据成功",Toast.LENGTH_LONG).show();
//                        }else{
//                            Toast.makeText(NewSecond.this,"插入Merchandise一条数据失败",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
               // Toast.makeText(NewSecond.this,"第一个图片的url："+pic.getFileUrl(),Toast.LENGTH_SHORT).show();
                final Merchandise mc = new Merchandise();
                if(imageUri!=null){
                pic.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            //Toast.makeText(NewSecond.this,"上传文件成功："+pic.getFileUrl(),Toast.LENGTH_LONG).show();
                            if(imageUri2!=null){
                                pic2.upload(new UploadFileListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            if(imageUri3!=null){
                                                pic3.upload(new UploadFileListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        if(e==null){
                                                            mc.setGoods_Id(goods_id);
                                                            mc.setGoods_name(goods_name);
                                                            mc.setPrice(price);
                                                            mc.setDescription(decription);
                                                            mc.setImage(pic);
                                                            mc.setImage2(pic2);
                                                            mc.setImage3(pic3);
                                                            mc.save(new SaveListener<String>() {
                                                                @Override
                                                                public void done(String s, BmobException e) {
                                                                    if(e==null){
                                                                        Toast.makeText(NewSecond.this,"插入Merchandise(3照片)一条数据成功",Toast.LENGTH_SHORT).show();
                                                                    }else{
                                                                        Toast.makeText(NewSecond.this,"插入Merchandise（3照片）一条数据失败",Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                        }
                                                        else{
                                                            Toast.makeText(NewSecond.this,"第三张照片上传失败",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }

                                            else{//只有两张照片
                                                mc.setGoods_Id(goods_id);
                                                mc.setGoods_name(goods_name);
                                                mc.setPrice(price);
                                                mc.setDescription(decription);
                                                mc.setImage(pic);
                                                mc.setImage2(pic2);
                                                mc.save(new SaveListener<String>() {
                                                    @Override
                                                    public void done(String s, BmobException e) {
                                                        if(e==null){
                                                            Toast.makeText(NewSecond.this,"插入Merchandise(2照片)一条数据成功",Toast.LENGTH_SHORT).show();
                                                        }else{
                                                            Toast.makeText(NewSecond.this,"插入Merchandise(2照片)一条数据失败",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                        else{
                                            Toast.makeText(NewSecond.this,"第二张照片上传失败",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                });

                            }

                            else{//只有一张照片

                            mc.setGoods_Id(goods_id);
                            mc.setGoods_name(goods_name);
                            mc.setPrice(price);
                            mc.setDescription(decription);
                            mc.setImage(pic);
                            mc.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                        Toast.makeText(NewSecond.this,"插入Merchandise（1照片）一条数据成功",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(NewSecond.this,"插入Merchandise（1照片）一条数据失败",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            }
                        }
                        else{
                            Toast.makeText(NewSecond.this,"第一张照片上传失败："+pic.getFileUrl(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });}


                //Log.d(TAG, mc.getObjectId());

                saler_goods salergood = new saler_goods();
                BmobQuery<saler_goods> sg = new BmobQuery<>();
                salergood.setUsername(isUsername);
                salergood.setGoods_id(goods_id);
                salergood.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(NewSecond.this,"插入saler_goods一条数据成功",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(NewSecond.this,"插入saler_goods一条数据失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });




                Toast.makeText(NewSecond.this, "已发布您的商品", Toast.LENGTH_SHORT).show();
            }
        });
        init();
    }

    //初始化控件
    public int photo;
    private void init() {
        img_btn = (ImageButton) findViewById(R.id.imageView1);
        img_btn2 = (ImageButton) findViewById(R.id.imageView2);
        img_btn3 = (ImageButton) findViewById(R.id.imageView3);

//为ImageButton和Button添加监听事件
        img_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                photo = 1;
                Toast.makeText(NewSecond.this, "第一个，photo : "+photo, Toast.LENGTH_SHORT).show();
                ShowDialog(1);
                //Toast.makeText(NewSecond.this, "photo1的 : "+pic.getFileUrl(), Toast.LENGTH_LONG).show();
            }
        });
        img_btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                photo = 2;
                Toast.makeText(NewSecond.this, "第二个，photo : "+photo, Toast.LENGTH_SHORT).show();
                ShowDialog(2);

            }
        });
        img_btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                photo = 3;
                Toast.makeText(NewSecond.this, "第三个，photo : "+photo, Toast.LENGTH_SHORT).show();
                ShowDialog(3);

            }
        });

    }
    public void setOutputImage(int photo){
        if(photo ==1)
            this.outputImage = new File(getExternalCacheDir(),getPhotoFileName());
        else if(photo ==2)
            this.outputImage2 = new File(getExternalCacheDir(),getPhotoFileName());
        else
            this.outputImage3 = new File(getExternalCacheDir(),getPhotoFileName());
    }
    public File getOutputImage(int photo){
        if(photo ==1)
            return outputImage;
        else if(photo==2)
            return outputImage2;
        else
            return outputImage3;

    }
    private Uri getImageUri(int photo){
        if(photo ==1)
            return imageUri;
        else if(photo == 2)
            return imageUri2;
        else
            return imageUri3;
    }
    private ImageButton getImageButton(int photo){
        if(photo == 1)
            return  img_btn;
        else if(photo == 2)
            return img_btn2;
        else
            return img_btn3;
    }
    private BmobFile getBmobfile(int photo){
        if(photo == 1)
            return  pic;
        else if(photo == 2)
            return pic2;
        else
            return pic3;
    }
    private void setBmobfile(int photo){
        if(photo == 1)
            pic = new BmobFile(outputImage);
        else if(photo == 2)
            pic2 = new BmobFile(outputImage2);
        else
            pic3 = new BmobFile(outputImage3);
    }


    //提示对话框方法
    private Uri imageUri;
    private Uri imageUri2;
    private Uri imageUri3;
    public void ShowDialog(final int photo) {
        new AlertDialog.Builder(this)
                .setTitle("选择图片")
                .setPositiveButton("拍照", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //dialog.dismiss();
                        // 调用系统的拍照功能
                        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        // 指定调用相机拍照后照片的储存路径
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                Uri.fromFile(tempFile));
//                        startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
                       // File outputImage = new File(Environment.getExternalStorageDirectory()+ "/Postcard",getPhotoFileName());
                            setOutputImage(photo);


                        try{
                            if(getOutputImage(photo).exists()){
                                getOutputImage(photo).delete();
                            }
                            getOutputImage(photo).createNewFile();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                        if(Build.VERSION.SDK_INT>=24){
                            if(photo==1)
                                imageUri = FileProvider.getUriForFile(NewSecond.this,
                                    "com.example.cameraalbumtest.fileprovide",getOutputImage(photo));
                            else if(photo ==2)
                                imageUri2 = FileProvider.getUriForFile(NewSecond.this,
                                    "com.example.cameraalbumtest.fileprovide",getOutputImage(photo));
                            else
                                imageUri3 = FileProvider.getUriForFile(NewSecond.this,
                                    "com.example.cameraalbumtest.fileprovide",getOutputImage(photo));
                        }else{
                            if(photo==1)
                                imageUri = Uri.fromFile(getOutputImage(photo));
                            else if(photo ==2)
                                imageUri2 = Uri.fromFile(getOutputImage(photo));
                            else
                                imageUri3 = Uri.fromFile(getOutputImage(photo));
                        }
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,getImageUri(photo));
                        startActivityForResult(intent,TAKE_PHOTO);
                    }
                })
                .setNegativeButton("从相册选择", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
//                        dialog.dismiss();
//                        Intent intent = new Intent(Intent.ACTION_PICK, null);
//                        intent.setDataAndType(
//                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                                "image/*");
//                        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                        Intent intent = new Intent("android.intent.action.GET_CONTENT");
                        intent.setType("image/*");
                        startActivityForResult(intent,CHOOSE_PHOTO);
                    }
                }).show();
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        ContentResolver resolver = getContentResolver();

        switch(requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(getImageUri(photo),"image/*");
                    intent.putExtra("scale",true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,getImageUri(photo));
                    try{
                    mContent = readStream(resolver.openInputStream(getImageUri(photo)));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    Bitmap bitmap = getPicFromBytes(mContent,null);
                    Drawable drawable = new BitmapDrawable(bitmap);
                    getImageButton(photo).setBackground(drawable);
                    setBmobfile(photo);
                    //startActivityForResult(intent,CROP_PHOTO);//启动剪裁程序
                }
                break;
            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(getImageUri(photo)));
                        Drawable drawable = new BitmapDrawable(bitmap);
                        getImageButton(photo).setBackground(drawable);

                        //pic = new BmobFile(outputImage);
                        setBmobfile(photo);
                        Log.d(TAG,"photo1的path : "+outputImage.getPath());


                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    //判断手机版本号
                    if(Build.VERSION.SDK_INT >= 19){
                        //4.4及以上用户使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }
                    else{
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }

    }
    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;


    }
    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        if(photo == 1)
            imageUri = data.getData();
        else if (photo==2)
            imageUri2 = data.getData();
        else if(photo == 3)
            imageUri3 = data.getData();
        if(DocumentsContract.isDocumentUri(this,getImageUri(photo))){
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(getImageUri(photo));
            if("com.android.providers.media.documents".equals(getImageUri(photo).getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" +id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }
            else if("com.android.providers.downloads.documents".equals(getImageUri(photo).getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(getImageUri(photo).getScheme())){
            //不是document类型的Uri，则用普通方式处理
            imagePath = getImagePath(getImageUri(photo),null);
        }

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        Drawable drawable = new BitmapDrawable(bitmap);
        if(photo ==1)
            img_btn.setBackground(drawable);
        else if(photo ==2)
            img_btn2.setBackground(drawable);
        else
            img_btn3.setBackground(drawable);
        //pic = new BmobFile(outputImage);
        setBmobfile(photo);
    }
    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        Drawable drawable = new BitmapDrawable(bitmap);
        //img_btn.setBackground(drawable);
        if(photo ==1)
            img_btn.setBackground(drawable);
        else if(photo ==2)
            img_btn2.setBackground(drawable);
        else
            img_btn3.setBackground(drawable);
        //pic = new BmobFile(outputImage);
        setBmobfile(photo);
    }


    private String getImagePath(Uri uri,String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//// TODO Auto-generated method stub
//
//        switch (requestCode) {
//            case PHOTO_REQUEST_TAKEPHOTO://当选择拍照时调用
//               // startPhotoZoom(Uri.fromFile(tempFile), 150);
//                break;
//
//            case PHOTO_REQUEST_GALLERY://当选择从本地获取图片时
////做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
//                if (data != null)
//                   // startPhotoZoom(data.getData(), 150);
//                break;
//
//            case PHOTO_REQUEST_CUT://返回的结果
//                if (data != null)
//                   // setPicToView(data);
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }

//    private void startPhotoZoom(Uri uri, int size) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//// crop为true是设置在开启的intent中设置显示的view可以剪裁
//        intent.putExtra("crop", "true");
//
//// aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//
//// outputX,outputY 是剪裁图片的宽高
//        intent.putExtra("outputX", size);
//        intent.putExtra("outputY", size);
//        intent.putExtra("return-data", true);
//
//        startActivityForResult(intent, PHOTO_REQUEST_CUT);
//    }

    //将进行剪裁后的图片显示到UI界面上
  //  @SuppressWarnings("deprecation")
//
//    private void setPicToView(Intent picdata) {
//        Bundle bundle = picdata.getExtras();
//        if (bundle != null) {
//            Bitmap photo = bundle.getParcelable("data");
//            Drawable drawable = new BitmapDrawable(photo);
//            img_btn.setBackgroundDrawable(drawable);
//
//            SaveImage(photo, Environment.getExternalStorageDirectory().getPath()+"/whoknow");
//            Log.d(TAG, Environment.getExternalStorageDirectory().getPath()+"/whoknow"+"/"+Ppath+".png");
//            fabu_picture_path = Environment.getExternalStorageDirectory().getPath()+"/whoknow"+"/"+Ppath+".png";
//            file = new File(fabu_picture_path);
//            pic = new BmobFile(file);
//
//        }
//    }
//    public void SaveImage(Bitmap bitmap,String path){
//        File file=new File(path);
//        FileOutputStream fileOutputStream=null;
//        //文件夹不存在就创建
//        if(!file.exists()){
//            file.mkdir();
//        }
//        try{
//            Ppath = ""+System.currentTimeMillis();
//            fileOutputStream=new FileOutputStream(path+"/"+Ppath+".png");
//
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
//            fileOutputStream.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() { 
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
}
