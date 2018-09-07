package com.xm.marsj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.xm.marsj.bean.UserBean;
import com.xm.marsj.util.CommonUtils;
import com.xm.marsj.BaseActivity;
import com.xm.marsj.Constants;
import com.xm.xmlogin.R;

import cn.bmob.v3.BmobUser;
/**
 * Created by sj on 2018/5/26.
 */

public class SplashActivity extends BaseActivity {


    private String currentVersionName;
    private String latestVersionName;
    private String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_splash);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constants.Screen.width = metrics.widthPixels;
        Constants.Screen.height = metrics.heightPixels;
        Constants.Screen.density = metrics.density;

        mHandler = new Handler();

        currentVersionName = CommonUtils.getSysVersionName(SplashActivity.this);

        afterVersionCheck();

    }

    private void afterVersionCheck() {
        UserBean userInfo = BmobUser.getCurrentUser( UserBean.class);
        if (userInfo != null ) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                }
            }, 1000);
        } else {

                // 否则，停留1.5秒进入登陆页面
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        SplashActivity.this.finish();
                    }
                }, 1500);
            }

    }



}
