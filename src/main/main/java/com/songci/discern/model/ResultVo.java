package com.songci.discern.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created By slx on 2019/2/20
 * @author songci
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo<T> {
    private Integer code;
    private boolean success = false;
    private String message;
    private T data;
}
