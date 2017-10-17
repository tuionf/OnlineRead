package com.example.tuionf.onlineread;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

/**
 * @author tuionf
 * @date 2017/10/16
 * @email 596019286@qq.com
 * @explain
 */

public class FileView extends FrameLayout implements TbsReaderView.ReaderCallback {

    private static final String TAG = "FileView";
    private TbsReaderView mTbsReaderView;
    private Context context;

    public FileView(@NonNull Context context) {
        this(context, null, 0);
    }

    public FileView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FileView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTbsReaderView = new TbsReaderView(context,this);
        this.addView(mTbsReaderView,new LinearLayout.LayoutParams(-1,-1));
        this.context = context;
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        Log.e(TAG, "onCallBackAction: "+integer );
    }

    public void showFile(String path){
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            //TODO  可以加入是不是支持的文件的判断
            if (this.mTbsReaderView == null){
                mTbsReaderView = new TbsReaderView(context,this);
            }
            Bundle bundle = new Bundle();
            bundle.putString("filePath", file.toString());
            bundle.putString("tempPath", Environment.getExternalStorageDirectory() + "/" + "Temp");
            boolean bool = this.mTbsReaderView.preOpen(getFileType(file.toString()), false);
            if (bool){
                mTbsReaderView.openFile(bundle);
            }
        }else {
            Toast.makeText(context, "无效的文件路径", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileType(String s) {
        String str = "";
        int i = s.lastIndexOf('.');
        Log.e(TAG, "getFileType: "+i );
        if (i <= -1) {
            return str;
        }
        str = s.substring(i + 1);
        return str;
    }

    public void onStopDisplay() {
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }
}
