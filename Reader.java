import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader {

    public static List<List<String>> read() throws IOException {
        List<List<String>> records = new ArrayList<>();
        try 
            (BufferedReader br = new BufferedReader(new FileReader("customer-1234567-ledger.csv"))) {
            String line;
            String headerLine = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.replaceAll("\"", "").split(",");
                records.add(Arrays.asList(values));
            }
        }
        return records;
    }
}
    
