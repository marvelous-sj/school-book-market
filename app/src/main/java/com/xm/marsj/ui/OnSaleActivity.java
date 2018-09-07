package com.xm.marsj.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.rey.material.widget.EditText;
import com.xm.marsj.bean.Book;
import com.xm.marsj.util.CommonUtils;
import com.xm.marsj.util.StringUtils;
import com.xm.xmlogin.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
/**
 * Created by sj on 2018/5/18.
 */
public class OnSaleActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mPhone;
    EditText mBookName;
    EditText mBookIntroduction;
    Button mSubmit;
    Button mGoBack;
    private File mFile;
    private boolean flag=false;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private ImageView mImageHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onsale);
        mPhone = (EditText) findViewById(R.id.phone);
        mBookName = (EditText) findViewById(R.id.book_name);
        mBookIntroduction = (EditText) findViewById(R.id.book_introduction);
        mSubmit = (Button) findViewById(R.id.submit);
        mGoBack = (Button) findViewById(R.id.goback);
        mImageHeader = (ImageView) findViewById(R.id.image_header);
        final Button selectBtn1 = (Button) findViewById(R.id.choose);
        final Button selectBtn2 = (Button) findViewById(R.id.shanchuan);
        selectBtn1.setOnClickListener(this);
        selectBtn2.setOnClickListener(this);
        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mPhoneStr = mPhone.getText().toString().trim()
                        .replaceAll(" ", "");
                final String mBooknameStr = mBookName.getText().toString().trim();
                final String mBookIntroductionStr = mBookIntroduction.getText().toString().trim();

                if (StringUtils.isBlank(mPhoneStr)) {
                    CommonUtils.showToast(OnSaleActivity.this, "手机号不能为空");
                } else if (!StringUtils.isMobilePhone(mPhoneStr)) {
                    CommonUtils.showToast(OnSaleActivity.this, "手机号输入有误");
                } else if (StringUtils.isBlank(mBooknameStr)) {
                    CommonUtils.showToast(OnSaleActivity.this, "书名不能为空");
                } else if (StringUtils.isBlank(mBookIntroductionStr)) {
                    CommonUtils.showToast(OnSaleActivity.this, "书本介绍不能为空");
                }else if (!flag) {
                    CommonUtils.showToast(OnSaleActivity.this, "图片不能为空");
                } else {
                    CommonUtils.showProgressDialog(OnSaleActivity.this, "正在提交");
                    final BmobFile bmobFile = new BmobFile(mFile);
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Book book = new Book();
                                book.setBookName(mBooknameStr);
                                book.setBookIntroduction(mBookIntroductionStr);
                                book.setPhone(mPhoneStr);
                                book.setPic(bmobFile);
                                book.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String objectId, BmobException e) {
                                        CommonUtils.hideProgressDialog();
                                        if(e==null){
                                            CommonUtils.showToast(OnSaleActivity.this,"提交成功");
                                            startActivity(new Intent(OnSaleActivity.this, MainActivity.class));
                                            OnSaleActivity.this.finish();
                                        }else{
                                            CommonUtils.showToast(OnSaleActivity.this,"提交失败"+e.getMessage()+","+e.getErrorCode());
                                        }
                                    }
                                });
                            }else{
                                CommonUtils.hideProgressDialog();
                                CommonUtils.showToast(OnSaleActivity.this,"提交失败"+e.getMessage()+","+e.getErrorCode());
                            }

                        }

                        @Override
                        public void onProgress(Integer value) {
                            // 返回的上传进度（百分比）
                        }
                    });

                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");//图片
                startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
                break;
            case R.id.shanchuan:
                if (isSdcardExisting()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    mFile = new File(file, System.currentTimeMillis() + ".jpg");
                    Uri uri = ifAndroidSeven(mFile);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                } else {
                    Toast.makeText(v.getContext(), "请插入sd卡", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }
    }

    private Uri ifAndroidSeven(File file) {
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(this, "xyz.marsj.bookmarket", file);
        } else {
            data = Uri.fromFile(file);
        }
        return data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    Uri originalUri=data.getData();//获取图片uri
                    resizeImage(originalUri);
                    break;
                case CAMERA_REQUEST_CODE:
                    Uri uri = ifAndroidSeven(mFile);
                    resizeImage(uri);
                    break;
                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isSdcardExisting() {//推断SD卡是否存在
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    public void resizeImage(Uri uri) {//重塑图片大小
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//能够裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }
    private void showResizeImage(Intent data) {//显示图片
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            try {
                File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                mFile = new File(file, System.currentTimeMillis() + ".jpg");
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(mFile));
                photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
                Drawable drawable = new BitmapDrawable(photo);
                flag=true;
                mImageHeader.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
