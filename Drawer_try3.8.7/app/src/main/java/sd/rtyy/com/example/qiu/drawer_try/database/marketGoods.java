package sd.rtyy.com.example.qiu.drawer_try.database;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by lenovo on 2017/9/20.
 */

public class marketGoods extends BmobObject{
    int mg_id;
    String mg_name;
    float mg_price;
    String mg_descripe;
    String isMarket;
    String isCollege;
    int telephone;

    BmobFile image;
    BmobFile image2;
    BmobFile image3;
    public marketGoods(int mg_id,String mg_name,float mg_price,String mg_descripe,String isMarket,String isCollege,
                        BmobFile image, BmobFile image2,BmobFile image3){
        setMg_id(mg_id);
        setMg_name(mg_name);
        setMg_price(mg_price);
        setMg_descripe(mg_descripe);
        setIsMarket(isMarket);
        setIsCollege(isCollege);

        setImage(image);
        setImage2(image2);
        setImage3(image3);
    }
    public marketGoods(int mg_id,String mg_name,float mg_price,String mg_descripe,String isMarket,String isCollege,
                        BmobFile image, BmobFile image2){
        setMg_id(mg_id);
        setMg_name(mg_name);
        setMg_price(mg_price);
        setMg_descripe(mg_descripe);
        setIsMarket(isMarket);
        setIsCollege(isCollege);

        setImage(image);
        setImage2(image2);

    }
    public marketGoods(int mg_id,String mg_name,float mg_price,String mg_descripe,String isMarket,String isCollege,
                        BmobFile image){
        setMg_id(mg_id);
        setMg_name(mg_name);
        setMg_price(mg_price);
        setMg_descripe(mg_descripe);
        setIsMarket(isMarket);
        setIsCollege(isCollege);

        setImage(image);

    }

    public String getIsCollege() {
        return isCollege;
    }

    public void setIsCollege(String isCollege) {
        this.isCollege = isCollege;
    }

    public int getMg_id() {
        return mg_id;
    }

    public void setMg_id(int mg_id) {
        this.mg_id = mg_id;
    }

    public String getMg_name() {
        return mg_name;
    }

    public void setMg_name(String mg_name) {
        this.mg_name = mg_name;
    }

    public float getMg_price() {
        return mg_price;
    }

    public void setMg_price(float mg_price) {
        this.mg_price = mg_price;
    }

    public String getMg_descripe() {
        return mg_descripe;
    }

    public void setMg_descripe(String mg_descripe) {
        this.mg_descripe = mg_descripe;
    }

    public String getIsMarket() {
        return isMarket;
    }

    public void setIsMarket(String isMarket) {
        this.isMarket = isMarket;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
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
}

