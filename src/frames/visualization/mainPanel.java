package frames.visualization;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class is used to generate the title page of the application.
 */
public class mainPanel extends JPanel {
    private Image backgroundImage;

    public mainPanel() {
        //załadowanie obrazu w tle
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/background1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ustawienie layoutu panelu
        setLayout(new GridBagLayout());

        // Tworzenie i konfiguracja etykiety z tytułem
        JLabel titleLabel = new JLabel("Football analyses", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Old Standard", Font.PLAIN, 150));
        titleLabel.setForeground(new Color(28, 28, 28));
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.insets = new Insets(50, 0, 0, 0);
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.weightx = 1;
        gbcTitle.weighty = 0;
        gbcTitle.fill = GridBagConstraints.HORIZONTAL;
        gbcTitle.anchor = GridBagConstraints.PAGE_START;
        add(titleLabel, gbcTitle);

        // Tworzenie i konfiguracja etykiety z informacjami o autorach
        JLabel authorsLabel = new JLabel("<html>Authors:<br>Antoni Kingston<br>Dominika Gimzicka<br>Jan Opala<br>Norbert Frydrysiak</html>");
        authorsLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        authorsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbcAuthors = new GridBagConstraints();
        gbcAuthors.gridx = 0;
        gbcAuthors.gridy = 2;
        gbcAuthors.weightx = 1;
        gbcAuthors.weighty = 0;
        gbcAuthors.anchor = GridBagConstraints.LAST_LINE_END;
        gbcAuthors.insets = new Insets(10, 0, 10, 20);
        add(authorsLabel, gbcAuthors);

        // Tworzenie i konfiguracja etykiety z opisem aplikacji
        JLabel descriptionLabel = new JLabel("<html>The application is designed to generate football analyses.<br>All data used for these analyses have been sourced from the website https://fbref.com/en/</html>");
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        descriptionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbcDescription = new GridBagConstraints();
        gbcDescription.gridx = 0;
        gbcDescription.gridy = 2;
        gbcDescription.weightx = 1;
        gbcDescription.weighty = 0;
        gbcDescription.anchor = GridBagConstraints.LAST_LINE_START;
        gbcDescription.insets = new Insets(10, 20, 10, 0);
        add(descriptionLabel, gbcDescription);
    }

    /**
     * This method is used to place a background image in the application.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Ustawienie przezroczystości
            Graphics2D g2d = (Graphics2D) g.create();
            AlphaComposite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
            g2d.setComposite(alphaComp);

            // Rysowanie obrazu w tle z zastosowaniem przezroczystości
            g2d.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

            g2d.dispose();
        }
    }
}