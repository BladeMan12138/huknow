package sd.rtyy.com.example.qiu.drawer_try.database;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by lenovo on 2017/8/9.
 */

public class Exchange extends BmobObject{
    int goods_Id;
    String goods_name;

    String isUsername;
    int telephone;
    String goodsBelongToC;

    String wantWhich;

    String description;
    Boolean sold;//true:卖出，false:未卖出
    BmobFile image;
    BmobFile image2;
    BmobFile image3;
    public Exchange(String isUsername,String goodsBelongToC,int goods_Id, String goods_name, String wantWhich,String description,
                    Boolean sold,BmobFile image,BmobFile image2, BmobFile image3){
        setIsUsername(isUsername);
        setGoodsBelongToC(goodsBelongToC);
        setGoods_Id(goods_Id);
        setGoods_name(goods_name);

        setWantWhich(wantWhich);
        setDescription(description);
        setSold(sold);
        setImage(image);
        setImage2(image2);
        setImage3(image3);
    }
    public Exchange(){

    }
    public Exchange(String isUsername,String goodsBelongToC,int goods_Id, String goods_name,   String wantWhich,String description,
                    Boolean sold,BmobFile image,BmobFile image2){
        setIsUsername(isUsername);
        setGoodsBelongToC(goodsBelongToC);
        setGoods_Id(goods_Id);
        setGoods_name(goods_name);

        setWantWhich(wantWhich);
        setDescription(description);
        setSold(sold);
        setImage(image);
        setImage2(image2);

    }
    public Exchange(String isUsername,String goodsBelongToC,int goods_Id, String goods_name,  String wantWhich,String description,
                    Boolean sold,BmobFile image){
        setIsUsername(isUsername);
        setGoodsBelongToC(goodsBelongToC);
        setGoods_Id(goods_Id);
        setGoods_name(goods_name);

        setWantWhich(wantWhich);
        setDescription(description);
        setSold(sold);
        setImage(image);

    }

    public int getGoods_Id() {
        return goods_Id;
    }

    public void setGoods_Id(int goods_Id) {
        this.goods_Id = goods_Id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }



    public String getIsUsername() {
        return isUsername;
    }

    public void setIsUsername(String isUsername) {
        this.isUsername = isUsername;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getGoodsBelongToC() {
        return goodsBelongToC;
    }

    public void setGoodsBelongToC(String goodsBelongToC) {
        this.goodsBelongToC = goodsBelongToC;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public BmobFile getImage2() {
        return image2;
    }

    public void setImage2(BmobFile image2) {
        this.image2 = image2;
    }

    public BmobFile getImage3() {
        return image3;
    }

    public void setImage3(BmobFile image3) {
        this.image3 = image3;
    }

    public String getWantWhich() {
        return wantWhich;
    }

    public void setWantWhich(String wantWhich) {
        this.wantWhich = wantWhich;
    }
}

