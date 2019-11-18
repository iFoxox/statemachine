package com.lrp.statemachine.statemachine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

/**
 * @author 廖山剑
 * create date 2019/11/16
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                //开始状态为待录入
                .initial(States.WAIT_RECORD)
                .states(EnumSet.allOf(States.class));
    }


    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> builder)
            throws Exception {

        builder
                .withExternal()
                //待录入 -> 部分录入
                .source(States.WAIT_RECORD)
                .target(States.PART_RECORD)
                .event(Events.RECORD)
                .action(waitToPartAction())
                //部分录入 -> 全部录入
                .and().withExternal()
                .source(States.PART_RECORD)
                .target(States.ALL_RECORD)
                .event(Events.RECORD)
                .action(waitToPartAction())
                //部分录入 -> 部分录入部分完成
                .and().withExternal()
                .source(States.PART_RECORD)
                .target(States.PART_RECORD_PART_FINISH)
                .event(Events.FINISH)
                .action(waitToPartAction())
                //全部录入 -> 全部录入部分完成
                .and().withExternal()
                .source(States.ALL_RECORD)
                .target(States.ALL_RECORD_PART_FINISH)
                .event(Events.FINISH)
                .action(waitToPartAction())
                //部分录入部分完成 -> 全部录入部分完成
                .and().withExternal()
                .source(States.PART_RECORD_PART_FINISH)
                .target(States.ALL_RECORD_PART_FINISH)
                .event(Events.RECORD)
                .action(waitToPartAction())

                //全部录入部分完成 -> 全部完成
                .and().withExternal()
                .source(States.ALL_RECORD_PART_FINISH)
                .target(States.ALL_FINISH)
                .event(Events.RECORD)
                .action(waitToPartAction())
        ;
    }

    /**
     * 异常处理Action
     *
     * @return action对象
     */
    @Bean
    public Action<States, Events> waitToPartAction() {
        return context -> {
            State<States, Events> source = context.getSource();
            State<States, Events> target = context.getTarget();
            States sourceId = source.getId();
            Events event = context.getEvent();
            States targetId = target.getId();
            System.out.println("[" + sourceId.getTag() + "] 状态发生 " + "[" + event.getTag() + "] 事件后变成 ["+targetId.getTag()+"] 状态");
        };
    }
}
