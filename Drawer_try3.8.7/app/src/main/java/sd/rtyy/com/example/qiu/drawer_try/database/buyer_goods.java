package sd.rtyy.com.example.qiu.drawer_try.database;

import cn.bmob.v3.BmobObject;

/**
 * Created by lenovo on 2017/7/20.
 */

public class buyer_goods extends BmobObject {
    String username;
    int goods_ID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGoods_ID() {
        return goods_ID;
    }

    public void setGoods_ID(int goods_ID) {
        this.goods_ID = goods_ID;
    }
}
