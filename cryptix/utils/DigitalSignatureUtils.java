package cryptix.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class DigitalSignatureUtils {
    private static final String ALGORITHM = "RSA";

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    public static String sign(String data, PrivateKey privateKey) throws Exception {
        Signature rsa = Signature.getInstance("SHA256withRSA");
        rsa.initSign(privateKey);
        rsa.update(data.getBytes());
        byte[] signature = rsa.sign();
        return Base64.getEncoder().encodeToString(signature);
    }

    public static boolean verify(String data, String signature, PublicKey publicKey) throws Exception {
        Signature rsa = Signature.getInstance("SHA256withRSA");
        rsa.initVerify(publicKey);
        rsa.update(data.getBytes());
        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return rsa.verify(signatureBytes);
    }
}
