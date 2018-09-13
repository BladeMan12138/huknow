package sd.rtyy.com.example.qiu.drawer_try.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import sd.rtyy.com.example.qiu.drawer_try.R;
import sd.rtyy.com.example.qiu.drawer_try.adapter.ProvinceAdapter;
import sd.rtyy.com.example.qiu.drawer_try.adapter.SchoolAdapter;
import sd.rtyy.com.example.qiu.drawer_try.config.Config;
import sd.rtyy.com.example.qiu.drawer_try.database.business;
import sd.rtyy.com.example.qiu.drawer_try.entity.Province;
import sd.rtyy.com.example.qiu.drawer_try.entity.ProvinceList;
import sd.rtyy.com.example.qiu.drawer_try.entity.School;
import sd.rtyy.com.example.qiu.drawer_try.entity.SchoolList;
import sd.rtyy.com.example.qiu.drawer_try.utils.GsonRequest;

/**
 * Created by lenovo on 2017/9/20.
 */

public class newBusiness extends Activity {
    private EditText newUsername,newPassword,newPassword2,marketName,Telephone;
    private String username,password,password2,marketname,telephone,usercollege;
    private EditText mSelectSchool;
    private ImageButton back;

    /**
     * popView相关
     **/
    private View parent;
    private ListView mProvinceListView;
    private ListView mSchoolListView;
    private TextView mTitle;
    private PopupWindow mPopWindow;
    /**
     * Volley相关
     **/
    private RequestQueue mRequestQueue;
    /**
     * Adapter相关
     **/
    private ProvinceAdapter mProvinceAdapter;
    private SchoolAdapter mSchoolAdapter;
    private String provinceId;
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.regi_buis);

        newUsername = (EditText)findViewById(R.id.edit_new_user);
        newPassword = (EditText)findViewById(R.id.edit_new_passwd);
        newPassword2 = (EditText)findViewById(R.id.edit_new_passwd2);
        marketName = (EditText)findViewById(R.id.marketName);
        Telephone = (EditText)findViewById(R.id.edit_telephone);
        mSelectSchool = (EditText) findViewById(R.id.select_school);
        initPopView();
        mSelectSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });

        back=(ImageButton)findViewById(R.id.dispaly_backarrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void initPopView() {
        parent = this.getWindow().getDecorView();
        View popView = View.inflate(this, R.layout.view_select_province_list, null);
        mTitle = (TextView) popView.findViewById(R.id.list_title);
        mProvinceListView = (ListView) popView.findViewById(R.id.province);
        mSchoolListView = (ListView) popView.findViewById(R.id.school);
        mProvinceListView.setOnItemClickListener(itemListener);
        mSchoolListView.setOnItemClickListener(itemListener);

        mProvinceAdapter = new ProvinceAdapter(this);
        mProvinceListView.setAdapter(mProvinceAdapter);
        mSchoolAdapter = new SchoolAdapter(this);
        mSchoolListView.setAdapter(mSchoolAdapter);

        int width = getResources().getDisplayMetrics().widthPixels * 3 / 4;
        int height = getResources().getDisplayMetrics().heightPixels * 3 / 5;
        mPopWindow = new PopupWindow(popView, width, height);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.setFocusable(true);
        mPopWindow.setTouchable(true);
        mPopWindow.setOutsideTouchable(true);//允许在外侧点击取消

        loadProvince();

        mPopWindow.setOnDismissListener(listener);
    }
    private void showPopWindow() {
        mPopWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }
    private void loadProvince() {
        mRequestQueue = Volley.newRequestQueue(this);
        GsonRequest<Province> request = new GsonRequest<Province>(Request.Method.POST, Config.PROVINCE_URL,
                Province.class, new Response.Listener<Province>() {
            @Override
            public void onResponse(Province response) {
                if (response.getData() != null && response.getError_code() == 0) {
                    mProvinceAdapter.setList(response.getData());
                    mProvinceAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }, this);
        mRequestQueue.add(request);
    }
    private void loadSchool() {
        mRequestQueue = Volley.newRequestQueue(this);
        GsonRequest<School> request = new GsonRequest<>(Request.Method.POST, Config.SCHOOL_URL + provinceId, School.class,
                new Response.Listener<School>() {
                    @Override
                    public void onResponse(School response) {
                        if (response.getData() != null && response.getError_code() == 0){
                            mSchoolAdapter.setList(response.getData());
                            mSchoolAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }, this);
        mRequestQueue.add(request);
    }
    AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent == mProvinceListView) {
                ProvinceList provinceName = (ProvinceList) mProvinceListView.getItemAtPosition(position);
                provinceId = provinceName.getProvince_id();
                mTitle.setText("选择学校");
                mProvinceListView.setVisibility(View.GONE);
                mSchoolListView.setVisibility(View.VISIBLE);
                loadSchool();
            } else if (parent == mSchoolListView) {
                SchoolList schoolName = (SchoolList) mSchoolListView.getItemAtPosition(position);
                mSelectSchool.setText(schoolName.getSchool_name());
                //Log.d(TAG, "onItemClick: chooseSchool:"+schoolName.getSchool_name());
                mPopWindow.dismiss();
            }
        }
    };

    /**
     * popWindow消失监听事件
     */
    PopupWindow.OnDismissListener listener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            mTitle.setText("选择地区");
            mProvinceListView.setVisibility(View.VISIBLE);
            mSchoolAdapter.setList(new ArrayList<SchoolList>());
            mSchoolAdapter.notifyDataSetChanged();
            mSchoolListView.setVisibility(View.GONE);
        }
    };
    public void busi_register(View view){
        username = newUsername.getText().toString();
        password = newPassword.getText().toString();
        password2 = newPassword2.getText().toString();
        marketname = marketName.getText().toString();
        telephone = Telephone.getText().toString();
        usercollege = mSelectSchool.getText().toString();
        if(username.equals("") || password.equals("") || password2.equals("")){
            Toast.makeText(newBusiness.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            return;}
        if(!password.equals(password2)){
            Toast.makeText(newBusiness.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        if(usercollege.equals("")){
            Toast.makeText(newBusiness.this,"请选择您所在的大学",Toast.LENGTH_SHORT).show();
            return;
        }
        queryData();
    }
    public void queryData(){
        BmobQuery<business> query = new BmobQuery<business>();
        query.addWhereEqualTo("businessName",username);
        query.setLimit(50);
        query.findObjects(new FindListener<business>() {
            @Override
            public void done(List<business> list, BmobException e) {
                if(e==null){
                    Toast.makeText(newBusiness.this,"正在注册",Toast.LENGTH_SHORT).show();
                    // insertData();//写在哪
                    if(list.size()==0){
                        insertData();
                        Intent intent = new Intent(newBusiness.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    }else{
                        Toast.makeText(newBusiness.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else{
                    Log.d(TAG, "done: "+e);
                    Toast.makeText(newBusiness.this,"可注册",Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    private static final String TAG = "newBusiness";
    public void insertData(){
        Log.d(TAG, "Business_insertData: "+username+"  "+password+"  "+marketname+"  "+telephone+"  "+usercollege+"  ");
        business busi = new business();
        busi.setBusinessName(username);
        busi.setPassword(password);
        busi.setMarketName(marketname);
        busi.setTelephone(telephone);
        busi.setBusinessCollege(usercollege);
        busi.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(newBusiness.this,"注册成功",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(newBusiness.this,"注册失败",Toast.LENGTH_SHORT).show();

            }
        });
    }



}
