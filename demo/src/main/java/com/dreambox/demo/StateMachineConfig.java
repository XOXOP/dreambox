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
            .initial(States.REGISTERED) // Стартуем в закрытом состоянии
            .state(States.IN_TRANSIT)
            .state(States.ARRIVED)
            .end(States.DELIVERED);
    }
// REGISTERED, DELIVERED,ARRIVED,IN_TRANSIT
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
            // Если LOCKED и бросили COIN -> переходим в UNLOCKED
            .withExternal().source(States.REGISTERED).target(States.IN_TRANSIT).event(Events.ORDER_PLACED)
            .and()
            // Если UNLOCKED и сделали PUSH -> переходим обратно в LOCKED
            .withExternal().source(States.IN_TRANSIT).target(States.ARRIVED).event(Events.PUSH)
            .and()
            .withExternal().source(States.ARRIVED).target(States.DELIVERED).event(Events.READY);
    }
}
