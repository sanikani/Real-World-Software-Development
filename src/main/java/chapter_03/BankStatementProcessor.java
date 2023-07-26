package chapter_03;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class BankStatementProcessor {
    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public double summarizeTransactions(final BankTransactionSummarizer bankTransactionSummarizer) {
        double result = 0;
        for (BankTransaction bankTransaction : bankTransactions) {
            result = bankTransactionSummarizer.summarize(result, bankTransaction);
        }
        return result;
    }

    public double calculateTotalAmount() {
        double total = 0;
        for (final BankTransaction bankTransaction : bankTransactions) {
            total += bankTransaction.getAmount();
        }
        return total;
    }

    public double calculateTotalInMonth(final Month month) {
        return summarizeTransactions((acc, bankTransaction) ->
                bankTransaction.getDate().getMonth() == month ? acc + bankTransaction.getAmount() : acc);
    }

    public double calculateTotalForCategory(final String category) {
        return summarizeTransactions((acc, bankTransaction) ->
                bankTransaction.getDescription().equals(category) ? acc + bankTransaction.getAmount() : acc);

    }

    public double calculateMaxInRange(final LocalDate startDate, final LocalDate endDate) {
        double max = Double.MIN_VALUE;
        for (final BankTransaction bankTransaction : bankTransactions) {
            LocalDate date = bankTransaction.getDate();
            if (isCorrectDate(startDate, endDate, date)) {
                if (bankTransaction.getAmount() > max) {
                    max = bankTransaction.getAmount();
                }
            }
        }
        return max;
    }
    public double calculateMinInRange(final LocalDate startDate, final LocalDate endDate) {
        double min = Double.MAX_VALUE;
        for (final BankTransaction bankTransaction : bankTransactions) {
            LocalDate date = bankTransaction.getDate();
            if (isCorrectDate(startDate, endDate, date)) {
                if (bankTransaction.getAmount() < min) {
                    min = bankTransaction.getAmount();
                }
            }
        }
        return min;
    }

    private static boolean isCorrectDate(LocalDate startDate, LocalDate endDate, LocalDate date) {
        return date.isAfter(startDate) && date.isBefore(endDate) || date.isEqual(startDate) || date.isEqual(endDate);
    }

    public void monthHistogram() {
        for (Month month : Month.values()) {
            double totalInMonth = calculateTotalInMonth(month);
            System.out.println(month + " : " + totalInMonth);
        }
    }

    public void descriptionHistogram(){
        Map<String, Double> descriptionMap = new HashMap<>();
        for (BankTransaction bankTransaction : bankTransactions) {
            String key = bankTransaction.getDescription();
            Double value = bankTransaction.getAmount();
            if(descriptionMap.containsKey(key)){
                double existingValue = descriptionMap.get(key);
                descriptionMap.put(key, existingValue + value);
            }else{
                descriptionMap.put(key, value);
            }
        }
        for (Map.Entry<String, Double> entrySet : descriptionMap.entrySet()) {
            System.out.println(entrySet.getKey() + " : " + entrySet.getValue());
        }

    }

    public List<BankTransaction> findTransactions(final BankTransactionFilter bankTransactionFilter) {
        final List<BankTransaction> result = new ArrayList<>();
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransactionFilter.test(bankTransaction)) {
                result.add(bankTransaction);
            }
        }
        return result;
    }

    public List<BankTransaction> findTransactionsGreaterThanEqual(final int amount){
        return findTransactions(bankTransaction -> bankTransaction.getAmount() >= amount);
    }

}
