package sd.rtyy.com.example.qiu.drawer_try.login;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by 文琪 on 2017/7/19.
 */

public class users extends BmobObject{
    String username;
    String password;
    String email;
    String telephone;
    String usersCollege;

    public String getUsersCollege() {
        return usersCollege;
    }

    public void setUsersCollege(String usersCollege) {
        this.usersCollege = usersCollege;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public users(){
        this.setTableName("users");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
