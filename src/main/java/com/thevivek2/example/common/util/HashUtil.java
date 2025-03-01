package com.thevivek2.example.common.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class HashUtil {

    private HashUtil() {
    }

    public static String getSha512(String passwordToHash, String salt) {
        try {
            var md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Failed to generate SHA-512", e);
        }
    }


    public static String getRandomHash() {
        return getSha512(UUID.randomUUID().toString(),
                String.valueOf(System.currentTimeMillis()) + UUID.randomUUID());
    }

    public static String getRandomHash256() throws NoSuchAlgorithmException {
        return sha256(UUID.randomUUID().toString() + System.currentTimeMillis()
                + UUID.randomUUID());
    }

    public static boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    private static String sha256(String str) throws NoSuchAlgorithmException {
        var digest = MessageDigest.getInstance("SHA-256");
        return bytesToHex(digest.digest(str.getBytes(StandardCharsets.UTF_8)));
    }

    private static String bytesToHex(byte[] hash) {
        var hexString = new StringBuilder();
        for (byte aHash : hash) {
            var hex = Integer.toHexString(0xff & aHash);
            if (1 == hex.length())
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

