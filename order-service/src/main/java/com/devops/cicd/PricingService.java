package com.devops.cicd;

public final class PricingService {

    private static final double VIP_DISCOUNT_RATE = 0.10;
    private static final double SHIPPING_COST = 4.99;

    private final PricingConfig config;

    public PricingService(PricingConfig config) {
        this.config = config;
    }

    public double applyVat(double amountExclVat) {
        double vatMultiplier = 1 + (config.getVatRate() / 100);
        return amountExclVat * vatMultiplier;
    }

    public double applyVipDiscount(double amount, boolean vip) {
        if (vip) {
            return amount * (1 - VIP_DISCOUNT_RATE);
        }
        return amount;
    }

    public double shippingCost(double amount) {
        if (amount >= config.getFreeShippingThreshold()) {
            return 0.0;
        }
        return SHIPPING_COST;
    }

    /**
     * - TVA appliquée d'abord : HT -> TTC
     * - remise VIP appliquée sur TTC
     * - frais de livraison ajoutés ensuite (calculés sur TTC)
     */
    public double finalTotal(double amountExclVat, boolean vip) {
        double amountWithVat = applyVat(amountExclVat);
        double amountAfterDiscount = applyVipDiscount(amountWithVat, vip);
        double shipping = shippingCost(amountWithVat);
        return amountAfterDiscount + shipping;
    }
}
