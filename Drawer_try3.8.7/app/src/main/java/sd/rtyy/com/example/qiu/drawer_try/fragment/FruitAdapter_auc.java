package sd.rtyy.com.example.qiu.drawer_try.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
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

import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Auction;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;
import sd.rtyy.com.example.qiu.drawer_try.toolbar_alpha;

/**
 * Created by lenovo on 2017/7/25.
 */

public class FruitAdapter_auc extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Auction> aAuctionList;
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
        TextView fruitaucPrice;
        TextView fruitDes;
        public ViewHolder (View view) {
            super(view);
            fruitView = view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            fruitaucPrice = (TextView)view.findViewById(R.id.fruit_price);
            fruitDes = (TextView)view.findViewById(R.id.fruit_description);
        }
    }

    public FruitAdapter_auc (List<Auction> datas, Context context, boolean hasMore) {
        // 初始化变量
        this.aAuctionList = datas;
        this.context = context;
        this.hasMore = hasMore;
    }

    /*public FruitAdapter_auc(Context context,List<Auction> auctionlist) {
        aAuctionList = auctionlist;
        this.context = context;
        // getEveryUrl();
    }*/
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
            final FruitAdapter_auc.ViewHolder holder = new ViewHolder(view);
            holder.fruitView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Auction auction = aAuctionList.get(position);
                    //                Toast.makeText(v.getContext(), "you click view " + auction.getAuction_goodsname(),
                    //                        Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), auction_display.class);
                    intent.putExtra("goods_name",auction.getAuction_goodsname());
                    intent.putExtra("isUsername",auction.getAuction_isUsername());
                    intent.putExtra("goods_id",auction.getAuction_goodsId());
                    intent.putExtra("current_price", auction.getCurrent_price());

                    Log.d(TAG, "current_price  "+auction.getCurrent_price());
                    intent.putExtra("timelimit",String.valueOf(auction.getTimeLimit()));
                    intent.putExtra("objectId",auction.getObjectId());
                    //intent.putExtra("auction",auction);
                    intent.putExtra("auction_startPrice",auction.getAuction_startPrice());
                    intent.putExtra("moneyRange",auction.getMoney_range());
//                    Log.d(TAG, "onCreate: timelimit  "+String.valueOf(auction.getTimeLimit()));
//                    Log.d(TAG, "onCreate: lastupdate "+String.valueOf(auction.getUpdatedAt()));

                    intent.putExtra("lastupdata_time",String.valueOf(auction.getUpdatedAt()));
                    intent.putExtra("goods_description",auction.getAuction_description());
                    intent.putExtra("imagepath1",auction.getAuction_photo().getUrl());
                    if(auction.getAuction_photo2()!=null){
                        intent.putExtra("imagepath2",auction.getAuction_photo2().getUrl());
                        if(auction.getAuction_photo3()!=null){
                            intent.putExtra("imagepath3",auction.getAuction_photo3().getUrl());
                        }
                    }

                    v.getContext().startActivity(intent);
                }
            });
            holder.fruitImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Auction auction = aAuctionList.get(position);
                    //                Toast.makeText(v.getContext(), "you click image " + auction.getAuction_goodsname(),
                    //                        Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), auction_display.class);
                    intent.putExtra("goods_name",auction.getAuction_goodsname());
                    intent.putExtra("isUsername",auction.getAuction_isUsername());
                    intent.putExtra("goods_id",auction.getAuction_goodsId());
                    intent.putExtra("current_price",auction.getCurrent_price());

                    Log.d(TAG, "current_price  "+auction.getCurrent_price());
                    intent.putExtra("timelimit",String.valueOf(auction.getTimeLimit()));
                    intent.putExtra("lastupdata_time",String.valueOf(auction.getUpdatedAt()));
                    intent.putExtra("objectId",auction.getObjectId());
                    Log.d(TAG, "onCreate: timelimit  "+String.valueOf(auction.getTimeLimit()));



                    Log.d(TAG, "onCreate: lastupdate "+String.valueOf(auction.getUpdatedAt()));
                    intent.putExtra("auction_startPrice",auction.getAuction_startPrice());
                    intent.putExtra("moneyRange",auction.getMoney_range());
                    intent.putExtra("goods_description",auction.getAuction_description());
                    intent.putExtra("imagepath1",auction.getAuction_photo().getUrl());
                    if(auction.getAuction_photo2()!=null){
                        intent.putExtra("imagepath2",auction.getAuction_photo2().getUrl());
                        if(auction.getAuction_photo3()!=null){
                            intent.putExtra("imagepath3",auction.getAuction_photo3().getUrl());
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
    private static final String TAG = "FruitAdapter_auc";

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
       // Merchandise merchandise = mMerchandiseList.get(position);
        Auction auction = aAuctionList.get(position);
        //Bitmap bitmap = BitmapFactory.decodeFile(merchandise.getImage().toString());
        //Uri url = Uri.parse(merchandise.getImage().getUrl());

        //Uri url = Uri.parse("http://pic51.nipic.com/file/20141025/11284670_091543201000_2.jpg");


        if (holder instanceof ViewHolder){
            Log.d(TAG,"123"+auction.getAuction_photo().getUrl());
            ((ViewHolder) holder).fruitImage.setImageBitmap(GetImageInputStream(auction.getAuction_photo().getUrl()));
            ((ViewHolder) holder).fruitName.setText(auction.getAuction_goodsname());
            ((ViewHolder)holder).fruitaucPrice.setText(String.valueOf(auction.getCurrent_price()));
            ((ViewHolder)holder).fruitDes.setText(auction.getAuction_description());
            Log.d(TAG, "onBindViewHolder: 123position    "+position);
            final RecyclerView.ViewHolder mHolder = holder ;
            Glide. with(context)
                    .load(aAuctionList.get(position).getAuction_photo().getUrl())
                    .asBitmap()
                    .error(R.drawable.img_1)
                    .into(new BitmapImageViewTarget(((ViewHolder) holder).fruitImage) {
                        @Override
                        public void onResourceReady (Bitmap resource , GlideAnimation<? super Bitmap> glideAnimation) {
                            super.onResourceReady(resource , glideAnimation);
                            //提取并设置颜色
//                        Palette.from (resource).generate(new Palette.PaletteAsyncListener() {
//                            public void onGenerated(Palette p) {
//                                int vibrant = p.getLightVibrantColor( 0x000000) ;
//                                mHolder .mTextView .setBackgroundColor(vibrant) ;
//                            }
//                        }) ;
                        }
                    });
        } else {
            ((FootHolder)holder).tips.setVisibility(View.VISIBLE);
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore == true) {
                // 不隐藏footView提示
                fadeTips = false;
                if (aAuctionList.size() > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多
                    ((FootHolder) holder).tips.setText("正在加载更多...");
                }
            } else {
                if (aAuctionList.size() > 0) {
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

    @Override
    public int getItemCount() {
        return aAuctionList.size();
    }

    public boolean isFadeTips() {
        return fadeTips;
    }

    public void updateList(List<Auction> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            aAuctionList.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    public int getRealLastPosition() {
        return aAuctionList.size();
    }

    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }

    public void resetDatas() {
        aAuctionList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }
}
