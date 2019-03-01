package com.songci.discern.model.vo;

import com.songci.discern.model.ResultVo;
import com.songci.discern.model.bo.BaiDuBasicGeneralBo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created By slx on 2019/2/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDiscren {
    /**
     * 识别二维码内容
     */
    private String qrcode;
    /**
     * 识别文字内容
     */
    private String words;

    public ImageDiscren(ResultVo qrcodeVo, ResultVo wordsVo) {
        if(qrcodeVo.isSuccess()){
            this.qrcode=qrcodeVo.getData().toString();
        }
        if(wordsVo.isSuccess()){
            StringBuilder str = new StringBuilder();
            BaiDuBasicGeneralBo bo=(BaiDuBasicGeneralBo)wordsVo.getData();
            bo.getWords_result().forEach(x->{
                str.append(x.getWords());
            });
            this.words=str.toString();
        }
    }
}
