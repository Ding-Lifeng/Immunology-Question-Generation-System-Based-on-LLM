package edu.ssadmin.security.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    public static String encrypt(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public static boolean verify(String rawPassword, String encryptedPassword) {
        // System.out.println(encrypt(rawPassword));
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }
}
