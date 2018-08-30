package com.nanchen.aiyagirl.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.nanchen.aiyagirl.App
import java.text.SimpleDateFormat
import java.util.*

/**
 * 获取原生资源工具类
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  15:09
 */

class CommonUtil {

    /**
     * 得到年月日的"日"
     */
    private val date: String
        get() {
            val date = Date()
            val dateFm = SimpleDateFormat("dd")
            return dateFm.format(date)
        }

    companion object {
        /**
         * 随机颜色
         */
        fun randomColor(): Int {
            val random = Random()
            val red = random.nextInt(150) + 50//50-199
            val green = random.nextInt(150) + 50//50-199
            val blue = random.nextInt(150) + 50//50-199
            return Color.rgb(red, green, blue)
        }

        /**
         * 获取屏幕px
         *
         * @param context
         * @return 分辨率
         */
        fun getScreenWidthPixels(context: Context): Int {
            val dm = DisplayMetrics()
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
                    .getMetrics(dm)
            return dm.widthPixels
        }

        //	public static void RunOnUiThread(Runnable r) {
        //		CloudReaderApplication.getInstance().getMainLooper().post(r);
        //	}

        fun getDrawable(resid: Int): Drawable {
            return resoure.getDrawable(resid)
        }

        fun getColor(resid: Int): Int {
            return resoure.getColor(resid)
        }

        val resoure: Resources
            get() = App.instance.resources

        fun getStringArray(resid: Int): Array<String> {
            return resoure.getStringArray(resid)
        }

        fun getString(resid: Int): String {
            return resoure.getString(resid)
        }

        fun getDimens(resId: Int): Float {
            return resoure.getDimension(resId)
        }

        fun removeSelfFromParent(child: View?) {

            if (child != null) {

                val parent = child.parent

                if (parent is ViewGroup) {

                    parent.removeView(child)
                }
            }
        }
    }
}
