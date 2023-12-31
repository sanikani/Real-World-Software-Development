package chapter_03;

import java.io.IOException;

public class MainApplication {
    public static void main(final String ... args) throws IOException {

        final BankTransactionAnalyzer bankTransactionAnalyzer = new BankTransactionAnalyzer();

        final BankStatementParser bankStatementParser = new BankStatementCSVParser();

        bankTransactionAnalyzer.analyze("bank-data-simple.csv", bankStatementParser);
    }
}
