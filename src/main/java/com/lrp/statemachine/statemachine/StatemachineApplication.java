package com.lrp.statemachine.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class StatemachineApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(StatemachineApplication.class, args);

    }


    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Override
    public void run(String... args) {
        //当前状态
        MyStateMachineUtils.setCurrentState(stateMachine,States.PART_RECORD);
        //事件
        stateMachine.sendEvent(Events.RECORD);

        //当前状态
        MyStateMachineUtils.setCurrentState(stateMachine,States.PART_RECORD_PART_FINISH);
        //事件
        stateMachine.sendEvent(Events.RECORD);
    }
}
