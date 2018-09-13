package sd.rtyy.com.example.qiu.drawer_try.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import sd.rtyy.com.example.qiu.drawer_try.Left_title_second.LiuYan;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;
import sd.rtyy.com.example.qiu.drawer_try.global_variable.variable_quantity;
import sd.rtyy.com.example.qiu.drawer_try.toolbar_alpha;

import static android.support.v4.app.ActivityCompat.startActivity;


/**
 * Created by 文琪 on 2017/7/11.
 */

public class FruitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Merchandise> mMerchandiseList;
    private Context context;

    private Handler mHandler = new Handler(Looper.getMainLooper());


    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View

    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false;


    Bitmap bitmap;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;
        TextView fruitPrice;
        TextView fruitDescription;
        TextView fruitLaiyuan;
        public ViewHolder (View view) {
            super(view);
            fruitView = view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            fruitPrice = (TextView)view.findViewById(R.id.fruit_price);
            fruitDescription = (TextView)view.findViewById(R.id.fruit_description);
            fruitLaiyuan = (TextView)view.findViewById(R.id.laiyuan);
        }
    }

    public FruitAdapter(List<Merchandise> datas, Context context, boolean hasMore) {
        // 初始化变量
        this.mMerchandiseList = datas;
        this.context = context;
        this.hasMore = hasMore;

    }


   public FruitAdapter(Context context,List<Merchandise> MerchandiseList) {
       mMerchandiseList = MerchandiseList;
       this.context = context;
      // getEveryUrl();
   }
    public static Bitmap GetImageInputStream(String imageurl) {
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


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.fruitView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();

                    Merchandise merchandise = mMerchandiseList.get(position);

    //                Toast.makeText(v.getContext(), "you click view " + merchandise.getGoods_name(),
    //                        Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(v.getContext(), displaytest2.class);
                    Log.d(TAG, "show is username: "+merchandise.getIsUsername());
                    intent.putExtra("isUsername",merchandise.getIsUsername());
                    intent.putExtra("url1",merchandise.getImage().getUrl());
                    intent.putExtra("goods_name",merchandise.getGoods_name());
                    intent.putExtra("goods_price",String.valueOf(merchandise.getPrice()));
                    intent.putExtra("description",merchandise.getDescription());
                    intent.putExtra("goods_id",merchandise.getGoods_Id());
                    intent.putExtra("goodsBelongToC",merchandise.getGoodsBelongToC());
                    intent.putExtra("objectId",merchandise.getObjectId());
                    if(merchandise.getImage2()!=null){
                    intent.putExtra("url2",merchandise.getImage2().getUrl());
                        if(merchandise.getImage3()!=null){
                            intent.putExtra("url3",merchandise.getImage3().getUrl());
                        }
                    }

                    v.getContext().startActivity(intent);
                }
            });
            holder.fruitImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Merchandise merchandise = mMerchandiseList.get(position);

    //                Toast.makeText(v.getContext(), "you click image " + merchandise.getGoods_name(),
    //                        Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(v.getContext(), displaytest2.class);
                    intent.putExtra("isUsername",merchandise.getIsUsername());
                    intent.putExtra("url1",merchandise.getImage().getUrl());
                    intent.putExtra("goods_name",merchandise.getGoods_name());
                    intent.putExtra("goods_price",String.valueOf(merchandise.getPrice()));
                    intent.putExtra("description",merchandise.getDescription());
                    intent .putExtra("goods_id",merchandise.getGoods_Id());
                    intent.putExtra("goodsBelongToC",merchandise.getGoodsBelongToC());
                    intent.putExtra("objectId",merchandise.getObjectId());
                    if(merchandise.getImage2()!=null){
                    intent.putExtra("url2",merchandise.getImage2().getUrl());
                        if(merchandise.getImage3()!=null){
                            intent.putExtra("url3",merchandise.getImage3().getUrl());
                        }

                    }

                    v.getContext().startActivity(intent);

                }
            });
            return holder;
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview, null));
        }
    }
    Handler handler;
    private static final String TAG = "FruitAdapter";

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  int position) {
        Merchandise merchandise = mMerchandiseList.get(position);

        if (holder instanceof ViewHolder) {
            ((ViewHolder)holder).fruitImage.setImageBitmap(GetImageInputStream(merchandise.getImage().getUrl()));
            ((ViewHolder)holder).fruitPrice.setText(String.valueOf(merchandise.getPrice()));
            ((ViewHolder)holder).fruitDescription.setText(merchandise.getDescription());
            ((ViewHolder)holder).fruitLaiyuan.setText(merchandise.getGoodsBelongToC());
            ((ViewHolder)holder).fruitName.setText(mMerchandiseList.get(position).getGoods_name());
            final RecyclerView.ViewHolder mHolder = holder;
            Glide. with(context)
                    .load(mMerchandiseList.get(position).getImage().getUrl())
                    .asBitmap()
                    .error(R.drawable.img_1)

                    .into(new BitmapImageViewTarget(((ViewHolder) holder).fruitImage) {
                        @Override
                        public void onResourceReady (Bitmap resource , GlideAnimation<? super Bitmap> glideAnimation) {
                            super.onResourceReady(resource , glideAnimation);
                        }
                    });
        } else {
            // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore == true) {
                // 不隐藏footView提示
                fadeTips = false;
                if (mMerchandiseList.size() > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多
                    ((FootHolder) holder).tips.setText("正在加载更多...");
                }
            } else {
                if (mMerchandiseList.size() > 0) {
                    // 如果查询数据发现并没有增加时，就显示没有更多数据了
                    ((FootHolder) holder).tips.setText("没有更多数据了");

                    // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 隐藏提示条
                            ((FootHolder) holder).tips.setVisibility(View.GONE);
                            // 将fadeTips设置true
                            fadeTips = true;
                            // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                            hasMore = true;
                        }
                    }, 500);
                }
            }
        }
    }

//    Handler handler=new Handler(){
//        public void handleMessage(android.os.Message msg){
//            if(msg.what==0x123){
//               // image.setImageBitmap(bitmap);
//            }
//        }
//    };



//    static Bitmap loadImageFromUrl(String url){
//        Bitmap bitmap = null;
//        try{
//            InputStream in = new java.net.URL(url).openStream();
//            bitmap = BitmapFactory.decodeStream(in);
//            in.close();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//        return bitmap;
//    }


        // 获取条目数量，之所以要加1是因为增加了一条footView
    @Override
    public int getItemCount() {
        Log.d(TAG, "mMerchandiseList.size"+mMerchandiseList.size());
        return(mMerchandiseList.size()) ;
    }

    public boolean isFadeTips() {
        return fadeTips;
    }

    public void updateList(List<Merchandise> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            mMerchandiseList.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    public int getRealLastPosition() {
        return mMerchandiseList.size();
    }

    /*class NormalHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public NormalHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }*/

    // // 底部footView的ViewHolder，用以缓存findView操作
    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }

    public void resetDatas() {
        mMerchandiseList = new ArrayList<>();

    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() -1 ) {
            return footType;
        } else {
            return normalType;
        }
    }
}

















