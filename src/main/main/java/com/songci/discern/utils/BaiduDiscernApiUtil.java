package com.songci.discern.utils;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;
import com.songci.discern.model.ResultVo;
import com.songci.discern.model.bo.BaiDuBasicGeneralBo;
import org.json.JSONObject;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.HashMap;

/**
 * Created By slx on 2019/2/20
 */
public class BaiduDiscernApiUtil {
    //设置APPID/AK/SK
    public static final String APP_ID = "15593768";
    public static final String API_KEY = "0fQG9Sf6VjquLo9NAEBmqeYX";
    public static final String SECRET_KEY = "3FfdGIpPOxAiZ6Cq1HQpfFZu5wwG3via";

    public static ResultVo sample(String url) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");


        // 参数为本地图片路径
//        String image = "test.jpg";
//        JSONObject res = client.basicGeneral(image, options);
//        System.out.println(res.toString(2));

        // 参数为本地图片二进制数组
//        byte[] file = readImageFile(image);
//        res = client.basicGeneral(file, options);
//        System.out.println(res.toString(2));

        /**
         * 通用文字识别, 图片参数为远程url图片
         */
        JSONObject res = client.basicGeneralUrl(url, options);
        String body = res.toString(2);
        System.out.println(body);
        BaiDuBasicGeneralBo bo = JSON.parseObject(body,BaiDuBasicGeneralBo.class);
        if(bo.getError_code()!=null){
            return new ResultVo(bo.getError_code(),false,bo.getError_msg(),null);
        }
        return new ResultVo(200,true,"",bo);

    }
    public static void main(String[] args) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550657828924&di=919f375c0d0f18445987bdd3804c64d9&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201607%2F28%2F20160728123124_QezsL.thumb.700_0.jpeg";
        //String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550660218662&di=b663faa7fa70cf93204eae3ec7a2ae67&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201409%2F13%2F20140913162810_4KNrV.jpeg";
        sample(url);
    }
}
