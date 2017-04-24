package com.nanchen.aiyagirl.module.picture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.nanchen.aiyagirl.R;
import com.nanchen.aiyagirl.module.picture.PictureContract.Presenter;
import com.nanchen.aiyagirl.utils.ToastyUtil;
import com.nanchen.aiyagirl.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-24  14:31
 */

public class PicturePresenter implements Presenter {

    private Context mContext;

    public PicturePresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void saveGirl(final String url, final int width, final int height, final String title) {
        Observable.create(new OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(Utils.getContext())
                            .load(url)
                            .asBitmap()
                            .into(width, height)
                            .get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                if (bitmap == null) {
                    subscriber.onError(new Exception("无法下载到图片！"));
                }
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<Bitmap, Observable<Uri>>() {
            @SuppressWarnings("ResultOfMethodCallIgnored")
            @Override
            public Observable<Uri> call(Bitmap bitmap) {
                File appDir = new File(Environment.getExternalStorageDirectory(), mContext.getResources().getString(R.string.app_name));
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = title.replace('/', '-') + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    if (bitmap != null) {
                        bitmap.compress(CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.fromFile(file);
                // 通知图库更新
                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                mContext.sendBroadcast(scannerIntent);
                return Observable.just(uri);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        File appDir = new File(Environment.getExternalStorageDirectory(), mContext.getResources().getString(R.string.app_name));
                        String msg = String.format("图片已保存至 %s 文件夹", appDir.getAbsoluteFile());
                        ToastyUtil.showSuccess(msg);
                    }
                });

    }
}
