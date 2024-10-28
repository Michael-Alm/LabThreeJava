import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainApp extends JFrame {
    private List<CrimeIncident> incidents;
    private TablePanel tablePanel;
    private StatsPanel statsPanel;
    private ChartPanel chartPanel;
    private DetailsPanel detailsPanel;

    public MainApp(List<CrimeIncident> incidents) {
        setTitle("Crime Data Visualization");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.incidents = incidents;
        tablePanel = new TablePanel(incidents);
        statsPanel = new StatsPanel();
        chartPanel = new ChartPanel(incidents);
        detailsPanel = new DetailsPanel();

        statsPanel.updateStats(incidents);

        add(tablePanel, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.EAST);
        add(detailsPanel, BorderLayout.SOUTH);

        // Filters
        JPanel filterPanel = new JPanel();
        JCheckBox eveningShiftFilter = new JCheckBox("Show Evening Incidents Only");
        JCheckBox theftFilter = new JCheckBox("Show Theft Incidents Only");
        JToggleButton dateSortToggle = new JToggleButton("Sort by Date Descending");

        filterPanel.add(eveningShiftFilter);
        filterPanel.add(theftFilter);
        filterPanel.add(dateSortToggle);
        add(filterPanel, BorderLayout.SOUTH);

        // Apply filters based on the selected checkboxes and toggle
        eveningShiftFilter.addActionListener(e -> applyFilters(eveningShiftFilter.isSelected(), theftFilter.isSelected(), dateSortToggle.isSelected()));
        theftFilter.addActionListener(e -> applyFilters(eveningShiftFilter.isSelected(), theftFilter.isSelected(), dateSortToggle.isSelected()));
        dateSortToggle.addActionListener(e -> applyFilters(eveningShiftFilter.isSelected(), theftFilter.isSelected(), dateSortToggle.isSelected()));
    }

    private void applyFilters(boolean eveningShiftOnly, boolean theftOnly, boolean sortByDateDescending) {
        List<CrimeIncident> filteredIncidents = incidents;

        // Apply filters based on the checkbox selections
        if (eveningShiftOnly) {
            filteredIncidents = filteredIncidents.stream()
                    .filter(incident -> "EVENING".equals(incident.getShift()))
                    .collect(Collectors.toList());
        }

        if (theftOnly) {
            filteredIncidents = filteredIncidents.stream()
                    .filter(incident -> "THEFT/OTHER".equals(incident.getOffense()))
                    .collect(Collectors.toList());
        }

        // Sort by date in descending order if toggle is selected
        if (sortByDateDescending) {
            filteredIncidents = filteredIncidents.stream()
                    .sorted(Comparator.comparing(CrimeIncident::getReportDate).reversed())
                    .collect(Collectors.toList());
        }

        // Update the table, stats, and chart panels with the filtered data
        tablePanel.refreshTable(filteredIncidents);
        statsPanel.updateStats(filteredIncidents);
        chartPanel = new ChartPanel(filteredIncidents);  // Recreate chart panel with filtered data
        add(chartPanel, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        InputStream inputStream = MainApp.class.getResourceAsStream("/Crime_Incidents_in_2024.csv");
        if (inputStream == null) {
            System.out.println("Error: Crime_Incidents_in_2024.csv not found in resources.");
            return;
        }

        List<CrimeIncident> incidents = CrimeDataReader.readCrimeData(inputStream);
        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp(incidents);
            app.setVisible(true);
        });
    }
}
