import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {
    private JLabel detailsLabel;

    public DetailsPanel() {
        setLayout(new BorderLayout());
        detailsLabel = new JLabel("Select an incident to see details.");
        add(detailsLabel, BorderLayout.CENTER);
    }

    public void showDetails(CrimeIncident incident) {
        detailsLabel.setText("<html>Details:<br>Offense: " + incident.getOffense() + "<br>Shift: " + incident.getShift()
                + "<br>Method: " + incident.getMethod() + "<br>Block: " + incident.getBlock() + "</html>");
    }
}
