package com.xm.marsj.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xm.marsj.bean.Book;
import com.xm.xmlogin.R;
/**
 * Created by sj on 2018/5/17.
 */
public class DeatilActivity extends AppCompatActivity {
    ImageView mImg;
    TextView mBookIntroduction;
    TextView mBookName;
    Button mGoBack;
    DisplayImageOptions options;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        options= new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        Book book = (Book) getIntent().getSerializableExtra("book");
        mImg= (ImageView) findViewById(R.id.img);
        mBookIntroduction= (TextView) findViewById(R.id.book_introduction);
        mBookName= (TextView) findViewById(R.id.book_name);
        mGoBack= (Button) findViewById(R.id.goback);
        String name = "《" + book.getBookName() + "》";
        mBookName.setText(name);
        String introduction = "简介："+book.getBookIntroduction()+"。" +"\n联系方式：" + book.getPhone();
        mBookIntroduction.setText(introduction);
        if(book.getPic()!=null) {
            ImageLoader.getInstance().displayImage(book.getPic().getFileUrl(), mImg, options);
        }
        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
