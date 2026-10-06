package com.dreambox.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

// 1. Определяем Состояния и События



@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== НАЧАЛО ТЕСТА ТУРНИКЕТА ===");
        
        stateMachine.startReactively().subscribe();
        System.out.println("Текущее состояние: " + stateMachine.getState().getId());

        System.out.println("\n--- Действие: Толкаем турникет без оплаты ---");
        stateMachine.sendEvent(Events.ORDER_PLACED);
        System.out.println("Текущее состояние: " + stateMachine.getState().getId());

        System.out.println("\n--- Действие: Бросаем монетку ---");
        stateMachine.sendEvent(Events.PUSH);
        System.out.println("Текущее состояние: " + stateMachine.getState().getId());
        
        System.out.println("\n--- Действие: вручили ---");
        stateMachine.sendEvent(Events.READY);
        System.out.println("Текущее состояние: " + stateMachine.getState().getId());

        
    }
}
