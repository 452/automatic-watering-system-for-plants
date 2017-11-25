package com.github.eclipse.kura.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(id = "com.github.eclipse.kura.components.WateringSystemComponent", name = "Watering System", description = "Automatic Watering System for plants")
public @interface Config {

    @AttributeDefinition(name = "enabled", description = "Enable Watering System")
    boolean enabled() default false;

}