package com.github.eclipse.kura.aws;

import com.github.eclipse.kura.HardwareUtils;

public class Pump {

    private int pumpAddress;

    public Pump(int pumpAddress) {
        this.pumpAddress = pumpAddress;
    }

    public void turnOn() {
        HardwareUtils.turnOn(pumpAddress);
    }

    public void turnOff() {
        HardwareUtils.turnOff(pumpAddress);
    }

}