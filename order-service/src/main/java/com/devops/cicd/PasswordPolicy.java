package com.devops.cicd;

public class PasswordPolicy {

    private static final int MIN_LENGTH = 8;
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+-=[]{}|;':\",./<>?";

    public static boolean isStrong(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        if (password.length() < MIN_LENGTH) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (SPECIAL_CHARACTERS.indexOf(c) >= 0) {
                hasSpecial = true;
            }
        }

        return hasUppercase && hasLowercase && hasDigit && hasSpecial;
    }
}
