package org.example;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.ObjectExtractor;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filepath = "C:\\Users\\omkar\\IdeaProjects\\parse-pdf\\pdfsource\\eStatement-2025_11.pdf";
        try {
            File file = new File(filepath);
            PDDocument document = Loader.loadPDF(file);
            ObjectExtractor extractor = new ObjectExtractor(document);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            int totalPages = document.getNumberOfPages();

            for (int i = 1; i <= totalPages; i++) {
                List<Table> tables = sea.extract(extractor.extract(i));

                for (Table table : tables) {
                    List<List<RectangularTextContainer>> rows = table.getRows();

                    for (List<RectangularTextContainer> row : rows) {
                        List<String> cells = getRowData(row);

                        if (cells != null) {
                            System.out.println("Date: " + cells.get(0) +
                                    "\nValue Date: " + cells.get(1) +
                                    "\nDescription: " + cells.get(2) +
                                    "\nCheque: " + cells.get(3) +
                                    "\nDeposit: " + cells.get(4) +
                                    "\nWithdrawal: " + cells.get(5) +
                                    "\nBalance: " + cells.get(6) + "\n"
                            );
                        }
                    }
                }
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getRowData(List<RectangularTextContainer> row) {

        List<String> cells = new ArrayList<>();

        /*
         * Build complete row first
         */
        for (RectangularTextContainer cell : row) {

            String text = cell.getText()
                    .replace("\r", "")
                    .replace("\n", "")
                    .trim();

            // remove commas only from numeric values
            if (text.matches("[0-9,]+\\.?[0-9]*"))
                text = text.replace(",", "");

            cells.add(text);
        }

        /*
         * Skip repeated page headers
         */
        boolean isHeader = cells.contains("Value Date") || cells.contains("ValueDate")
                        && cells.contains("Description")
                        && cells.contains("Withdrawal");

        if (isHeader)
            return null;

        /*
         * Skip TOTAL row
         */
        boolean isTotal = cells
                .stream()
                .anyMatch(c -> c.equalsIgnoreCase("Total"));

        if (isTotal)
            return null;

        /*
         * Merge split balance column
         */
        if (cells.size() >= 8) {
            String secondLast = cells.get(cells.size() - 2);
            String last = cells.get(cells.size() - 1);

            if (secondLast.matches("\\d+")
                    && last.matches("\\d+\\.\\d+")) {

                String mergedBalance = secondLast + last;

                cells.remove(cells.size() - 1);
                cells.set(cells.size() - 1, mergedBalance);
            }
        }

        /*
         * Ensure exactly 7 columns
         */
        while (cells.size() < 7)
            cells.add("");

        return cells;
    }
}