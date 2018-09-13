package sd.rtyy.com.example.qiu.drawer_try.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import sd.rtyy.com.example.qiu.drawer_try.Collectors.BaseActivity;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.ZhuYeMian;
import sd.rtyy.com.example.qiu.drawer_try.database.business;
import sd.rtyy.com.example.qiu.drawer_try.global_variable.variable_quantity;
import java.sql.Connection;
import java.sql.SQLException;

import static sd.rtyy.com.example.qiu.drawer_try.R.layout.login;

/**
 * Created by ybc on 2017/1/1.
 */

public class MainActivity extends BaseActivity {
    private EditText mUsername, mPassword;
    public static String username_key,email;
    private CheckBox rememberPass;
    private CheckBox show;
    private String identify;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public void setUsername_key(String username_key) {
        this.username_key = username_key;
        variable_quantity.whoLogin = username_key;
    }

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean isNet;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Bmob.initialize(this,"6f5035abd6ff138e936b06e3149d088d");
        mUsername = (EditText)findViewById(R.id.edit_user);
        mPassword = (EditText)findViewById(R.id.edit_passwd);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        //选择登陆入口
        RadioGroup group = (RadioGroup)findViewById(R.id.identity);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
              //  int radioId = group.getCheckedRadioButtonId();
              //  RadioButton rb = (RadioButton)MainActivity.this.findViewById(radioId);
                RadioButton rb = (RadioButton)group.findViewById(checkedId);
                Log.d(TAG, "onCheckedChanged: "+rb.getText().toString());
                identify = rb.getText().toString();
                variable_quantity.identify = rb.getText().toString();
//                if(rb.getText().toString()=="学生"){
//                    variable_quantity.identify = "学生";
//                    identify = "学生";
//                    variable_quantity.ifStudent = true;
//                    Log.d(TAG, "login: "+variable_quantity.ifStudent);
//                }
//                else{
//                    variable_quantity.identify = "商家";
//                    identify = "商家";
//                    variable_quantity.ifStudent = false;
//                }
            }
        });


        show = (CheckBox)findViewById(R.id.show);
        show.setOnClickListener(new OnclickListenerImp());

        boolean isRemember = pref.getBoolean("remember_password", false);

        if (isRemember) {
            String account = pref.getString("account", "");
            String password = pref.getString("password","");
            mUsername.setText(account);
            mPassword.setText(password);
            rememberPass.setChecked(true);
        }

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

    }
    private class OnclickListenerImp implements View.OnClickListener {

        public void onClick(View v) {
            if(MainActivity.this.show.isChecked()){
            //设置为明文显示
                MainActivity.this.mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
            //设置为秘闻显示
                MainActivity.this.mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }


        }
    }
    public void newUser(View view){
        Intent intent = new Intent(MainActivity.this,register.class);
        startActivity(intent);
    }
    public void newBusiness(View view){
        Intent intent = new Intent(MainActivity.this,newBusiness.class);
        startActivity(intent);
    }

    private static final String TAG = "MainActivity";
    public void login(View view) {
//        Intent intent = new Intent(MainActivity.this, ZhuYeMian.class);
//        startActivity(intent);

        final String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        editor = pref.edit();
        if (rememberPass.isChecked()) {
            editor.putBoolean("remember_password", true);
            editor.putString("account", username);
            editor.putString("password", password);

        } else {
            editor.clear();
        }
        editor.apply();
        if (username.equals("") || password.equals(""))
            return;
        if (variable_quantity.identify.equals("学生")){
            Log.d(TAG, "login: "+variable_quantity.ifStudent);

            Toast.makeText(MainActivity.this, "正在登录", Toast.LENGTH_LONG).show();
            BmobQuery<users> query = new BmobQuery<users>();
            query.addWhereEqualTo("username", username);
            query.addWhereEqualTo("password", password);
            query.findObjects(new FindListener<users>() {
            @Override
            public void done(List<users> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 1) {
                        Toast.makeText(MainActivity.this, "已登录", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, ZhuYeMian.class);
                        startActivity(intent);

                        setUsername_key(username);
                    } else {
//                        Toast.makeText(MainActivity.this,"用户名不存在，请先注册",Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(MainActivity.this,register.class);
//                        startActivity(intent);
                        BmobQuery<users> query = new BmobQuery<users>();
                        query.addWhereEqualTo("username", username);
                        query.findObjects(new FindListener<users>() {
                            @Override
                            public void done(List<users> list, BmobException e) {
                                if (isNet == false) {
                                    Toast.makeText(MainActivity.this, "未连接网络", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                if (list.size() == 1) {
                                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                                    return;
                                } else {
                                    Toast.makeText(MainActivity.this, "用户名不存在，请先注册1", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this, register.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                } else {
                    if (isNet == false) {
                        Toast.makeText(MainActivity.this, "未连接网络", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                }
            }
        });

    }else if(variable_quantity.identify.equals("商家")){
            Log.d(TAG, "identify: "+identify);
            Toast.makeText(MainActivity.this, "正在登录", Toast.LENGTH_LONG).show();
            BmobQuery<business> query = new BmobQuery<business>();
            query.addWhereEqualTo("businessName", username);
            query.addWhereEqualTo("password", password);
            query.findObjects(new FindListener<business>() {
                @Override
                public void done(List<business> list, BmobException e) {
                    if (e == null) {
                        if (list.size() == 1) {
                            Toast.makeText(MainActivity.this, "已登录", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, ZhuYeMian.class);
                            startActivity(intent);
                            finish();
                            setUsername_key(username);
                        } else {
//                        Toast.makeText(MainActivity.this,"用户名不存在，请先注册",Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(MainActivity.this,register.class);
//                        startActivity(intent);
                            BmobQuery<business> query = new BmobQuery<business>();
                            query.addWhereEqualTo("businessName", username);
                            query.findObjects(new FindListener<business>() {
                                @Override
                                public void done(List<business> list, BmobException e) {
                                    if (isNet == false) {
                                        Toast.makeText(MainActivity.this, "未连接网络", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    if (list.size() == 1) {
                                        Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                                        return;
                                    } else {
                                        Toast.makeText(MainActivity.this, "用户名不存在，请先注册2", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, newBusiness.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    } else {
                        if (isNet == false) {
                            Toast.makeText(MainActivity.this, "未连接网络", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Toast.makeText(MainActivity.this, "请选择你的身份", Toast.LENGTH_LONG).show();
        }



//        users loginObj = new users();
//        loginObj.setUsername(username);
//        loginObj.setPassword(password);
//        loginObj.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e==null){
//                    Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
//                }
//                else
//                    Toast.makeText(MainActivity.this,"failure",Toast.LENGTH_SHORT).show();
//
//            }
//        });

//        users users = new users();
//        users.setUsername(username);
//        users.setPassword(password);
//        users.login(new SaveListener<String>() {
//
//            public void done(String s, BmobException e) {
//                if(e==null){
//                    Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
//                }
//                else
//                    Toast.makeText(MainActivity.this,"failure",Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()){
                isNet = true;
            }else {
                isNet = false;
            }
        }
    }

}