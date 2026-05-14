package org.example;

import technology.tabula.RectangularTextContainer;
import java.util.ArrayList;
import java.util.List;

public class PdfParser {

    static List<String> getRowData(List<RectangularTextContainer> row) {

        List<String> cells = new ArrayList<>();

        /*
         * Build complete row first
         */
        for (RectangularTextContainer cell : row) {

            String text = cell.getText()
                    .replace("\r", "")
                    .replace("\n", "")
                    .replace(",", "")
                    .trim();

            // remove commas only from numeric values
            if (text.matches("[0-9,]+\\.?[0-9]*"))
                text = text.replace(",", "");

            cells.add(text);
        }

        /*
         * Skip repeated page headers
         */
        boolean isHeader = (cells.contains("Value Date") || cells.contains("ValueDate"))
                        && cells.contains("Description")
                        && cells.contains("Withdrawal")
                        || cells.contains("BALANCE FORWARD");

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
