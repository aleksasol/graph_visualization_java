import gui.MainWindow;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = null;
            try {
                window = new MainWindow();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            window.setVisible(true);
        });
    }
}