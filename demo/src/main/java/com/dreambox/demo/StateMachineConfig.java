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


@Configuration
@EnableStateMachine
class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
            .withStates()
            .initial(States.LOCKED) // Стартуем в закрытом состоянии
            .state(States.UNLOCKED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
            // Если LOCKED и бросили COIN -> переходим в UNLOCKED
            .withExternal().source(States.LOCKED).target(States.UNLOCKED).event(Events.COIN)
            .and()
            // Если UNLOCKED и сделали PUSH -> переходим обратно в LOCKED
            .withExternal().source(States.UNLOCKED).target(States.LOCKED).event(Events.PUSH);
    }
}
