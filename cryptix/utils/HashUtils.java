package cryptix.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class HashUtils {
    public static String generateHash(String filePath) throws Exception {
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(fileBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
