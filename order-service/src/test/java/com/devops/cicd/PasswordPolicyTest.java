package com.devops.cicd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordPolicyTest {

    // ==================== Tests pour mot de passe fort valide ====================

    @Test
    void isStrong_withValidPassword_shouldReturnTrue() {
        // Un mot de passe qui respecte toutes les règles
        assertTrue(PasswordPolicy.isStrong("Azerty123!"));
    }

    @Test
    void isStrong_withAnotherValidPassword_shouldReturnTrue() {
        assertTrue(PasswordPolicy.isStrong("P@ssw0rd"));
    }

    // ==================== Tests pour null et vide ====================

    @Test
    void isStrong_withNull_shouldReturnFalse() {
        assertFalse(PasswordPolicy.isStrong(null));
    }

    @Test
    void isStrong_withEmptyString_shouldReturnFalse() {
        assertFalse(PasswordPolicy.isStrong(""));
    }

    // ==================== Tests pour longueur minimale (8 caractères) ====================

    @Test
    void isStrong_withLessThanEightCharacters_shouldReturnFalse() {
        // "Ab1!" = 4 caractères seulement
        assertFalse(PasswordPolicy.isStrong("Ab1!"));
    }

    @Test
    void isStrong_withExactlySevenCharacters_shouldReturnFalse() {
        // "Abc12!x" = 7 caractères
        assertFalse(PasswordPolicy.isStrong("Abc12!x"));
    }

    @Test
    void isStrong_withExactlyEightCharacters_shouldReturnTrue() {
        // "Abc123!x" = 8 caractères avec toutes les règles respectées
        assertTrue(PasswordPolicy.isStrong("Abc123!x"));
    }

    // ==================== Tests pour majuscule obligatoire ====================

    @Test
    void isStrong_withoutUppercase_shouldReturnFalse() {
        // Pas de majuscule
        assertFalse(PasswordPolicy.isStrong("azerty123!"));
    }

    // ==================== Tests pour minuscule obligatoire ====================

    @Test
    void isStrong_withoutLowercase_shouldReturnFalse() {
        // Pas de minuscule
        assertFalse(PasswordPolicy.isStrong("AZERTY123!"));
    }

    // ==================== Tests pour chiffre obligatoire ====================

    @Test
    void isStrong_withoutDigit_shouldReturnFalse() {
        // Pas de chiffre
        assertFalse(PasswordPolicy.isStrong("Azertyui!"));
    }

    // ==================== Tests pour caractère spécial obligatoire ====================

    @Test
    void isStrong_withoutSpecialCharacter_shouldReturnFalse() {
        // Pas de caractère spécial
        assertFalse(PasswordPolicy.isStrong("Azerty123"));
    }

    @Test
    void isStrong_withDifferentSpecialCharacters_shouldReturnTrue() {
        assertTrue(PasswordPolicy.isStrong("Azerty1@"));
        assertTrue(PasswordPolicy.isStrong("Azerty1#"));
        assertTrue(PasswordPolicy.isStrong("Azerty1$"));
        assertTrue(PasswordPolicy.isStrong("Azerty1%"));
        assertTrue(PasswordPolicy.isStrong("Azerty1&"));
    }
}
