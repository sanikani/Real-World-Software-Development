package chapter_03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;


public class BankTransactionAnalyzer {
    private static final String RESOURCES = "src/main/resources/";

    public void analyze(final String fileName, final BankStatementParser bankStatementParser) throws IOException{
        final Path path = Paths.get(RESOURCES + fileName);
        final List<String> lines = Files.readAllLines(path);

        final List<BankTransaction> bankTransactions = bankStatementParser.parseLinesFrom(lines);

        final BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);

        collectSummary(bankStatementProcessor);
    }

    private static void collectSummary(final BankStatementProcessor bankStatementProcessor) {
        System.out.println("The total for all transaction is " + bankStatementProcessor.calculateTotalAmount());

        System.out.println("The total for in January is " + bankStatementProcessor.calculateTotalInMonth(Month.JANUARY));

        System.out.println("The total for in February is " + bankStatementProcessor.calculateTotalInMonth(Month.FEBRUARY));

        System.out.println("The total salary received is " + bankStatementProcessor.calculateTotalForCategory("Salary"));

        System.out.println("The max transaction in date is " + bankStatementProcessor.calculateMaxInRange(LocalDate.of(2017, Month.JANUARY, 30), LocalDate.of(2017, Month.FEBRUARY, 2)));

        System.out.println("The min transaction in date is " + bankStatementProcessor.calculateMinInRange(LocalDate.of(2017, Month.JANUARY, 30), LocalDate.of(2017, Month.FEBRUARY, 2)));

        System.out.println("The Histogram in Month is " );

        bankStatementProcessor.monthHistogram();

        System.out.println("The Histogram in Description is " );

        bankStatementProcessor.descriptionHistogram();
    }


}
