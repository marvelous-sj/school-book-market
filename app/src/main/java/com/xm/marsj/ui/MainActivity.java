package com.xm.marsj.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.xm.marsj.fragment.ContentFragment;
import com.xm.marsj.util.AppManager;
import com.xm.marsj.util.CommonUtils;
import com.xm.xmlogin.R;
import com.xm.marsj.fragment.MenuFragment;

public class MainActivity extends AppCompatActivity {
    private ContentFragment mContentFragment;
    private MenuFragment mMenuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.getInstance().addActivity(this);
        setDefaultFragment();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentManager fm=getFragmentManager();
                FragmentTransaction transaction=fm.beginTransaction();
                if (tabId == R.id.book_market) {

                    if (mContentFragment!=null){
                        mContentFragment=new ContentFragment();
                    }
                    transaction.replace(R.id.fragment,mContentFragment);
                }
                if (tabId == R.id.my_info) {

                    if (mMenuFragment==null){
                        mMenuFragment=new MenuFragment();
                    }
                    transaction.replace(R.id.fragment,mMenuFragment);
                }
                transaction.commit();
            }

        });




    }
    private void setDefaultFragment() {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        mContentFragment=new ContentFragment();
        transaction.replace(R.id.fragment,mContentFragment);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        CommonUtils.showExitDialog(MainActivity.this);

    }
}
