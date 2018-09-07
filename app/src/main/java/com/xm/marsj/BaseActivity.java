package com.xm.marsj;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.xm.marsj.util.AppManager;
import com.xm.marsj.util.NetWorkUtils;
/**
 * Created by sj on 2018/5/20.
 */
public abstract class BaseActivity extends Activity {

	protected BaseApplication mApplication;
	protected Handler mHandler;
	protected String TAG;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(this);
		NetWorkUtils.networkStateTips(this);
		mApplication = (BaseApplication) getApplication();
		TAG=this.getLocalClassName();
	}
	    
}
