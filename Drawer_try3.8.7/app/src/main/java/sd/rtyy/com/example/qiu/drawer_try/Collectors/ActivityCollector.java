package sd.rtyy.com.example.qiu.drawer_try.Collectors;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 文琪 on 2017/7/23.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAllActs() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }
}

