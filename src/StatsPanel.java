import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsPanel extends JPanel {
    private JLabel totalLabel, topOffenseLabel, shiftDistributionLabel;

    public StatsPanel() {
        setLayout(new GridLayout(3, 1));
        totalLabel = new JLabel("Total Incidents: ");
        topOffenseLabel = new JLabel("Top Offense: ");
        shiftDistributionLabel = new JLabel("Shift Distribution: ");
        add(totalLabel);
        add(topOffenseLabel);
        add(shiftDistributionLabel);
    }

    public void updateStats(List<CrimeIncident> incidents) {
        totalLabel.setText("Total Incidents: " + incidents.size());

        Map<String, Long> offenseCounts = incidents.stream()
                .collect(Collectors.groupingBy(CrimeIncident::getOffense, Collectors.counting()));
        String topOffense = offenseCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("N/A");

        topOffenseLabel.setText("Top Offense: " + topOffense);

        Map<String, Long> shiftCounts = incidents.stream()
                .collect(Collectors.groupingBy(CrimeIncident::getShift, Collectors.counting()));
        String shiftDistribution = shiftCounts.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(", "));

        shiftDistributionLabel.setText("Shift Distribution: " + shiftDistribution);
    }
}
