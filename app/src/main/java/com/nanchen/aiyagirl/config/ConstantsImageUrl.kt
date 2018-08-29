package com.nanchen.aiyagirl.config

import java.util.*

/**
 *
 * 登录到期时间 2018-01-18 需重新登录
 * .bkt.clouddn.com为新创建存储空间后系统默认为用户生成的测试域名，此类测试域名，限总流量，限单 IP 访问频率，限速，仅供测试使用。
 * 单IP每秒限制请求次数10次，大于10次403禁止5秒。
 * 单url限速8Mbps，下载到10MB之后，限速1Mbps。
 *
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  15:06
 */

object ConstantsImageUrl {
    // 电影栏头部的图片
    val ONE_URL_01 = "http://ojyz0c8un.bkt.clouddn.com/one_01.png"

    // 头像
    val IC_AVATAR = "http://ojyz0c8un.bkt.clouddn.com/ic_avatar.png"

    // 过渡图的图片链接
    private const val TRANSITION_URL_01 = "http://ojyz0c8un.bkt.clouddn.com/b_1.jpg"
    private const val TRANSITION_URL_02 = "http://ojyz0c8un.bkt.clouddn.com/b_2.jpg"
    private const val TRANSITION_URL_03 = "http://ojyz0c8un.bkt.clouddn.com/b_3.jpg"
    private const val TRANSITION_URL_04 = "http://ojyz0c8un.bkt.clouddn.com/b_4.jpg"
    private const val TRANSITION_URL_05 = "http://ojyz0c8un.bkt.clouddn.com/b_5.jpg"
    private const val TRANSITION_URL_06 = "http://ojyz0c8un.bkt.clouddn.com/b_6.jpg"
    private const val TRANSITION_URL_07 = "http://ojyz0c8un.bkt.clouddn.com/b_7.jpg"
    private const val TRANSITION_URL_08 = "http://ojyz0c8un.bkt.clouddn.com/b_8.jpg"
    private const val TRANSITION_URL_09 = "http://ojyz0c8un.bkt.clouddn.com/b_9.jpg"
    private const val TRANSITION_URL_10 = "http://ojyz0c8un.bkt.clouddn.com/b_10.jpg"
    val TRANSITION_URLS = arrayOf(TRANSITION_URL_01, TRANSITION_URL_02, TRANSITION_URL_03, TRANSITION_URL_04, TRANSITION_URL_05, TRANSITION_URL_06, TRANSITION_URL_07, TRANSITION_URL_08, TRANSITION_URL_09, TRANSITION_URL_10)

    // 2张图的随机图
    private const val HOME_TWO_01 = "http://ojyz0c8un.bkt.clouddn.com/home_two_01.png"
    private const val HOME_TWO_02 = "http://ojyz0c8un.bkt.clouddn.com/home_two_02.png"
    private const val HOME_TWO_03 = "http://ojyz0c8un.bkt.clouddn.com/home_two_03.png"
    private const val HOME_TWO_04 = "http://ojyz0c8un.bkt.clouddn.com/home_two_04.png"
    private const val HOME_TWO_05 = "http://ojyz0c8un.bkt.clouddn.com/home_two_05.png"
    private const val HOME_TWO_06 = "http://ojyz0c8un.bkt.clouddn.com/home_two_06.png"
    private const val HOME_TWO_07 = "http://ojyz0c8un.bkt.clouddn.com/home_two_07.png"
    private const val HOME_TWO_08 = "http://ojyz0c8un.bkt.clouddn.com/home_two_08.png"
    private const val HOME_TWO_09 = "http://ojyz0c8un.bkt.clouddn.com/home_two_09.png"
    val HOME_TWO_URLS = arrayOf(HOME_TWO_01, HOME_TWO_02, HOME_TWO_03, HOME_TWO_04, HOME_TWO_05, HOME_TWO_06, HOME_TWO_07, HOME_TWO_08, HOME_TWO_09)

    /**
     * 一张图的随机图
     */
    private val HOME_ONE_1 = "http://ojyz0c8un.bkt.clouddn.com/home_one_1.png"

    private var oneList: ArrayList<String>? = null

    private//        DebugUtil.error("oneList == null:   " + (oneList == null));
    val oneUrl: ArrayList<String>
        get() {
            if (oneList == null) {
                synchronized(ArrayList::class.java) {
                    if (oneList == null) {
                        oneList = ArrayList()
                        for (i in 1..12) {
                            oneList!!.add("http://ojyz0c8un.bkt.clouddn.com/home_one_$i.png")
                        }
                        return oneList as ArrayList<String>
                    }
                }
            }
            return oneList as ArrayList<String>
        }

    // 一张图的随机图
    val HOME_ONE_URLS = arrayOf(oneUrl[0], oneUrl[1], oneUrl[2], oneUrl[3], oneUrl[4], oneUrl[5], oneUrl[6], oneUrl[7], oneUrl[8], oneUrl[9], oneUrl[10], oneUrl[11])


    //-----------------------------------------------------------------------------
    // 1 -- 23
    private val HOME_SIX_1 = "http://ojyz0c8un.bkt.clouddn.com/home_six_1.png"
    private var sixList: ArrayList<String>? = null

    private val sixUrl: ArrayList<String>
        get() {
            if (sixList == null) {
                synchronized(ArrayList::class.java) {
                    if (sixList == null) {
                        sixList = ArrayList()
                        for (i in 1..23) {
                            sixList!!.add("http://ojyz0c8un.bkt.clouddn.com/home_six_$i.png")
                        }
                        return sixList as ArrayList<String>
                    }
                }
            }
            return sixList as ArrayList<String>
        }

    // 六图的随机图
    val HOME_SIX_URLS = arrayOf(sixUrl[0], sixUrl[1], sixUrl[2], sixUrl[3], sixUrl[4], sixUrl[5], sixUrl[6], sixUrl[7], sixUrl[8], sixUrl[9], sixUrl[10], sixUrl[11], sixUrl[12], sixUrl[13], sixUrl[14], sixUrl[15], sixUrl[16], sixUrl[17], sixUrl[18], sixUrl[19], sixUrl[20], sixUrl[21], sixUrl[22])
}
