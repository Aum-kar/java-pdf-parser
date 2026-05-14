package org.example;
import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filepath = "C:\\Users\\omkar\\IdeaProjects\\parse-pdf\\pdfsource\\eStatement-2024_06.pdf";
        try {
            File file = new File(filepath);
            List<Transaction> transactions = PdfParser.processTransactions(file);

            for(Transaction transaction : transactions)
                System.out.println(transaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
