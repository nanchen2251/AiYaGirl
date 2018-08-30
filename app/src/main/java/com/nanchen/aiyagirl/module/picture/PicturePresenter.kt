package com.nanchen.aiyagirl.module.picture

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Environment
import com.nanchen.aiyagirl.R
import com.nanchen.aiyagirl.module.picture.PictureContract.Presenter
import com.nanchen.aiyagirl.utils.ToastyUtil
import rx.Observable
import rx.Observable.OnSubscribe
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-24  14:31
 */

class PicturePresenter(private val mContext: Context) : Presenter {

    override fun subscribe() {

    }

    override fun unSubscribe() {

    }

    override fun saveGirl(url: String, bitmap: Bitmap, title: String) {
        Observable.create(OnSubscribe<Bitmap> { subscriber ->
            subscriber.onError(Exception("无法下载到图片！"))
            subscriber.onNext(bitmap)
            subscriber.onCompleted()
        }).flatMap { bitmap1 ->
            val appDir = File(Environment.getExternalStorageDirectory(), mContext.resources.getString(R.string.app_name))
            if (!appDir.exists()) {
                appDir.mkdir()
            }
            val fileName = title.replace('/', '-') + ".jpg"
            val file = File(appDir, fileName)
            try {
                val fos = FileOutputStream(file)
                if (bitmap1 != null) {
                    bitmap1.compress(CompressFormat.JPEG, 100, fos)
                    fos.flush()
                    fos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val uri = Uri.fromFile(file)
            // 通知图库更新
            val scannerIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
            mContext.sendBroadcast(scannerIntent)
            Observable.just(uri)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val appDir = File(Environment.getExternalStorageDirectory(), mContext.resources.getString(R.string.app_name))
                    val msg = String.format("图片已保存至 %s 文件夹", appDir.absoluteFile)
                    ToastyUtil.showSuccess(msg)
                }

    }
}
