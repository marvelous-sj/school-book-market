package com.xm.marsj.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xm.xmlogin.R;
import com.xm.marsj.bean.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sj on 2018/5/26.
 */

public  class BookAdapter extends BaseAdapter {
ArrayList<Book> Books;
    LayoutInflater inflater;
    DisplayImageOptions options;

    public BookAdapter(ArrayList<Book> books, Context context) {
        this.Books = books;
        inflater = LayoutInflater.from(context);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        options= new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return Books.size();
    }

    @Override
    public Object getItem(int i) {
        return Books.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        Book book = Books.get(i);
        if(view==null){
            holder=new ViewHolder();
            view = inflater.inflate(R.layout.item_book, null);
            holder.img= (ImageView) view.findViewById(R.id.img);
            holder.book_introduction= (TextView) view.findViewById(R.id.book_introduction);
            holder.book_name= (TextView) view.findViewById(R.id.book_name);
            holder.contact= (TextView) view.findViewById(R.id.contact);
            view.setTag(holder);

        }else{
            holder = (ViewHolder) view.getTag();

        }
        initView(holder,book);
        return view;
    }
    public void initView(ViewHolder holder,Book book){
        holder.book_introduction.setText("简介："+book.getBookIntroduction());
        holder.book_name.setText("《"+book.getBookName()+"》");
        holder.contact.setText("联系："+book.getPhone());
       if(book.getPic()!=null) {
           ImageLoader.getInstance().displayImage(book.getPic().getFileUrl(), holder.img, options);
       }
    }
public void addDate(List<Book> add){
if(Books==null){
    Books=new ArrayList<>();
}else{
    Books.addAll(add);
    notifyDataSetChanged();
}

}
public Book getDateByIndex(int index){
    Book book = Books.get(index);
    return book;
}
     class ViewHolder{
         ImageView img;
        TextView book_introduction;
        TextView book_name;
        TextView contact;
    }
}
