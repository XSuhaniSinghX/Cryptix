package cryptix.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class KeyManager {
    private static final String ALGORITHM = "AES";

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(256); // Use 256-bit AES
        return keyGen.generateKey();
    }

    public static void saveKey(SecretKey key, String fileName) throws IOException {
        byte[] keyBytes = key.getEncoded();
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(keyBytes);
        }
    }

    public static SecretKey loadKey(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("Key file not found: " + fileName);
        }

        byte[] keyBytes = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            int bytesRead = fis.read(keyBytes);
            if (bytesRead != keyBytes.length) {
                throw new IOException("Could not read the complete key file.");
            }
        }

        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IOException("Invalid AES key length: " + keyBytes.length);
        }

        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}
