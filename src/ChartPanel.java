import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartPanel extends JPanel {
    private Map<String, Long> offenseCounts;

    public ChartPanel(List<CrimeIncident> incidents) {
        // Calculate the count of each offense type
        this.offenseCounts = incidents.stream()
                .collect(Collectors.groupingBy(CrimeIncident::getOffense, Collectors.counting()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set up basic chart properties
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int padding = 50;
        int barWidth = (panelWidth - 2 * padding) / offenseCounts.size();
        int maxBarHeight = panelHeight - 2 * padding;

        // Get the maximum offense count for scaling
        long maxCount = offenseCounts.values().stream().max(Long::compare).orElse(1L);

        int x = padding;
        for (Map.Entry<String, Long> entry : offenseCounts.entrySet()) {
            String offense = entry.getKey();
            long count = entry.getValue();

            // Calculate the height of the bar
            int barHeight = (int) ((double) count / maxCount * maxBarHeight);

            // Draw the bar
            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, panelHeight - padding - barHeight, barWidth, barHeight);

            // Draw the offense label
            g2d.setColor(Color.BLACK);
            g2d.drawString(offense, x, panelHeight - padding + 20);

            // Draw the count label above the bar
            g2d.drawString(String.valueOf(count), x, panelHeight - padding - barHeight - 5);

            x += barWidth + 10; // Space between bars
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }
}
