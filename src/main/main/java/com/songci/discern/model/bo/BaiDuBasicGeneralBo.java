package com.songci.discern.model.bo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created By slx on 2019/2/20
 */
@Data
public class BaiDuBasicGeneralBo {
    /**
     * 图像方向，当detect_direction=true时存在。
     *- -1:未定义，
     *- 0:正向，
     *- 1: 逆时针90度，
     *- 2:逆时针180度，
     *- 3:逆时针270度
     */
    private Integer direction;
    /**
     * 唯一的log id，用于问题定位
     */
    private String log_id;
    /**
     * 文字行数
     */
    private Integer words_result_num;
    /**
     * 错误码
     */
    private Integer error_code;
    /**
     * 错误信息
     */
    private String error_msg;
    /**
     * 定位和识别结果数组
     */
    private List<Discern>  words_result;
    @Data
    @ToString
    public class Discern{
        /**
         * 识别的文字内容
         */
        private String words;
    }
}
