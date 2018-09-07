package com.xm.marsj.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xm.xmlogin.R;
import com.xm.marsj.fragment.MyBookFragment;
import com.xm.marsj.util.AppManager;
/**
 * Created by sj on 2018/5/18.
 */
public class MyBookActivity extends AppCompatActivity {
    private MyBookFragment mMyBookFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybook);
        AppManager.getInstance().addActivity(this);
        setDefaultFragment();

    }
    private void setDefaultFragment() {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        mMyBookFragment=new MyBookFragment();
        transaction.replace(R.id.fragment,mMyBookFragment);
        transaction.commit();
    }
}
