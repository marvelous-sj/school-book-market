package com.xm.marsj.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.xm.marsj.bean.Book;
import com.xm.xmlogin.R;
import com.xm.marsj.bean.UserBean;
import com.xm.marsj.ui.LoginActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;



public class CommonUtils {

    protected static Toast toast = null;

    public static MaterialDialog materialDialog;

    public static void deleteBookInfo(final Context context, final Book book) {
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
                                    showToast(context,"删除成功");

                                }else{
                                    showToast(context,"删除失败");
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
    public static void showExitDialog(final Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title("应用提示")
                .theme(Theme.LIGHT)
                .content("确定退出" + context.getResources().getString(R.string.app_name) + "吗?")
                .positiveText("确定").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        AppManager.getInstance().AppExit(context);
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

    public static void showLogoutDialog(final Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title("应用提示")
                .theme(Theme.LIGHT)
                .content("确定退出当前登录账号" + UserBean.getCurrentUser().getUsername() + "吗？")
                .positiveText("确定").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        UserBean.logOut();
                        AppManager.getInstance().killAllActivity();
                        context.startActivity(new Intent(context, LoginActivity.class));
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

    public static void showProgressDialog(Context context, String content) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.close_dialog);
        TextView textView = (TextView) view.findViewById(R.id.dialog_meassage);
        textView.setText(content);
        materialDialog = new MaterialDialog.Builder(context)
                .customView(view,true)
                .cancelable(false)
                .theme(Theme.LIGHT)
                .show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });
    }

    public static void hideProgressDialog() {
        materialDialog.dismiss();
    }


    /**
     * 显示Toast消息
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    /**
     * 获取系统版本号
     *
     * @param context
     * @return
     */
    public static String getSysVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

}
