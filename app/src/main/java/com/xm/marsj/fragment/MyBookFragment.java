package com.xm.marsj.fragment;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.xm.marsj.bean.Book;
import com.xm.marsj.util.CommonUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
/**
 * Created by sj on 2018/5/24.
 */
public class MyBookFragment extends ContentFragment {

    @Override
    protected void initView() {
        myHandler=new MyHandler(this);
        inflater= LayoutInflater.from(getActivity());


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Book book = adapter.getDateByIndex(position - mListView.getHeaderViewsCount());
               deleteBookInfo(getActivity(),book);

            }
        });
    }

    @Override
    protected void getDate(boolean flag) {
        if(flag){
            count=0;
        }
        callIndex();
        count++;
        BmobQuery<Book> query=new BmobQuery<Book>();
        query.order("-createdAt");// 按照时间降序
        query.addWhereEqualTo("phone", this.getActivity().getIntent().getStringExtra("phone"));
        query.setSkip(Strat);
        query.setLimit(20);
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> list, BmobException e) {
                if (e == null) {
                    Message message = myHandler.obtainMessage(  SUCESS);
                    message.obj = list;
                    myHandler.sendMessage(message);
                } else {
                    Log.e("bmob", "" + e);
                }
            }
        });
    }

    private  void deleteBookInfo(final Context context, final Book book) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title("应用提示")
                .theme(Theme.LIGHT)
                .content("确定要删除'" + book.getBookName() + "'这条信息吗?")
                .positiveText("确定").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        book.delete(new UpdateListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    CommonUtils.showToast(context,"删除成功");
                                    getActivity().finish();
                                }else{
                                    CommonUtils.showToast(context,"删除失败");
                                }
                            }

                        });
                    }
                })
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }
}
