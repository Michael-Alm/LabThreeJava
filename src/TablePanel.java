import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public TablePanel(List<CrimeIncident> incidents) {
        setLayout(new BorderLayout());
        String[] columnNames = {"Report Date", "Shift", "Method", "Offense", "Block"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        populateTable(incidents);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void populateTable(List<CrimeIncident> incidents) {
        tableModel.setRowCount(0); // Clear table
        for (CrimeIncident incident : incidents) {
            tableModel.addRow(new Object[]{
                    incident.getReportDate(), incident.getShift(), incident.getMethod(), incident.getOffense(), incident.getBlock()
            });
        }
    }

    public void refreshTable(List<CrimeIncident> incidents) {
        populateTable(incidents);
    }

    public JTable getTable() {
        return table;
    }
}
