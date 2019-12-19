package com.mrzang.commonlibrary.phone;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by ejiang on 2018/2/2.
 */
public class PhoneCallUtils {
    //拨打电话
    public static void callPhone(Activity activity, String phoneNum){
        Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:"+phoneNum));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
