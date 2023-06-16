package com.example.chatgpt.security;

public class PasswordGenerator {
    public static String generatePassword(int length) {
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";

        String allChars = upperChars + lowerChars + numbers + symbols;
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * allChars.length());
            password.append(allChars.charAt(index));
        }

        return password.toString();
    }
}