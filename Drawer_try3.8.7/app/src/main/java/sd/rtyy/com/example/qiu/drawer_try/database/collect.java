package sd.rtyy.com.example.qiu.drawer_try.database;

import cn.bmob.v3.BmobObject;
import sd.rtyy.com.example.qiu.drawer_try.login.users;

/**
 * Created by lenovo on 2017/8/29.
 */

public class collect extends BmobObject{
    private String username;
    private Merchandise merchandise;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Merchandise getMerchandise_c() {
        return merchandise;
    }

    public void setMerchandise_c(Merchandise merchandise) {
        this.merchandise = merchandise;
    }
}
