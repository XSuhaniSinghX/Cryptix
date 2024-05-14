package cryptix;


import cryptix.gui.CryptoToolGUI;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CryptoToolGUI frame = new CryptoToolGUI();
            frame.setVisible(true);
        });
    }
}
