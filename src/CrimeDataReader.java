import java.io.*;
import java.util.*;

public class CrimeDataReader {
    public static List<CrimeIncident> readCrimeData(String filePath) {
        List<CrimeIncident> incidents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                incidents.add(new CrimeIncident(
                        values[3], values[4], values[5], values[6], values[7], values[17] // Assume relevant columns
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return incidents;
    }
}
