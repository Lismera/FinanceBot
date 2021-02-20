package handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader {

    public Reader() {
        // empty constructor
    }

    public List<List<String>> read(String inputFile) throws IOException {
        List<List<String>> records = new ArrayList<>();

        File directory = new File("./");
        String trueDirectory = directory.getAbsolutePath().replace(".","") + inputFile;
                
        try (BufferedReader br = new BufferedReader(new FileReader(trueDirectory))) {
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
    
