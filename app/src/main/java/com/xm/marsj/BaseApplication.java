package com.xm.marsj;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import cn.bmob.v3.Bmob;
/**
 * Created by sj on 2018/5/20.
 */
public class BaseApplication extends Application {
	public SharedPreferences mSharedPreferences;
	public static BaseApplication mInstance = null;
	@Override
	public void onCreate() {

		super.onCreate();

		mInstance = this;
		mSharedPreferences = getSharedPreferences(
				Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);

		Bmob.initialize(this, "8ec8771e1e527e6ec4433be30db37536");

	}

}
