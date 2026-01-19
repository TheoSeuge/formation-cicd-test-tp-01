package com.devops.cicd;

public class PricingConfig {
    private final double vatRate;
    private final double freeShippingThreshold;

    public PricingConfig(double vatRate, double freeShippingThreshold) {
        this.vatRate = vatRate;
        this.freeShippingThreshold = freeShippingThreshold;
    }

    public double getVatRate() {
        return vatRate;
    }

    public double getFreeShippingThreshold() {
        return freeShippingThreshold;
    }
}
