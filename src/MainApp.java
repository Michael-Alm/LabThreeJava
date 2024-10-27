import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainApp extends JFrame {
    public MainApp(List<CrimeIncident> incidents) {
        setTitle("Crime Data Visualization");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        TablePanel tablePanel = new TablePanel(incidents);
        StatsPanel statsPanel = new StatsPanel();
        ChartPanel chartPanel = new ChartPanel(incidents);
        DetailsPanel detailsPanel = new DetailsPanel();

        statsPanel.updateStats(incidents);

        add(tablePanel, BorderLayout.WEST);
        add(statsPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.EAST);
        add(detailsPanel, BorderLayout.SOUTH);

        tablePanel.getTable().getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tablePanel.getTable().getSelectedRow();
            if (selectedRow >= 0) {
                CrimeIncident selectedIncident = incidents.get(selectedRow);
                detailsPanel.showDetails(selectedIncident);
            }
        });
    }

    public static void main(String[] args) {
        List<CrimeIncident> incidents = CrimeDataReader.readCrimeData("C:/Users/mikea/IdeaProjects/LabThreeJava/src/Crime_Incidents_in_2024.csv");
        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp(incidents);
            app.setVisible(true);
        });
    }
}
