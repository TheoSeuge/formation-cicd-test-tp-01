package com.devops.cicd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingServiceTest {

    private final PricingConfig fakeConfig = new PricingConfig(20.0, 50.0);
    private final PricingService service = new PricingService(fakeConfig);

    // ==================== Tests TVA ====================

    @Test
    void applyVat_shouldAddTwentyPercentVat() {
        double result = service.applyVat(100.0);
        assertEquals(120.0, result, 0.01);
    }

    @Test
    void applyVat_withZeroAmount_shouldReturnZero() {
        double result = service.applyVat(0.0);
        assertEquals(0.0, result, 0.01);
    }

    // ==================== Tests Remise VIP ====================

    @Test
    void applyVipDiscount_whenVip_shouldApplyTenPercentDiscount() {
        double result = service.applyVipDiscount(100.0, true);
        assertEquals(90.0, result, 0.01);
    }

    @Test
    void applyVipDiscount_whenNotVip_shouldNotApplyDiscount() {
        double result = service.applyVipDiscount(100.0, false);
        assertEquals(100.0, result, 0.01);
    }

    // ==================== Tests Frais de Livraison ====================

    @Test
    void shippingCost_whenAmountBelowThreshold_shouldChargeShipping() {
        double result = service.shippingCost(49.99);
        assertEquals(4.99, result, 0.01);
    }

    @Test
    void shippingCost_whenAmountEqualsThreshold_shouldBeFree() {
        double result = service.shippingCost(50.0);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    void shippingCost_whenAmountAboveThreshold_shouldBeFree() {
        double result = service.shippingCost(100.0);
        assertEquals(0.0, result, 0.01);
    }

    // ==================== Tests Total Final ====================

    @Test
    void finalTotal_vipWithFreeShipping_shouldApplyVatAndDiscount() {
        // 100 HT -> 120 TTC -> 108 après remise VIP -> livraison gratuite (120 >= 50)
        double result = service.finalTotal(100.0, true);
        assertEquals(108.0, result, 0.01);
    }

    @Test
    void finalTotal_nonVipWithFreeShipping_shouldApplyVatOnly() {
        // 100 HT -> 120 TTC -> pas de remise -> livraison gratuite (120 >= 50)
        double result = service.finalTotal(100.0, false);
        assertEquals(120.0, result, 0.01);
    }

    @Test
    void finalTotal_vipWithShippingCost_shouldAddShipping() {
        // 30 HT -> 36 TTC -> 32.4 après remise VIP -> 4.99 livraison (36 < 50)
        double result = service.finalTotal(30.0, true);
        assertEquals(37.39, result, 0.01);
    }

    @Test
    void finalTotal_nonVipWithShippingCost_shouldAddShipping() {
        // 30 HT -> 36 TTC -> pas de remise -> 4.99 livraison (36 < 50)
        double result = service.finalTotal(30.0, false);
        assertEquals(40.99, result, 0.01);
    }
}
