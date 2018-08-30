package com.nanchen.aiyagirl.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.service.quicksettings.TileService
import java.net.URISyntaxException

/**
 * 一个轻量的支付宝转账工具类
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-20  15:54
 */

object AlipayZeroSdk {
    // 支付宝包名
    private const val ALIPAY_PACKAGE_NAME = "com.eg.android.AlipayGphone"

    // 旧版支付宝二维码通用 Intent Scheme Url 格式
    private const val INTENT_URL_FORMAT = "intent://platformapi/startapp?saId=10000007&" +
            "clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{urlCode}%3F_s" +
            "%3Dweb-other&_t=1472443966571#Intent;" +
            "scheme=alipayqr;package=com.eg.android.AlipayGphone;end"

    /**
     * 打开转账窗口
     * 旧版支付宝二维码方法，需要使用 https://fama.alipay.com/qrcode/index.htm 网站生成的二维码
     * 这个方法最好，但在 2016 年 8 月发现新用户可能无法使用
     *
     * @param activity Parent Activity
     * @param urlCode 手动解析二维码获得地址中的参数，例如 https://qr.alipay.com/aehvyvf4taua18zo6e 最后那段
     * @return 是否成功调用
     */
    fun startAlipayClient(activity: Activity, urlCode: String): Boolean {
        return startIntentUrl(activity, INTENT_URL_FORMAT.replace("{urlCode}", urlCode))
    }

    /**
     * 打开 Intent Scheme Url
     *
     * @param activity Parent Activity
     * @param intentFullUrl Intent 跳转地址
     * @return 是否成功调用
     */
    fun startIntentUrl(activity: Activity, intentFullUrl: String): Boolean {
        return try {
            val intent = Intent.parseUri(
                    intentFullUrl,
                    Intent.URI_INTENT_SCHEME
            )
            activity.startActivity(intent)
            true
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            false
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            false
        }

    }

    /**
     * 判断支付宝客户端是否已安装，建议调用转账前检查
     * @param context Context
     * @return 支付宝客户端是否已安装
     */
    fun hasInstalledAlipayClient(context: Context): Boolean {
        val pm = context.packageManager
        return try {
            val info = pm.getPackageInfo(ALIPAY_PACKAGE_NAME, 0)
            info != null
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }

    }

    /**
     * 获取支付宝客户端版本名称，作用不大
     * @param context Context
     * @return 版本名称
     */
    fun getAlipayClientVersion(context: Context): String? {
        val pm = context.packageManager
        return try {
            val info = pm.getPackageInfo(ALIPAY_PACKAGE_NAME, 0)
            info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }

    }

    /**
     * 打开支付宝扫一扫界面
     * @param context Context
     * @return 是否成功打开 Activity
     */
    fun openAlipayScan(context: Context): Boolean {
        return try {
            val uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if ((context is TileService)) {
                    context.startActivityAndCollapse(intent)
                }
            } else {
                context.startActivity(intent)
            }
            true
        } catch (e: Exception) {
            false
        }

    }

    /**
     * 打开支付宝付款码
     * @param context Context
     * @return 是否成功打开 Activity
     */
    fun openAlipayBarcode(context: Context): Boolean {
        return try {
            val uri = Uri.parse("alipayqr://platformapi/startapp?saId=20000056")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (context is TileService) {
                    context.startActivityAndCollapse(intent)
                }
            } else {
                context.startActivity(intent)
            }
            true
        } catch (e: Exception) {
            false
        }

    }
}
