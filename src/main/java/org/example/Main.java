package org.example;
import java.io.File;

import static org.example.PdfParser.printStatementSummary;

public class Main {
    public static void main(String[] args) {
        File directory = new File("C:\\Users\\omkar\\IdeaProjects\\parse-pdf\\pdfsource\\");

        File[] files = directory.listFiles(f -> f.getName().endsWith(".pdf"));
        assert files != null;
        System.out.println("fileName, deposit, withdrawal, balance");
        for (File file : files) {
            printStatementSummary(file);
        }
    }
}
