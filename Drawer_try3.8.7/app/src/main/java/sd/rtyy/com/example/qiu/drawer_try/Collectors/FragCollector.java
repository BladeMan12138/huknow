package sd.rtyy.com.example.qiu.drawer_try.Collectors;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 文琪 on 2017/7/23.
 */

public class FragCollector {
    private static final String TAG = "FragCollector";

    public static List<FragmentActivity> activities = new ArrayList<>();

    public static void addActivity(FragmentActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(FragmentActivity activity){
        activities.remove(activity);
    }

    public static void finishAllFrags(){
        for (FragmentActivity activity : activities) {
            if (!activity.isFinishing()) {
                Log.d(TAG, "finishAllFrags:mawenqi " + activity.toString());
                activity.finish();
            }
        }
        activities.clear();
    }

}
