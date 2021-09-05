package com.reloadly.customeraccount.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
public class PasswordManager implements PasswordEncoder {

    @Value("${password.encoder.secret}")
    private String secret;

    @Value("${password.encoder.iteration}")
    private Integer iteration;

    @Value("${password.encoder.keylength}")
    private Integer keylength;

    /**
     * Encode password
     */
    @Override
    public String encode(CharSequence password) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(password.toString().toCharArray(), secret.getBytes(), iteration, keylength))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Validate password match
     */
    @Override
    public boolean matches(CharSequence password, String hashPassword) {
        return encode(password).equals(hashPassword);
    }

}
