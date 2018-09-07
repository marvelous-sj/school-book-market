package com.xm.marsj.fragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xm.marsj.bean.UserBean;
import com.xm.marsj.ui.MyBookActivity;
import com.xm.marsj.ui.OnSaleActivity;
import com.xm.marsj.util.CommonUtils;
import com.xm.xmlogin.R;
/**
 * Created by sj on 2018/5/24.
 */
public class MenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_menu,container,false);
        TextView top = (TextView) root.findViewById(R.id.top);
        top.setText("欢迎"+ UserBean.getCurrentUser().getUsername());

        root.findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.showLogoutDialog(view.getContext());
            }
        });
        root.findViewById(R.id.my_goods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), MyBookActivity.class);
                intent.putExtra("phone",UserBean.getCurrentUser().getUsername());
                startActivity(intent);
            }
        });
        root.findViewById(R.id.on_sale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), OnSaleActivity.class);
                intent.putExtra("phone",UserBean.getCurrentUser().getUsername());
                startActivity(intent);
            }
        });
        return root;
    }
}
