package com.example.tuionf.onlineread;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

/**
 * @author tuionf
 * @date 2017/10/16
 * @email 596019286@qq.com
 * @explain
 */

public class loadingDialog extends Dialog {

    public loadingDialog(@NonNull Context context) {
        super(context);
    }

    public loadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected loadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        setCancelable(true);
    }
}
