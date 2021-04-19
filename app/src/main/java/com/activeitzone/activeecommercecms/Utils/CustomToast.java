package com.activeitzone.activeecommercecms.Utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.activeitzone.activeecommercecms.R;

import org.aviran.cookiebar2.CookieBar;

public class CustomToast {
    public static void showToast(Activity activity, String msg, int color){

        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));

        //LinearLayout linearLayout = (LinearLayout) layout.findViewById(R.id.toast_layout_root);

        TextView toastMessage = (TextView) layout.findViewById(R.id.toastMessage);
        //layout.setBackgroundColor(color);
        toastMessage.setText(msg);

        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setView(layout);
        toast.show();

//        CookieBar.dismiss(activity);
//        CookieBar.build(activity)
//                .setTitle(R.string.app_name)
//                .setMessage(msg)
//                .setBackgroundColor(color)
//                .setAnimationIn(R.anim.slide_in_top, R.anim.slide_in_top)
//                .setAnimationOut(R.anim.slide_out_top, R.anim.slide_out_top)
//                .setDuration(1000)
//                .show();
    }
}
