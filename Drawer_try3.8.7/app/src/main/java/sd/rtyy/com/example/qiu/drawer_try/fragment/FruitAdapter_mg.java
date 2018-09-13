package sd.rtyy.com.example.qiu.drawer_try.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Exchange;
import sd.rtyy.com.example.qiu.drawer_try.database.marketGoods;

/**
 * Created by lenovo on 2017/9/21.
 */

public class FruitAdapter_mg extends RecyclerView.Adapter<FruitAdapter_mg.ViewHolder>{
    private List<marketGoods> mMarketGoodsList;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;
        TextView fruitPrice;
        TextView fruitDescripe;
        public ViewHolder (View view) {
            super(view);
            fruitView = view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            fruitPrice = (TextView)view.findViewById(R.id.fruit_price);
            fruitDescripe = (TextView)view.findViewById(R.id.fruit_description);
        }
    }
    public FruitAdapter_mg(Context context,List<marketGoods> marketGoodsList) {
        mMarketGoodsList = marketGoodsList;
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

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        final FruitAdapter_mg.ViewHolder holder = new FruitAdapter_mg.ViewHolder(view);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                marketGoods marketgoods = mMarketGoodsList.get(position);
                Intent intent = new Intent(v.getContext(), displaytest2.class);
                intent.putExtra("url1", marketgoods.getImage().getUrl());
                intent.putExtra("goods_name", marketgoods.getMg_name());
                intent.putExtra("goods_price",String.valueOf(marketgoods.getMg_price()));
                intent.putExtra("description", marketgoods.getMg_descripe());
                intent.putExtra("goods_id", marketgoods.getMg_id());
                intent.putExtra("objectId", marketgoods.getObjectId());
                if (marketgoods.getImage2() != null) {
                    intent.putExtra("url2", marketgoods.getImage2().getUrl());
                    if (marketgoods.getImage3() != null) {
                        intent.putExtra("url3", marketgoods.getImage3().getUrl());
                    }
                }
                v.getContext().startActivity(intent);
            }
        });
        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                marketGoods marketgoods = mMarketGoodsList.get(position);
                Intent intent = new Intent(v.getContext(), displaytest2.class);
                intent.putExtra("url1", marketgoods.getImage().getUrl());
                intent.putExtra("goods_name", marketgoods.getMg_name());
                //intent.putExtra("goods_price",String.valueOf(exchange.getPrice()));
                intent.putExtra("goods_price",String.valueOf(marketgoods.getMg_price()));
                intent.putExtra("description", marketgoods.getMg_descripe());
                intent.putExtra("goods_id", marketgoods.getMg_id());
                intent.putExtra("objectId", marketgoods.getObjectId());
                if (marketgoods.getImage2() != null) {
                    intent.putExtra("url2", marketgoods.getImage2().getUrl());
                    if (marketgoods.getImage3() != null) {
                        intent.putExtra("url3", marketgoods.getImage3().getUrl());
                    }
                }
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        marketGoods marketgoods = mMarketGoodsList.get(position);

        //Bitmap bitmap = BitmapFactory.decodeFile(merchandise.getImage().toString());
        //Uri url = Uri.parse(merchandise.getImage().getUrl());

        //Uri url = Uri.parse("http://pic51.nipic.com/file/20141025/11284670_091543201000_2.jpg");



        // Log.d(TAG,"123"+merchandise.getImage().getUrl());
        holder.fruitImage.setImageBitmap(GetImageInputStream(marketgoods.getImage().getUrl()));
        //holder.fruitPrice.setText(String.valueOf(exchange.getPrice()));
        holder.fruitPrice.setText(String.valueOf(marketgoods.getMg_price()));
        holder.fruitDescripe.setText(marketgoods.getMg_name());
        holder.fruitName.setText(marketgoods.getMg_name());
        //Log.d(TAG, "onBindViewHolder: 123position    "+position);
        final FruitAdapter_mg.ViewHolder mHolder = holder ;
        Glide. with(context)
                .load(mMarketGoodsList.get(position).getImage().getUrl())
                .asBitmap()
                .error(R.drawable.img_1)
                .into(new BitmapImageViewTarget(holder.fruitImage) {
                    @Override
                    public void onResourceReady (Bitmap resource , GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource , glideAnimation);
                    }
                });
    }
    public void resetDatas() {
        mMarketGoodsList = new ArrayList<>();

    }
    @Override
    public int getItemCount() {
        return mMarketGoodsList.size();
    }
}

