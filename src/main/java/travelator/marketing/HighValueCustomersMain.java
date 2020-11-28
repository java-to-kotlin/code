package travelator.marketing;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class HighValueCustomersMain {

    public static void main(String[] args) throws IOException {
        try (
            var reader = new InputStreamReader(System.in);
            var writer = new OutputStreamWriter(System.out)
        ) {
            HighValueCustomersReport.generate(reader, writer);
        }
    }
}