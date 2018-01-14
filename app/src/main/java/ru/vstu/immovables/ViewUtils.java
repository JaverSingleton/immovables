package ru.vstu.immovables;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

public final class ViewUtils {

    public static boolean pickFocus(@NonNull Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            return true;
        }
        return pickFocus(view);
    }

    public static boolean pickFocus(@NonNull View view) {
        ViewParent parent = view.getParent();
        ViewGroup group;
        while (parent != null) {
            if (parent instanceof ViewGroup) {
                group = (ViewGroup) parent;
                if (group.isFocusable()) {
                    group.requestFocus();
                    return true;
                }
            }
            parent = parent.getParent();
        }
        return false;
    }

}
