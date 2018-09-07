package com.xm.marsj.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Book extends BmobObject {
    private String phone;
    private String bookName;
    private String bookIntroduction;
    private BmobFile pic;
    private transient Integer count;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String playerName) {
        this.phone = playerName;
    }


    public BmobFile getPic() {
        return pic;
    }

    public void setPic(BmobFile pic) {
        this.pic = pic;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookIntroduction() {
        return bookIntroduction;
    }

    public void setBookIntroduction(String bookIntroduction) {
        this.bookIntroduction = bookIntroduction;
    }

    @Override
    public String toString() {
        return "bookName"+bookName;
    }
}
