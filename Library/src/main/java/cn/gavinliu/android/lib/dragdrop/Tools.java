package cn.gavinliu.android.lib.dragdrop;

import android.content.res.Resources;

/**
 * Created by GavinLiu on 2015-05-27
 */
public class Tools {

    public static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
