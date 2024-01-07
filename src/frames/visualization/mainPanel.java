package frames.visualization;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;

public class mainPanel extends JPanel {
    private Image backgroundImage;
    public JButton startButton;

    public mainPanel() {
        // Załaduj obraz w tle
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/image2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ustawienie layoutu panelu na GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Tworzenie i konfiguracja etykiety z tekstem
        JLabel label = new JLabel("Analiza danych piłkarskich", SwingConstants.CENTER);
        label.setFont(new Font("Century Gothic", Font.BOLD, 110));
        label.setForeground(new Color(28,28,28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        add(label, gbc);

        /*
        // Tworzenie i konfiguracja przycisku START
        startButton = new JButton("START");
        startButton.setPreferredSize(new Dimension(320, 130));
        startButton.setFont(new Font("Arial", Font.BOLD, 80));

        startButton.setFocusPainted(false);
        startButton.setForeground(Color.BLACK); // Kolor tekstu przycisku na czarny
        startButton.setOpaque(false); // Ustawienie przycisku jako nieprzezroczystego
        startButton.setContentAreaFilled(false); // Wyłączenie wypełnienia tła
        startButton.setBorder(new LineBorder(Color.BLACK, 7)); // Czarne obramowanie
        startButton.setToolTipText("Kliknij, aby rozpocząć");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(startButton, gbc);
        */
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Ustawienie przezroczystości
            Graphics2D g2d = (Graphics2D) g.create();
            AlphaComposite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
            g2d.setComposite(alphaComp);

            // Rysowanie obrazu w tle z zastosowaniem przezroczystości
            g2d.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

            g2d.dispose();
        }

    }

    public JButton getStartButton() {
        return startButton;
    }
}