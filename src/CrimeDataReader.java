import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// Class responsible for reading crime data from an InputStream
public class CrimeDataReader {

    // Reads crime data and returns a list of CrimeIncident objects
    public static List<CrimeIncident> readCrimeData(InputStream inputStream) {
        List<CrimeIncident> incidents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Create a new CrimeIncident and add it to the list
                incidents.add(new CrimeIncident(
                        values[3], // Report Date
                        values[4], // Shift
                        values[5], // Method
                        values[6], // Offense
                        values[7], // Block
                        values[17] // BID
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return incidents; // Return the list of incidents
    }
}
