package sd.rtyy.com.example.qiu.drawer_try.database;

import cn.bmob.v3.BmobObject;

/**
 * Created by lenovo on 2017/9/20.
 */

public class business extends BmobObject{
    String businessName;
    String password;

    String marketName;
    String telephone;
    String businessCollege;

    public String getBusinessCollege() {
        return businessCollege;
    }

    public void setBusinessCollege(String businessCollege) {
        this.businessCollege = businessCollege;
    }


    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
