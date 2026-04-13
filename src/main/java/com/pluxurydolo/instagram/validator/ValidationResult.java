package com.pluxurydolo.instagram.validator;

public enum ValidationResult {
    SUCCESS,
    FAILURE;

    public static ValidationResult fromBoolean(boolean result) {
        if (result) {
            return SUCCESS;
        }
        return FAILURE;
    }
}
