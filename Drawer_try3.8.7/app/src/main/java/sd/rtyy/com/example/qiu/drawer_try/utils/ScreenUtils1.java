package sd.rtyy.com.example.qiu.drawer_try.utils;

/**
 * Created by lenovo on 2017/8/27.
 */

/**
 * Created by admin on 2017/8/27.
 */



import android.content.Context;
import android.view.WindowManager;

/**
 * 屏幕帮助类
 *
 * @author zhaokaiqiang
 *
 */
public class ScreenUtils1 {

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        return ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getWidth();
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        return ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getHeight();
    }

}
