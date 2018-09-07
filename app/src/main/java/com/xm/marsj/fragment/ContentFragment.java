package com.xm.marsj.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xm.marsj.adapter.BookAdapter;
import com.xm.marsj.bean.Book;
import com.xm.xmlogin.R;
import com.xm.marsj.ui.DeatilActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
/**
 * Created by sj on 2018/5/24.
 */
public  class ContentFragment extends Fragment implements AbsListView.OnScrollListener {
    ListView mListView;
    RelativeLayout loading;
    ArrayList<Book> Books;
    MyHandler myHandler;
    BookAdapter adapter;
    LayoutInflater inflater;
    static final int SUCESS=1;
    static final int STOP_RESH  =2;
    boolean isToEnd=false;
    int Strat=0;
    int count=0;
    FragmentActivity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (FragmentActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_content,container,false);
        mListView= (ListView) view.findViewById(R.id.listView);
        loading = (RelativeLayout) view.findViewById(R.id.loading);
        mListView.setOnScrollListener(this);
        mListView.setEmptyView(loading);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getDate(true);
    }



    protected void initView() {
        myHandler=new MyHandler(this);
        inflater= LayoutInflater.from(getActivity());


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), DeatilActivity.class);
                Book book = adapter.getDateByIndex(position-mListView.getHeaderViewsCount());
                intent.putExtra("book",book);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_in,R.anim.activity_out);

            }
        });

    }

    protected void getDate(final boolean flag) {
        if(flag){
            count=0;
        }
        callIndex();
        count++;
        BmobQuery<Book> query=new BmobQuery<Book>();
        query.order("-createdAt");// 按照时间降序
        query.setSkip(Strat);
        query.setLimit(20);
        query.findObjects(new FindListener<Book>() {
                @Override
                public void done(List<Book> list, BmobException e) {
                    if (e == null) {
                        Message message = myHandler.obtainMessage(SUCESS);
                        message.obj = list;
                        myHandler.sendMessage(message);
                    } else {
                        Log.e("bmob", "" + e);
                    }
                }
            });
        }



    protected void callIndex() {
        if(count==0){
            Strat=0;

        }else{
            Strat=Strat+20;
        }

    }
    public void upDate(List<Book> newDate){
        if(adapter==null){
            Books= new ArrayList<>();
            Books.addAll(newDate);
            adapter=new BookAdapter(Books,mActivity);
            mListView.setAdapter(adapter);
        }else{
            adapter.addDate(newDate);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if(i== SCROLL_STATE_IDLE&&isToEnd){
            getDate(false);
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if(absListView.getLastVisiblePosition()==i2-1){
            isToEnd=true;
        }else{
            isToEnd=false;
        }
    }


    static class MyHandler extends Handler{
        WeakReference<ContentFragment> fragment;
        protected MyHandler(ContentFragment frament){
            this.fragment=new WeakReference(frament);
        }
        @Override
        public void handleMessage(Message msg) {
            ContentFragment hot = fragment.get();
            if(hot==null){
                return;
            }
            switch (msg.what){
                case SUCESS:
                    List<Book> details = (List<Book>) msg.obj;
                    hot.upDate(details);
                    break;
                case STOP_RESH:
                    break;
            }
            super.handleMessage(msg);
        }
    }




}
