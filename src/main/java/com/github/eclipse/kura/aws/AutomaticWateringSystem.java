package com.github.eclipse.kura.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eclipse.kura.HardwareUtils;

public class AutomaticWateringSystem {

    private final Logger log = LoggerFactory.getLogger(AutomaticWateringSystem.class);
    private final Pump pump = new Pump(HardwareUtils.PUMP);
    private static final int WATERING_TIME = 3000;

    public AutomaticWateringSystem() {
        pump.turnOff();
    }

    public void startWatering() throws Exception {
        log.info("plantation watering");
        pump.turnOn();
        Thread.sleep(WATERING_TIME);
        pump.turnOff();
        log.info("plantation watering done");
    }
}