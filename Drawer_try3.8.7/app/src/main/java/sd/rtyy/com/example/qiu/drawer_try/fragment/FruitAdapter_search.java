package sd.rtyy.com.example.qiu.drawer_try.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sd.rtyy.com.example.qiu.drawer_try.LeftTitle.myPublic_display;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.database.Exchange;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;


/**
 * Created by 文琪 on 2017/7/11.
 */

public class FruitAdapter_search extends RecyclerView.Adapter<FruitAdapter_search.ViewHolder> {

    private List<Merchandise> mMerchandiseList;
    private Context context;
    private String type;
    private Class whichDisplay;
    private List<Exchange> eExchange;
    private String adapterType;
    Bitmap bitmap;

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
    public FruitAdapter_search( List<Exchange> ExchangeList,Context context){

        eExchange =ExchangeList;
        this.context = context;
        this.whichDisplay = displaytest2.class;
    }
   public FruitAdapter_search(Context context, List<Merchandise> MerchandiseList) {

        mMerchandiseList = MerchandiseList;
        this.context = context;
        this.whichDisplay = displaytest2.class;
       this.type = "displaytest2";
        // getEveryUrl();
    }
    public FruitAdapter_search(Context context, List<Merchandise> MerchandiseList,String type) {

        mMerchandiseList = MerchandiseList;
        this.context = context;
        this.type = type;
        // getEveryUrl();
        if(type.equals("myPublic")){
            this.whichDisplay = myPublic_display.class;
            this.type = "myPublic_display";
        }else{
            this.whichDisplay = displaytest2.class;
        }
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();

                Merchandise merchandise = mMerchandiseList.get(position);

//                Toast.makeText(v.getContext(), "you click view " + merchandise.getGoods_name(),
//                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), whichDisplay);
                //intent.putExtra("whichdisplay",type);
                //intent.putExtra("objectId",merchandise.getObjectId());
                intent.putExtra("url1",merchandise.getImage().getUrl());
                intent.putExtra("goods_name",merchandise.getGoods_name());
                intent.putExtra("goods_price",String.valueOf(merchandise.getPrice()));
                intent.putExtra("description",merchandise.getDescription());
                intent .putExtra("goods_id",merchandise.getGoods_Id());
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
                Intent intent = new Intent(v.getContext(), whichDisplay);
                //
                intent.putExtra("url1",merchandise.getImage().getUrl());
                intent.putExtra("goods_name",merchandise.getGoods_name());
                intent.putExtra("goods_price",String.valueOf(merchandise.getPrice()));
                intent.putExtra("description",merchandise.getDescription());
                intent .putExtra("goods_id",merchandise.getGoods_Id());
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
    }
    Handler handler;
    private static final String TAG = "FruitAdapter";

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Merchandise merchandise = mMerchandiseList.get(position);

        //Bitmap bitmap = BitmapFactory.decodeFile(merchandise.getImage().toString());
        //Uri url = Uri.parse(merchandise.getImage().getUrl());

        //Uri url = Uri.parse("http://pic51.nipic.com/file/20141025/11284670_091543201000_2.jpg");



       // Log.d(TAG,"123"+merchandise.getImage().getUrl());
        holder.fruitImage.setImageBitmap(GetImageInputStream(merchandise.getImage().getUrl()));
        holder.fruitPrice.setText(String.valueOf(merchandise.getPrice()));
        holder.fruitName.setText(merchandise.getGoods_name());
        holder.fruitDescripe.setText(merchandise.getDescription());
            Log.d(TAG, "onBindViewHolder: 123position    "+position);
        final ViewHolder mHolder = holder ;
        Glide. with(context)
                .load(mMerchandiseList.get(position).getImage().getUrl())
                .asBitmap()
                .error(R.drawable.img_1)
                .into(new BitmapImageViewTarget(holder.fruitImage) {
                    @Override
                    public void onResourceReady (Bitmap resource , GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource , glideAnimation);
                    }
                });
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
public void resetDatas() {
    mMerchandiseList = new ArrayList<>();

}
    @Override
    public int getItemCount() {
        return mMerchandiseList.size();
    }
}

















