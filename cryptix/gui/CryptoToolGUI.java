package cryptix.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.crypto.SecretKey;
import cryptix.utils.*;
import java.security.KeyPair;

public class CryptoToolGUI extends JFrame {
    private JTextField filePathField;
    private JTextArea outputArea;
    private SecretKey secretKey;
    private KeyPair keyPair;

    public CryptoToolGUI() {
        setTitle("Cryptix");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        filePathField = new JTextField(20);
        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(new BrowseAction());

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(new EncryptAction());
        JButton decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(new DecryptAction());
        JButton generateKeyButton = new JButton("Generate Key");
        generateKeyButton.addActionListener(new GenerateKeyAction());
        JButton saveKeyButton = new JButton("Save Key");
        saveKeyButton.addActionListener(new SaveKeyAction());
        JButton loadKeyButton = new JButton("Load Key");
        loadKeyButton.addActionListener(new LoadKeyAction());
        JButton hashButton = new JButton("Generate Hash");
        hashButton.addActionListener(new HashAction());
        JButton signButton = new JButton("Sign");
        signButton.addActionListener(new SignAction());
        JButton verifyButton = new JButton("Verify");
        verifyButton.addActionListener(new VerifyAction());

        outputArea = new JTextArea(10, 50);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);

        panel.add(new JLabel("File:"));
        panel.add(filePathField);
        panel.add(browseButton);
        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(generateKeyButton);
        panel.add(saveKeyButton);
        panel.add(loadKeyButton);
        panel.add(hashButton);
        panel.add(signButton);
        panel.add(verifyButton);
        panel.add(new JScrollPane(outputArea));

        add(panel);
    }

    private class BrowseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(CryptoToolGUI.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                filePathField.setText(fileChooser.getSelectedFile().getPath());
            }
        }
    }

    private class EncryptAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathField.getText();
            try {
                byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
                String encryptedText = CryptoUtils.encrypt(new String(fileBytes), secretKey);
                Files.write(Paths.get(filePath + ".enc"), encryptedText.getBytes());
                outputArea.setText("File encrypted successfully!\n" + filePath + ".enc");
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private class DecryptAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathField.getText();
            try {
                byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
                String decryptedText = CryptoUtils.decrypt(new String(fileBytes), secretKey);
                Files.write(Paths.get(filePath.replace(".enc", "")), decryptedText.getBytes());
                outputArea.setText("File decrypted successfully!\n" + filePath.replace(".enc", ""));
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private class GenerateKeyAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                secretKey = KeyManager.generateKey();
                outputArea.setText("Key generated successfully!");
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private class SaveKeyAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                KeyManager.saveKey(secretKey, "keyfile.key");
                outputArea.setText("Key saved successfully!");
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private class LoadKeyAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                secretKey = KeyManager.loadKey("keyfile.key");
                outputArea.setText("Key loaded successfully!");
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private class HashAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathField.getText();
            try {
                String hash = HashUtils.generateHash(filePath);
                outputArea.setText("File hash (SHA-256): " + hash);
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private class SignAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathField.getText();
            try {
                if (keyPair == null) {
                    keyPair = DigitalSignatureUtils.generateKeyPair();
                }
                byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
                String signature = DigitalSignatureUtils.sign(new String(fileBytes), keyPair.getPrivate());
                Files.write(Paths.get(filePath + ".sig"), signature.getBytes());
                outputArea.setText("File signed successfully!\n" + filePath + ".sig");
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private class VerifyAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathField.getText();
            try {
                byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
                byte[] sigBytes = Files.readAllBytes(Paths.get(filePath + ".sig"));
                String signature = new String(sigBytes);
                boolean isVerified = DigitalSignatureUtils.verify(new String(fileBytes), signature, keyPair.getPublic());
                if (isVerified) {
                    outputArea.setText("Signature verified successfully!");
                } else {
                    outputArea.setText("Signature verification failed!");
                }
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CryptoToolGUI gui = new CryptoToolGUI();
            gui.setVisible(true);
        });
    }
}
