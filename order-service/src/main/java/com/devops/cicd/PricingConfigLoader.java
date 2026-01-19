package com.devops.cicd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PricingConfigLoader {

    public PricingConfig load() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                throw new IllegalStateException("Unable to find app.properties");
            }
            props.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load app.properties", e);
        }

        double vatRate = Double.parseDouble(required(props, "vatRate"));
        double freeShippingThreshold = Double.parseDouble(required(props, "freeShippingThreshold"));

        return new PricingConfig(vatRate, freeShippingThreshold);
    }

    private String required(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing required property: " + key);
        }
        return value.trim();
    }
}
