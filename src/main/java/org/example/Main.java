package org.example;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.ObjectExtractor;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.example.PdfParser.getRowData;

public class Main {
    public static void main(String[] args) {
        String filepath = "C:\\Users\\omkar\\IdeaProjects\\parse-pdf\\pdfsource\\eStatement-2026_04.pdf";
        try {
            File file = new File(filepath);
            PDDocument document = Loader.loadPDF(file);
            ObjectExtractor extractor = new ObjectExtractor(document);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            int totalPages = document.getNumberOfPages();

            BufferedWriter writer =
                    new BufferedWriter(new FileWriter("C:\\Users\\omkar\\IdeaProjects\\parse-pdf\\pdfsource\\textOutputFiles\\test.csv"));


            System.out.println("Date\tValue Date\tDescription\tCheque\tDeposits\tWithdrawal\tBalance");
            for (int i = 1; i <= totalPages; i++) {
                List<Table> tables = sea.extract(extractor.extract(i));

                for (Table table : tables) {
                    List<List<RectangularTextContainer>> rows = table.getRows();

                    for (List<RectangularTextContainer> row : rows) {
                        List<String> cells = getRowData(row);



                        if (cells != null) {
                            writer.write(cells.get(0) +
                                    ", " + cells.get(1) +
                                    ", " + cells.get(2) +
                                    ", " + cells.get(3) +
                                    ", " + cells.get(4) +
                                    ", " + cells.get(5) +
                                    ", " + cells.get(6) + "\n"
                            );
                        }
                    }
                }
            }
            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}