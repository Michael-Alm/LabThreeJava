import javax.swing.*;
import java.awt.*;

// Panel to display details about a selected crime incident
public class DetailsPanel extends JPanel {
    private JLabel detailsLabel;

    // Constructor to initialize the details panel
    public DetailsPanel() {
        setLayout(new BorderLayout());
        detailsLabel = new JLabel("Select an incident to see details.");
        add(detailsLabel, BorderLayout.CENTER); // Add label to the center of the panel
    }

    // Method to update and show details of the selected incident
    public void showDetails(CrimeIncident incident) {
        detailsLabel.setText("<html>Details:<br>Offense: " + incident.getOffense() + "<br>Shift: " + incident.getShift()
                + "<br>Method: " + incident.getMethod() + "<br>Block: " + incident.getBlock() + "</html>");
    }
}
