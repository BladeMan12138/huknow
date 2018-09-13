package sd.rtyy.com.example.qiu.drawer_try;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import sd.rtyy.com.example.qiu.drawer_try.database.Merchandise;
import sd.rtyy.com.example.qiu.drawer_try.fragment.FruitAdapter_search;

public class SearchInSecondMarket extends Activity {

    private static final String TAG = "SearchInSecondMarket";

    private EditText search;

    private List<Merchandise> mList;

    private  String str;
    Button btn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_in_second_market2);
        //Toast.makeText(SearchInSecondMarket.this, "点击", Toast.LENGTH_SHORT).show();
        Button btn_search = (Button) findViewById(R.id.search_button);
        search = (EditText) findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                str = search.getText().toString();
                mList = new LinkedList<>();
                //Toast.makeText(SearchInSecondMarket.this, "点击", Toast.LENGTH_SHORT).show();
                //Log.e(TAG, "done: mawenqi" + str);
                BmobQuery<Merchandise> query = new BmobQuery<Merchandise>();
                    //query.addWhereEqualTo("goods_name", str);
                query.findObjects(new FindListener<Merchandise>() {
                    @Override
                    public void done(List<Merchandise> list, BmobException e) {
                        if (e == null) {
                            Log.d(TAG, "done: mawenqi" + list.size());
                            for (int i = 0; i < list.size(); i++){
                                if (list.get(i).getGoods_name().contains(str)){
                                    mList.add(list.get(i));
                                }
                            }
                            //Log.d(TAG, "done: mawenqi" + list.get(0).getGoods_name());
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getParent());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(new FruitAdapter_search(getBaseContext(), mList));
                        } else {
                            Log.d(TAG, "done: mawenqi:chucuole");
                        }
                    }
                });

            }
        });


        /*.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText search = (EditText) findViewById(R.id.search);
                str = search.getText().toString();
                mList = new LinkedList<>();
                Toast.makeText(SearchInSecondMarket.this, "点击", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "done: mawenqi" + str);
                BmobQuery<Merchandise> query = new BmobQuery<Merchandise>();
                //query.addWhereEqualTo("goods_name", str);
                query.findObjects(new FindListener<Merchandise>() {
                    @Override
                    public void done(List<Merchandise> list, BmobException e) {
                        if (e == null) {
                            Log.d(TAG, "done: mawenqi" + list.size());
                            for (int i = 0; i < list.size(); i++){
                                if (list.get(i).getGoods_name().contains(str)){
                                    mList.add(list.get(i));
                                }
                            }
                            Log.d(TAG, "done: mawenqi" + list.get(0).getGoods_name());
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getParent());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(new FruitAdapter(getBaseContext(), mList));
                        } else {
                            Log.d(TAG, "done: mawenqi:chucuole");
                        }
                    }
                });
            }
        });*/

    }



}
