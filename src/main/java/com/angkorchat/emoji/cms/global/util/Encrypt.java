package com.angkorchat.emoji.cms.global.util;

import com.angkorchat.emoji.cms.global.error.MethodArgumentNotValidException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import jakarta.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class Encrypt {
    public static String encryptSHA256(String value) throws Exception {
        String encryptStr = "";

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(value.getBytes());

            byte[] digest = sha.digest();
            encryptStr = Hex.encodeHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            encryptStr = null;
        }

        return encryptStr;
    }

    public static String encryptSHA512(String value) throws Exception {
        String encryptStr;

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            sha.update(value.getBytes());

            byte[] digest = sha.digest();
            encryptStr = Hex.encodeHexString(digest);
        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException(ex);
        }

        return encryptStr;
    }

    public static PublicKey loadPublicKey(String publicKeyStr, String encode) {
        byte[] keyBytes = new byte[0];

        if (encode.equals("Base64")) {
            publicKeyStr = publicKeyStr.replace("-----BEGIN PUBLIC KEY-----\n", "")
                    .replace("-----END PUBLICK KEY", "")
                    .replace("\\s", "");

            keyBytes = Base64.getDecoder().decode(publicKeyStr);
        } else if (encode.equals("Hex")) {
            keyBytes = DatatypeConverter.parseHexBinary(publicKeyStr);
        }

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        try {
            return keyFactory.generatePublic(spec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
