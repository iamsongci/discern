package com.songci.discern.controller;

import com.songci.discern.model.ResultVo;
import com.songci.discern.model.vo.ImageDiscren;
import com.songci.discern.utils.BaiduDiscernApiUtil;
import com.songci.discern.utils.QRBarCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created By slx on 2019/2/20
 * @author songci
 */

@RestController
@RequestMapping("/image")
@Api(description = "图片识别")
public class ImageDiscrenController {
    @ApiOperation(value = "内容识别", notes = "获取图片中二维码和文本信息")
    @RequestMapping(value = "/ImageDiscren",method = RequestMethod.POST)
    public ImageDiscren imageDiscren(@RequestBody @ApiParam(name = "url", value = "图片地址") String url){
        return new ImageDiscren(QRBarCodeUtil.parseQRCodeByUrl(url),BaiduDiscernApiUtil.sample(url));
    }
//    @ApiOperation(value = "二维码识别", notes = "获取图片中二维码中信息")
//    @RequestMapping(value = "/QRCodeDiscren",method = RequestMethod.POST)
//    public ResultVo qrCodeDiscren(@RequestBody @ApiParam(name = "url", value = "图片地址") String url){
//        return QRBarCodeUtil.parseQRCodeByUrl(url);
//    }
//
//    @ApiOperation(value = "通用文字识别", notes = "获取图片中文本信息")
//    @RequestMapping(value = "/OrdinaryTextDiscren",method = RequestMethod.POST)
//    public ResultVo ordinaryTextDiscren(@RequestBody @ApiParam(name = "url", value = "图片地址") String url){
//        return BaiduDiscernApiUtil.sample(url);
//    }
}
