package com.github.eclipse.kura.routes;

import org.apache.camel.BeanInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import com.github.eclipse.kura.aws.AutomaticWateringSystem;
import com.github.eclipse.kura.processors.WateringProcess;

public class WateringSystemRouteBuilder extends RouteBuilder {

    @BeanInject("automaticWateringSystem")
    private AutomaticWateringSystem automaticWateringSystem = new AutomaticWateringSystem();
    // period=1h15m
    private String wateringProcess = "timer:wateringProcess?period=30m";

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.auto);

        rest("plantation").produces("application/json").get("/list").route().id("list").transform().constant("ok")
                .log("ok").endRest();

        from(wateringProcess).routeId("wateringProcess").process(new WateringProcess()).log("=))))) Fixed Time ${body}")
                .to("mock:success");
    }
}
