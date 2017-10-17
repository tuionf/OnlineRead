package com.example.tuionf.onlineread;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
/**
 * @author tuionf
 * @date 2017/10/16
 * @email 596019286@qq.com
 * @explain
 */
public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
		Log.e("app", "onCreate:======================= " );


		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
			@Override
			public void onCoreInitFinished() {

			}

			@Override
			public void onViewInitFinished(boolean b) {

			}
		};

		//x5内核初始化接口
		QbSdk.initX5Environment(getApplicationContext(),  cb);

//		QbSdk.PreInitCallback callback = new QbSdk.PreInitCallback() {
//			@Override
//			public void onCoreInitFinished() {
//
//			}
//
//			@Override
//			public void onViewInitFinished() {
//				Log.e(TAG, "onViewInitFinished: " );
//			}
//		};
//
//		QbSdk.preInit(getApplicationContext(),callback);
	}

}
