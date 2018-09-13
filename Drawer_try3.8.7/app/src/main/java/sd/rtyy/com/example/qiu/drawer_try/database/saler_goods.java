package sd.rtyy.com.example.qiu.drawer_try.database;

import cn.bmob.v3.BmobObject;

/**
 * Created by lenovo on 2017/7/20.
 */

public class saler_goods extends BmobObject {
    String username;
    int user_ID;
    int goods_id;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
