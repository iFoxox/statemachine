package com.lrp.statemachine.statemachine;

import lombok.Getter;

/**
 * @author 廖山剑
 * create date 2019/11/16
 */
public enum States {
    //
    WAIT_RECORD(1, "待录入"),
    PART_RECORD(2, "部分录入"),
    ALL_RECORD(3, "全部录入"),
    PART_RECORD_PART_FINISH(4, "部分录入部分完成"),
    ALL_RECORD_PART_FINISH(5, "全部录入部分完成"),
    ALL_FINISH(6, "全部完成");


    @Getter
    private Integer value;

    @Getter
    private String tag;

    States(Integer value, String tag) {
        this.value = value;
        this.tag = tag;
    }
}

