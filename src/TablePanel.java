import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Panel to display a table of crime incidents
public class TablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    // Constructor to initialize the table panel with a list of incidents
    public TablePanel(List<CrimeIncident> incidents) {
        setLayout(new BorderLayout());
        String[] columnNames = {"Report Date", "Shift", "Method", "Offense", "Block"};
        tableModel = new DefaultTableModel(columnNames, 0); // Initialize table model with column names
        table = new JTable(tableModel);

        populateTable(incidents); // Populate the table with incident data
        add(new JScrollPane(table), BorderLayout.CENTER); // Add the table with scroll functionality
    }

    // Method to populate the table with crime incident data
    private void populateTable(List<CrimeIncident> incidents) {
        tableModel.setRowCount(0); // Clear the table before populating
        for (CrimeIncident incident : incidents) {
            tableModel.addRow(new Object[]{
                    incident.getReportDate(), incident.getShift(), incident.getMethod(), incident.getOffense(), incident.getBlock()
            });
        }
    }

    // Method to refresh the table with updated incident data
    public void refreshTable(List<CrimeIncident> incidents) {
        populateTable(incidents); // Repopulate the table with the new incidents
    }

    // Getter to access the JTable object
    public JTable getTable() {
        return table;
    }
}
