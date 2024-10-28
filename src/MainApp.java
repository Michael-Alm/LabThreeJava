import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Main application to visualize crime data
public class MainApp extends JFrame {
    private List<CrimeIncident> incidents;
    private TablePanel tablePanel;
    private StatsPanel statsPanel;
    private ChartPanel chartPanel;
    private DetailsPanel detailsPanel;

    // Constructor to initialize the main application with the list of incidents
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

        // Add the panels to the main window
        add(tablePanel, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.EAST);
        add(detailsPanel, BorderLayout.SOUTH);

        // Create filter panel with checkboxes and toggle button for filtering incidents
        JPanel filterPanel = new JPanel();
        JCheckBox eveningShiftFilter = new JCheckBox("Show Evening Incidents Only");
        JCheckBox theftFilter = new JCheckBox("Show Theft Incidents Only");
        JToggleButton dateSortToggle = new JToggleButton("Sort by Date Descending");

        filterPanel.add(eveningShiftFilter);
        filterPanel.add(theftFilter);
        filterPanel.add(dateSortToggle);
        add(filterPanel, BorderLayout.SOUTH);

        // Add listeners to apply filters when checkboxes or toggle button are selected
        eveningShiftFilter.addActionListener(e -> applyFilters(eveningShiftFilter.isSelected(), theftFilter.isSelected(), dateSortToggle.isSelected()));
        theftFilter.addActionListener(e -> applyFilters(eveningShiftFilter.isSelected(), theftFilter.isSelected(), dateSortToggle.isSelected()));
        dateSortToggle.addActionListener(e -> applyFilters(eveningShiftFilter.isSelected(), theftFilter.isSelected(), dateSortToggle.isSelected()));
    }

    // Method to apply filters and update the data displayed in the panels
    private void applyFilters(boolean eveningShiftOnly, boolean theftOnly, boolean sortByDateDescending) {
        List<CrimeIncident> filteredIncidents = incidents;

        // Filter incidents based on the evening shift checkbox
        if (eveningShiftOnly) {
            filteredIncidents = filteredIncidents.stream()
                    .filter(incident -> "EVENING".equals(incident.getShift()))
                    .collect(Collectors.toList());
        }

        // Filter incidents based on the theft checkbox
        if (theftOnly) {
            filteredIncidents = filteredIncidents.stream()
                    .filter(incident -> "THEFT/OTHER".equals(incident.getOffense()))
                    .collect(Collectors.toList());
        }

        // Sort incidents by date in descending order if the toggle is selected
        if (sortByDateDescending) {
            filteredIncidents = filteredIncidents.stream()
                    .sorted(Comparator.comparing(CrimeIncident::getReportDate).reversed())
                    .collect(Collectors.toList());
        }

        // Update the table, stats, and chart panels with the filtered incidents
        tablePanel.refreshTable(filteredIncidents);
        statsPanel.updateStats(filteredIncidents);
        chartPanel = new ChartPanel(filteredIncidents);  // Recreate chart panel with filtered data
        add(chartPanel, BorderLayout.EAST);
        revalidate(); // Revalidate the layout after making changes
        repaint(); // Repaint to update the UI
    }

    // Main method to start the application
    public static void main(String[] args) {
        InputStream inputStream = MainApp.class.getResourceAsStream("/Crime_Incidents_in_2024.csv");
        if (inputStream == null) {
            System.out.println("Error: Crime_Incidents_in_2024.csv not found in resources.");
            return;
        }

        // Read the incidents from the CSV file and launch the application
        List<CrimeIncident> incidents = CrimeDataReader.readCrimeData(inputStream);
        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp(incidents);
            app.setVisible(true); // Display the application window
        });
    }
}
