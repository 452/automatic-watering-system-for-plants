package com.github.eclipse.kura.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.github.eclipse.kura.aws.AutomaticWateringSystem;

public class WateringProcess implements Processor {

    // @BeanInject("automaticWateringSystem")
    private AutomaticWateringSystem automaticWateringSystem = new AutomaticWateringSystem();

    public void process(Exchange outExchange) throws Exception {
        automaticWateringSystem.startWatering();
        outExchange.getOut().setBody(">>> " + automaticWateringSystem);
    }
}
