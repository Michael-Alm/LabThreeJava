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
        int maxBarHeight = panelHeight - 2 * padding;

        // Dynamic bar width calculation
        int barWidth = Math.max((panelWidth - 2 * padding) / offenseCounts.size() - 10, 20); // Minimum width 20, spacing 10
        int barSpacing = 15; // Space between bars

        // Get the maximum offense count for scaling
        long maxCount = offenseCounts.values().stream().max(Long::compare).orElse(0L);

        // If maxCount is zero (no data to display), skip drawing
        if (maxCount == 0) {
            g2d.drawString("No data to display", panelWidth / 2 - 50, panelHeight / 2);
            return;
        }

        int x = padding;
        for (Map.Entry<String, Long> entry : offenseCounts.entrySet()) {
            String offense = entry.getKey();
            long count = entry.getValue();

            // Calculate the height of the bar
            int barHeight = (int) ((double) count / maxCount * maxBarHeight);

            // Draw the bar
            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, panelHeight - padding - barHeight, barWidth, barHeight);

            // Split offense name if too long (e.g., more than 10 characters) and draw multi-line label
            g2d.setColor(Color.BLACK);
            String[] labelLines = splitLabel(offense, 10); // Adjust character limit as needed
            int labelY = panelHeight - padding + 20;
            for (String line : labelLines) {
                g2d.drawString(line, x, labelY);
                labelY += 15; // Move down for the next line
            }

            // Draw the count label above the bar
            g2d.drawString(String.valueOf(count), x + barWidth / 2 - 10, panelHeight - padding - barHeight - 5);

            x += barWidth + barSpacing; // Adjust x for the next bar position
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 400); // Wider default size
    }

    // Helper function to split label into multiple lines
    private String[] splitLabel(String label, int maxLength) {
        if (label.length() <= maxLength) {
            return new String[]{label}; // No need to split
        }

        // Split label at maxLength boundary, inserting a newline
        int splitIndex = label.lastIndexOf(" ", maxLength); // Try to split at a space
        if (splitIndex == -1) splitIndex = maxLength; // If no space found, split at maxLength

        String line1 = label.substring(0, splitIndex);
        String line2 = label.substring(splitIndex).trim();
        return new String[]{line1, line2};
    }
}
