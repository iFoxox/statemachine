package com.lrp.statemachine.statemachine;

import cn.hutool.core.util.ReflectUtil;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.AbstractStateMachine;
import org.springframework.statemachine.support.StateMachineUtils;
import org.springframework.statemachine.transition.Transition;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author 廖山剑
 * create date 2019/11/17
 */
public class MyStateMachineUtils extends StateMachineUtils {

    public static <S, E> void setCurrentState(StateMachine<S, E> stateMachine, S state) {
        if (stateMachine instanceof AbstractStateMachine) {
            setCurrentState((AbstractStateMachine<S, E>) stateMachine, state);

//            System.out.println("当前状态:" + state.toString());
        } else {
            throw new IllegalArgumentException("Provided StateMachine is not a valid type");
        }
    }

    public static <S, E> void setCurrentState(AbstractStateMachine<S, E> stateMachine, S state) {
        Method method = ReflectUtil.getMethod(stateMachine.getClass(), "setCurrentStateInternal"
                , State.class, Message.class, Transition.class, Boolean.class, StateMachine.class,
                Collection.class, Collection.class);
        method.setAccessible(true);
        ReflectionUtils.invokeMethod(method, stateMachine, findState(stateMachine, state), null, null, false, stateMachine, null, null);
    }

    private static <S, E> State<S, E> findState(AbstractStateMachine<S, E> stateMachine, S stateId) {
        for (State<S, E> state : stateMachine.getStates()) {
            if (state.getId() == stateId) {
                return state;
            }
        }

        throw new IllegalArgumentException("Specified State ID is not valid");
    }
}
