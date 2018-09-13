package sd.rtyy.com.example.qiu.drawer_try.Collectors;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * Created by 文琪 on 2017/7/23.
 */

public class FragBaseActivity extends FragmentActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragCollector.removeActivity(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         FragCollector.addActivity(this);
    }
}
