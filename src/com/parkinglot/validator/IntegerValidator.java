package com.parkinglot.validator;

public class IntegerValidator {

    public static boolean isInteger(final String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}



