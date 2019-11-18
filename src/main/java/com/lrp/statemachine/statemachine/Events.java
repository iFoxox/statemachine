package com.lrp.statemachine.statemachine;

import lombok.Getter;

/**
 * @author 廖山剑
 * create date 2019/11/16
 */
public enum Events {
    //
    RECORD(1, "录入"),
    FINISH(2, "完成");


    @Getter
    private Integer value;

    @Getter
    private String tag;

    Events(Integer value, String tag) {
        this.value = value;
        this.tag = tag;
    }

}
