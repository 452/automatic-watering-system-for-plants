package com.github.eclipse.kura;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HardwareUtils {

    // https://pinout.xyz/pinout/pin36_gpio16
    public static final int PUMP = 36;
    private static final Logger log = LoggerFactory.getLogger(HardwareUtils.class);
    // private static final boolean ON = true;
    // private static final boolean OFF = false;

    private HardwareUtils() {
    }

    public static void turnOn(int pinAddress) {
        // on this moment commented code not work
        // GPIOPinConfig pump = new GPIOPinConfig(DeviceConfig.DEFAULT, pinAddress, GPIOPinConfig.DIR_OUTPUT_ONLY,
        // GPIOPinConfig.MODE_OUTPUT_PUSH_PULL, GPIOPinConfig.TRIGGER_NONE, true);
        // try (GPIOPin pin = DeviceManager.open(pump)) {
        // pin.setValue(ON);
        // } catch (Exception e) {
        // log.error("cannot turnOn pin", e);
        // }
        try {
            Runtime.getRuntime().exec(String.format("gpio -1 mode %s output", String.valueOf(pinAddress)));
            Runtime.getRuntime().exec(String.format("gpio -1 write %s 1", String.valueOf(pinAddress)));
        } catch (IOException e) {
            log.error("cannot turnOn pin", e);
        }
    }

    public static void turnOff(int pinAddress) {
        // on this moment commented code not work
        // GPIOPinConfig pump = new GPIOPinConfig(DeviceConfig.DEFAULT, pinAddress, GPIOPinConfig.DIR_OUTPUT_ONLY,
        // GPIOPinConfig.MODE_OUTPUT_PUSH_PULL, GPIOPinConfig.TRIGGER_NONE, true);
        // try (GPIOPin pin = DeviceManager.open(pump)) {
        // pin.setValue(OFF);
        // } catch (Exception e) {
        // log.error("cannot turnOff pin", e);
        // }
        try {
            Runtime.getRuntime().exec(String.format("gpio -1 mode %s output", String.valueOf(pinAddress)));
            Runtime.getRuntime().exec(String.format("gpio -1 write %s 0", String.valueOf(pinAddress)));
        } catch (IOException e) {
            log.error("cannot turnOn pin", e);
        }
    }

}