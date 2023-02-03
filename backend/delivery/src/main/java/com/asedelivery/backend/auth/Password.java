package com.asedelivery.backend.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
    private static String hashAlgorithm = "SHA-256";
    private static MessageDigest digest = null;

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    static public String generateSalt() {
        return "";
    }

    static public String encrypt(String password, String salt) {
        String token = password + salt;
        return hash(token);
    }

    static private String hash(String token) {
        return bytesToHex(digest.digest(token.getBytes(StandardCharsets.UTF_8)));
    }

    private String salt;
    private String cyphertext;

    public Password(String password) throws NoSuchAlgorithmException {
        if (digest == null) {
            digest = MessageDigest.getInstance(hashAlgorithm);
        }

        salt = generateSalt();
        cyphertext = encrypt(password, salt);
    }

    public boolean checkPassword(String password) {
        return cyphertext.equals(encrypt(password, salt));
    }
}
