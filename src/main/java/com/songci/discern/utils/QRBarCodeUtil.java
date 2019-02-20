package com.songci.discern.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.songci.discern.model.ResultVo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;


/**
 * Created By slx on 2019/2/20
 * 二维码、条形码工具类
 * @author songci
 */
public class QRBarCodeUtil {

    /**
     * CODE_WIDTH：二维码宽度，单位像素
     * CODE_HEIGHT：二维码高度，单位像素
     * FRONT_COLOR：二维码前景色，0x000000 表示黑色
     * BACKGROUND_COLOR：二维码背景色，0xFFFFFF 表示白色
     * 演示用 16 进制表示，和前端页面 CSS 的取色是一样的，注意前后景颜色应该对比明显，如常见的黑白
     */
    private static final int CODE_WIDTH = 400;
    private static final int CODE_HEIGHT = 400;
    private static final int FRONT_COLOR = 0x000000;
    private static final int BACKGROUND_COLOR = 0xFFFFFF;

    /**
     * 根据本地二维码图片————————解析二维码内容
     * （注：图片必须是二维码图片，但也可以是微信用户二维码名片，上面有名称、头像也是可以的）
     *
     * @param file 本地二维码图片文件,如 E:\\logs\\2.jpg
     * @return
     * @throws Exception
     */
    public static String parseQRCodeByFile(File file) {
        String resultStr = null;
        if (file == null || file.isDirectory() || !file.exists()) {
            return resultStr;
        }
        try {
            /**ImageIO 的 BufferedImage read(URL input) 方法用于读取网络图片文件转为内存缓冲图像
             * 同理还有：read(File input)、read(InputStream input)、、read(ImageInputStream stream)
             */
            BufferedImage bufferedImage = ImageIO.read(file);
            /**
             * com.google.zxing.client.j2se.BufferedImageLuminanceSource：缓冲图像亮度源
             * 将 java.awt.image.BufferedImage 转为 zxing 的 缓冲图像亮度源
             * 关键就是下面这几句：HybridBinarizer 用于读取二维码图像数据，BinaryBitmap 二进制位图
             */
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Hashtable hints = new Hashtable();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            /**
             * 如果图片不是二维码图片，则 decode 抛异常：com.google.zxing.NotFoundException
             * MultiFormatWriter 的 encode 用于对内容进行编码成 2D 矩阵
             * MultiFormatReader 的 decode 用于读取二进制位图数据
             */
            Result result = new MultiFormatReader().decode(bitmap, hints);
            resultStr = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
            System.out.println("-----图片非二维码图片：" + file.getPath());
        }
        return resultStr;
    }


    /**
     * 根据网络二维码图片————————解析二维码内容
     * （区别仅仅在于 ImageIO.read(url); 这一个重载的方法）
     *
     * @param item 二维码图片网络地址，如 https://res.wx.qq.com/mpres/htmledition/images/mp_qrcode3a7b38.gif
     * @return
     * @throws Exception
     */
    public static ResultVo parseQRCodeByUrl(String item) {
        String resultStr = null;
        if (item == null || item.equals("")) {
            return new ResultVo(-1,false,"url为null",null);
        }
        URL url = null;
        try {
            url = new URL(item);
        } catch (MalformedURLException e) {
            return new ResultVo(10003,false,"URL输入有误",null);
        }
        try {
            /**ImageIO 的 BufferedImage read(URL input) 方法用于读取网络图片文件转为内存缓冲图像
             * 同理还有：read(File input)、read(InputStream input)、、read(ImageInputStream stream)
             *
             * 如果图片网络地址错误，比如不能访问，则 read 抛异常：javax.imageio.IIOException: Can't get input stream from URL!
             */
            BufferedImage bufferedImage = ImageIO.read(url);
            /**
             * com.google.zxing.client.j2se.BufferedImageLuminanceSource：缓冲图像亮度源
             * 将 java.awt.image.BufferedImage 转为 zxing 的 缓冲图像亮度源
             * 关键就是下面这几句：HybridBinarizer 用于读取二维码图像数据，BinaryBitmap 二进制位图
             */
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Hashtable hints = new Hashtable();
            /**
             * 如果内容包含中文，则解码的字符集格式应该和编码时一致
             */
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            /**
             * 如果图片不是二维码图片，则 decode 抛异常：com.google.zxing.NotFoundException
             * MultiFormatWriter 的 encode 用于对内容进行编码成 2D 矩阵
             * MultiFormatReader 的 decode 用于读取二进制位图数据
             */
            Result result = new MultiFormatReader().decode(bitmap, hints);
            resultStr = result.getText();
        } catch (IOException e) {
            return new ResultVo(10001,false,"二维码图片地址错误",null);
        } catch (NotFoundException e) {
            return new ResultVo(10002,false,"图片不包含二维码",null);
        }catch (NullPointerException e){
            return new ResultVo(10004,false,"图片网络地址错误",null);
        }
        return new ResultVo(200,true,"图片解析二维码成功",resultStr);
    }

    public static void main(String[] args) throws MalformedURLException {
//        File localFile = new File("E:\\logs\\1.png");
//        String localQRcodeContent = parseQRCodeByFile(localFile);
//        System.out.println(localFile + " 二维码内容：" + localQRcodeContent);

        String url  = ("http://talk.hndt.com/fileupload/PIC/2019/2/19/12f6b1bed7054ec9860ac2a3e7caa36e.gif");
        String netQRcodeContent = parseQRCodeByUrl(url).toString();
        System.out.println(netQRcodeContent);
    }
}
