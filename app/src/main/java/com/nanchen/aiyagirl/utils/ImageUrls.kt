package com.nanchen.aiyagirl.utils

import com.nanchen.aiyagirl.model.PicEntity

/**
 * Description:随时图片模拟地址
 * @author: caiyoufei
 * @date: 2019/9/29 18:12
 */
class ImageUrls private constructor() {
    private object SingletonHolder {
        val holder = ImageUrls()
    }

    companion object {
        val instance = SingletonHolder.holder
    }

    //码云
//  val prefix = "https://gitee.com/CASE_CAI/img/raw/master/"
    //gitlab
    private val prefix = "https://gitlab.com/case2578/img/raw/master/"
    //图片分组
    private val imageUrls1 = mutableListOf(
            "${prefix}beimoting.jpg",
            "${prefix}buliangren.jpg",
            "${prefix}chaoshenxueyuan.jpg",
            "${prefix}dilingqu.jpg",
            "${prefix}doupocangqiong.jpg",
            "${prefix}huanshimensheng.jpg",
            "${prefix}lingjianzun.jpg",
            "${prefix}lingzhu.jpg",
            "${prefix}modaozhushi.jpg"
    )
    private val imageUrls2 = mutableListOf(
            "${prefix}qinshimingyue.jpg",
            "${prefix}quanzhigaoshou.jpg",
            "${prefix}tianxingjiuge.jpg",
            "${prefix}wanjianxianzong.jpg",
            "${prefix}wudongqiankun.jpg",
            "${prefix}wugengji.jpg",
            "${prefix}xialan.jpg",
            "${prefix}xingchenbian.jpg",
            "${prefix}xueyinglingzhu.jpg"
    )
    private val imageUrls3 = mutableListOf(
            "${prefix}yaoshenji.jpg",
            "${prefix}yirenzhixia.jpg",
            "${prefix}aladedalu.jpg",
            "${prefix}conglingkaishideyishijie.jpg",
            "${prefix}congqianyouzuolingjianshan.jpg",
            "${prefix}daojianshenyu.jpg",
            "${prefix}dongjinganya.jpg",
            "${prefix}dongjingshishigui.jpg",
            "${prefix}douhunwei.jpg"
    )
    private val imageUrls4 = mutableListOf(
            "${prefix}guanhaice.jpg",
            "${prefix}guanlangaoshou.jpg",
            "${prefix}haizeiwang.jpg",
            "${prefix}heizidelanqiu.jpg",
            "${prefix}henxiyou.jpg",
            "${prefix}jiemoren.jpg",
            "${prefix}jingjidejuren.jpg",
            "${prefix}kuiba.jpg",
            "${prefix}lingyu.jpg"
    )
    private val imageUrls5 = mutableListOf(
            "${prefix}menghuanxiyou.jpg",
            "${prefix}mingzhentankenan.jpg",
            "${prefix}muwangzhiwang.jpg",
            "${prefix}namihexin.jpg",
            "${prefix}qiangshenji.jpg",
            "${prefix}qihun.jpg",
            "${prefix}qilongzhu.jpg",
            "${prefix}quanzhifashi.jpg",
            "${prefix}shaoniangexing.jpg"
    )
    private val imageUrls6 = mutableListOf(
            "${prefix}shaonianjinyiwei.jpg",
            "${prefix}shengdoushixingshi.jpg",
            "${prefix}shengsihuifang.jpg",
            "${prefix}shilaimu.jpg",
            "${prefix}sishen.jpg",
            "${prefix}wangqiuwangzi.jpg",
            "${prefix}wanguxianqiong.jpg",
            "${prefix}wodenitianshenqi.jpg",
            "${prefix}wodetianjienvyou.jpg"
    )
    private val imageUrls7 = mutableListOf(
            "${prefix}xixingji.jpg",
            "${prefix}xuesecangqiong.jpg",
            "${prefix}yaoguaimingdan.jpg",
            "${prefix}yaojingdeweiba.jpg",
            "${prefix}yinzhishoumuren.jpg",
            "${prefix}yongzhedamaoxian.jpg",
            "${prefix}zetianji.jpg",
            "${prefix}zuqiuxiaojiang.jpg",
            "${prefix}silingbianma.jpg"
    )
    private val imageUrls8 = mutableListOf(
            "${prefix}duolaameng.jpg",
            "${prefix}jinglingbaokemeng.jpg",
            "${prefix}lianjinshushi.jpg",
            "${prefix}quanyecha.jpg",
            "${prefix}quanzhilieren.jpg",
            "${prefix}shumabaobei.jpg",
            "${prefix}yinhun.jpg",
            "${prefix}youxiwang.jpg",
            "${prefix}yuzhouzhanjiandahehao.jpg"
    )
    //图片地址组
    val imgs: MutableList<String> = (imageUrls1 +
            imageUrls2 +
            imageUrls3 +
            imageUrls4 +
            imageUrls5 +
            imageUrls6 +
            imageUrls7 +
            imageUrls8).toMutableList()
    //包含尺寸的图片组
    var pics = arrayOf(
            PicEntity(
                    "${prefix}wangjishijian.jpg",
                    width = 343,
                    height = 457
            ),
            PicEntity(
                    "${prefix}menghuanxiyou.jpg",
                    width = 720,
                    height = 1280
            ),
            PicEntity(
                    "${prefix}buliangren.jpg",
                    width = 500,
                    height = 314
            ),
            PicEntity(
                    "${prefix}chaoshenxueyuan.jpg",
                    width = 496,
                    height = 280
            ),
            PicEntity(
                    "${prefix}dilingqu.jpg",
                    width = 500,
                    height = 312
            ),
            PicEntity(
                    "${prefix}doupocangqiong.jpg",
                    width = 575,
                    height = 359
            ),
            PicEntity(
                    "${prefix}huanshimensheng.jpg",
                    width = 640,
                    height = 344
            ),
            PicEntity(
                    "${prefix}lingjianzun.jpg",
                    width = 496,
                    height = 280
            ),
            PicEntity(
                    "${prefix}lingzhu.jpg",
                    width = 419,
                    height = 304
            ),
            PicEntity(
                    "${prefix}modaozhushi.jpg",
                    width = 500,
                    height = 329
            ),
            PicEntity(
                    "${prefix}qinshimingyue.jpg",
                    width = 800,
                    height = 480
            ),
            PicEntity(
                    "${prefix}quanzhigaoshou.jpg",
                    width = 499,
                    height = 323
            ),
            PicEntity(
                    "${prefix}tianxingjiuge.jpg",
                    width = 533,
                    height = 300
            ),
            PicEntity(
                    "${prefix}wanjianxianzong.jpg",
                    width = 533,
                    height = 300
            ),
            PicEntity(
                    "${prefix}wudongqiankun.jpg",
                    width = 533,
                    height = 300
            ),
            PicEntity(
                    "${prefix}wugengji.jpg",
                    width = 800,
                    height = 499
            ),
            PicEntity(
                    "${prefix}xialan.jpg",
                    width = 480,
                    height = 270
            ),
            PicEntity(
                    "${prefix}xingchenbian.jpg",
                    width = 1000,
                    height = 592
            ),
            PicEntity(
                    "${prefix}xueyinglingzhu.jpg",
                    width = 533,
                    height = 300
            ),
            PicEntity(
                    "${prefix}yaoshenji.jpg",
                    width = 1392,
                    height = 870
            ),
            PicEntity(
                    "${prefix}yirenzhixia.jpg",
                    width = 500,
                    height = 311
            ),
            PicEntity(
                    "${prefix}aladedalu.jpg",
                    width = 480,
                    height = 246
            ),
            PicEntity(
                    "${prefix}conglingkaishideyishijie.jpg",
                    width = 574,
                    height = 300
            ),
            PicEntity(
                    "${prefix}congqianyouzuolingjianshan.jpg",
                    width = 500,
                    height = 307
            ),
            PicEntity(
                    "${prefix}daojianshenyu.jpg",
                    width = 500,
                    height = 357
            ),
            PicEntity(
                    "${prefix}dongjinganya.jpg",
                    width = 533,
                    height = 300
            ),
            PicEntity(
                    "${prefix}dongjingshishigui.jpg",
                    width = 533,
                    height = 300
            ),
            PicEntity(
                    "${prefix}douhunwei.jpg",
                    width = 533,
                    height = 300
            )
    )

    //获取随机图片
    fun getRandomImgUrl(random: Int = (Math.random() * imgs.size).toInt()): String {
        return imgs[random % imgs.size]
    }

    //获取随机图片bean
    fun getRandomPic(random: Int = (Math.random() * pics.size).toInt()): PicEntity {
        return pics[random % pics.size]
    }
}