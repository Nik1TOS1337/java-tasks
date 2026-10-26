package academy.tochkavhoda.competition.service;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

final class Passwords {
    private Passwords() {
    }

    static String salt() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    static String hash(String password, String salt) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),
                Base64.getDecoder().decode(salt), 65536, 256);
        try {
            byte[] hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Не удалось проверить пароль", e);
        } finally {
            spec.clearPassword();
        }
    }

    static boolean matches(String password, String salt, String expected) {
        return MessageDigest.isEqual(Base64.getDecoder().decode(hash(password, salt)),
                Base64.getDecoder().decode(expected));
    }
}
