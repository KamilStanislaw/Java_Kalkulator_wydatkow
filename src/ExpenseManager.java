import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ExpenseManager {
    List<Expense> expensesList = new ArrayList<>();
    DecimalFormat format = new DecimalFormat("##.00");
    int choice;
    String description;
    double amount;
    double totalAmount = 0;
    double averageAmount = 0;
    String line;

    public void listMonthExpenses(Scanner scanner, int workMonth) {
        System.out.println("Oto lista Twoich wydatków w miesi¹cu: " + workMonth);
        for (Expense expense : expensesList) {
            if (expense.getMonth() == workMonth) {
                System.out.println(expense + format.format(expense.getAmount()));
            }
        }
        System.out.println("\n0. Powrót");
        System.out.println("1. Sortowanie wed³ug nazwy");
        System.out.println("2. Sortowanie od najmniejszej kwoty");
        System.out.println("3. Sortowanie od najwiêkszej kwoty");
        System.out.println("Wybieram opcjê: ");
        choice = scanner.nextInt();

        if (choice == 0) {

        } else if (choice == 1) { //sort by name
            sortByName(expensesList, workMonth, format);
        } else if (choice == 2) {
            sortByAmountAscending(expensesList, workMonth, format);
        } else if (choice == 3) {
            sortByAmountDescending(expensesList, workMonth, format);
        }
    }

    public void addExpense(Scanner scanner, int workMonth) {
        ExpenseType name = null;
        scanner.nextLine();
        System.out.println("Podaj typ wydatku (dom, jedzenie, rozrywka, auto, inny): ");
        while (true) {
            line = scanner.nextLine();
            line = line.toUpperCase();
            if ("DOM".equals(line) || "JEDZENIE".equals(line) || "ROZRYWKA".equals(line) || "AUTO".equals(line) || "INNY".equals(line)) {
                name = ExpenseType.valueOf(line);
                break;
            } else {
                System.out.println("Poda³eœ niew³aœciwy typ. Spróbuj jeszcze raz.");
            }
        }
        System.out.println("Opisz swój wydatek: ");
        description = scanner.nextLine();

        System.out.println("Podaj kwotê wydatku (u¿yj przecinka ','): ");
        amount = scanner.nextDouble();

        Expense myExpense = new Expense(name, description, amount, workMonth);
        expensesList.add(myExpense);
    }

    public void summary(Scanner scanner, int workMonth) {
        System.out.println("0. Powrót");
        System.out.println("1. Miesiêczne");
        System.out.println("2. Roczne");
        choice = scanner.nextInt();

        if (choice == 0) {
        } else if (choice == 1) {
            monthlySummary(workMonth);
        } else if (choice == 2) {
            annualSummary();
        }
    }

    private void monthlySummary(int workMonth) {
        for (Expense expense : expensesList) {
            if (expense.getMonth() == workMonth) {
                totalAmount += expense.getAmount();
            }
        }
        System.out.println("Suma wydatków w tym miesi¹cu(" + workMonth + "): " + format.format(totalAmount));
        totalAmount = 0;
        int i = 0;
        for (Expense expense : expensesList) {
            if (expense.getMonth() == workMonth) {
                averageAmount += expense.getAmount();
                i++;
            }
        }
        averageAmount /= i;
        System.out.println("Œrednie wydatki w tym miesi¹cu(" + workMonth + "): " + format.format(averageAmount));
        averageAmount = 0;
    }

    private void annualSummary() {
        System.out.println("Lista wszytkich wydatków w tym roku: ");
        for (Expense expense : expensesList) {
            System.out.println(expense + format.format(expense.getAmount()));
        }
        for (Expense expense : expensesList) {
            totalAmount += expense.getAmount();
        }
        System.out.println("\nSuma wydatków w tym roku: " + format.format(totalAmount));
        totalAmount = 0;
        for (Expense expense : expensesList) {
            averageAmount += expense.getAmount();
        }
        averageAmount /= expensesList.size();
        System.out.println("Œredni jednorazowy wydatek w tym roku: " + format.format(averageAmount));
        averageAmount = 0;
    }

    private static void sortByAmountDescending(List<Expense> expensesList, int workMonth, DecimalFormat format) {
        expensesList.stream()
                .filter(expense -> expense.getMonth() == workMonth)
                .sorted((o1, o2) -> Double.compare(o2.getAmount(), o1.getAmount()))
                .forEach(expense -> System.out.println(expense + format.format(expense.getAmount())));
    }

    private static void sortByAmountAscending(List<Expense> expensesList, int workMonth, DecimalFormat format) {
        expensesList.stream()
                .filter(expense -> expense.getMonth() == workMonth)
                .sorted(Comparator.comparingDouble(expense -> expense.getAmount()))
                .forEach(expense -> System.out.println(expense + format.format(expense.getAmount())));
    }

    private static void sortByName(List<Expense> expensesList, int workMonth, DecimalFormat format) {
        expensesList.stream()
                .filter(expense -> expense.getMonth() == workMonth)
                .sorted(Expense::compareTo)
                .forEach(expense -> System.out.println(expense + format.format(expense.getAmount())));
    }

}
