package com.devops.cicd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PricingIntegrationTest {

    @Test
    void fullPricingFlow_withRealConfigFile() {
        // Arrange - Charger la configuration réelle depuis le fichier
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig config = loader.load();

        // Vérifier que la configuration est chargée correctement
        assertNotNull(config);
        assertEquals(20.0, config.getVatRate(), 0.01);
        assertEquals(50.0, config.getFreeShippingThreshold(), 0.01);

        // Instancier le service avec la configuration réelle
        PricingService service = new PricingService(config);

        // Act - Scénario métier complet
        // montant HT = 100
        // TVA = 20% -> TTC = 120
        // client VIP -> remise 10% -> 108
        // livraison gratuite (120 >= 50)
        // Total = 108
        double result = service.finalTotal(100.0, true);

        // Assert
        assertEquals(108.0, result, 0.01);
    }

    @Test
    void fullPricingFlow_nonVipWithShipping() {
        // Arrange
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig config = loader.load();
        PricingService service = new PricingService(config);

        // Act - Scénario: client non VIP avec frais de livraison
        // montant HT = 30
        // TVA = 20% -> TTC = 36
        // pas de remise VIP
        // frais de livraison (36 < 50) -> 4.99
        // Total = 36 + 4.99 = 40.99
        double result = service.finalTotal(30.0, false);

        // Assert
        assertEquals(40.99, result, 0.01);
    }

    @Test
    void configLoader_shouldLoadAllProperties() {
        // Arrange & Act
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig config = loader.load();

        // Assert - Vérifier que toutes les propriétés sont chargées
        assertEquals(20.0, config.getVatRate(), 0.01);
        assertEquals(50.0, config.getFreeShippingThreshold(), 0.01);
    }
}
