import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class ExpenseManager {
    private static final String FILE_PATH_SER = "src\\main\\resources\\ListOfExpenses.ser";
    List<Expense> expensesList = new ArrayList<>();
    int choice;
    String description;
    BigDecimal amount;
    BigDecimal totalAmount = new BigDecimal("0");
    BigDecimal averageAmount = new BigDecimal("0");
    String line;

    void loadListFromFile() { //wczytanie przy uzyciu wbudowanych klas i metod, do pliku .ser (serializable)
        try {
            ObjectInputStream loadingStream = new ObjectInputStream(
                    new FileInputStream(FILE_PATH_SER));
            expensesList = (List<Expense>) loadingStream.readObject();
            loadingStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Wczytywanie pliku nie powiodło się.");
            e.printStackTrace();
        }
    }

    //zapis i odczyt do pliku .ser możliwe tylko na obiektach (kolekcjach tych obiektów)
    // na których zaimplementowano Interferjs Serializable

    void saveListToFile() { //zapis przy użyciu wbudowanych klas i metod do pliku .ser (serializable)
        try {
            ObjectOutputStream savingStream = new ObjectOutputStream(
                    new FileOutputStream(FILE_PATH_SER));
            savingStream.writeObject(expensesList);
            savingStream.close();
        } catch (IOException e) {
            System.err.println("Nie udało się zapisać pliku");
            e.printStackTrace();
        }
    }

    void listMonthExpenses(Scanner scanner, int workMonth) {
        System.out.println("Oto lista Twoich wydatków w miesiącu: " + workMonth);
        for (Expense expense : expensesList) {
            if (expense.getMonth() == workMonth) {
                System.out.println(expense);
            }
        }
        System.out.println("\n0. Powrót");
        System.out.println("1. Sortowanie według nazwy");
        System.out.println("2. Sortowanie od najmniejszej kwoty");
        System.out.println("3. Sortowanie od największej kwoty");
        System.out.println("Wybieram opcję: ");
        choice = scanner.nextInt();

        if (choice == 0) {

        } else if (choice == 1) { //sort by name
            sortByName(expensesList, workMonth);
        } else if (choice == 2) {
            sortByAmountAscending(expensesList, workMonth);
        } else if (choice == 3) {
            sortByAmountDescending(expensesList, workMonth);
        }
    }

    void addExpense(Scanner scanner, int workMonth) {
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
                System.out.println("Podałeś niewłaściwy typ. Spróbuj jeszcze raz.");
            }
        }
        System.out.println("Opisz swój wydatek: ");
        description = scanner.nextLine();

        System.out.println("Podaj kwotę wydatku (użyj przecinka ','): ");
        String parseToString = String.valueOf(scanner.nextDouble());
        amount = new BigDecimal(parseToString);

        Expense myExpense = new Expense(name, description, amount, workMonth);
        expensesList.add(myExpense);
    }

    public void deleteExpense(int workMonth) {
        Scanner scanner2 = new Scanner(System.in);
        for (Expense expense : expensesList) {
            if (expense.getMonth() == workMonth) {
                System.out.println(expense);
            }
        }
        System.out.println("Opisz swój wydatek: ");
        description = scanner2.nextLine();

        System.out.println("Podaj kwotę wydatku (użyj przecinka ','): ");
        String parseToString = String.valueOf(scanner2.nextDouble());
        amount = new BigDecimal(parseToString);

        for (Expense expense : expensesList) {
            if ((expense.getMonth() == workMonth) && (expense.getDescription().equals(description)
                    && (expense.getAmount().equals(amount)))) {
                System.out.println("Usunięto wydatek: " + expense);
                expensesList.remove(expense);
                break;
            }
        }

    }

    void summary(Scanner scanner, int workMonth) {
        System.out.println("0. Powrót");
        System.out.println("1. Miesięczne");
        System.out.println("2. Roczne");
        choice = scanner.nextInt();

        if (choice == 0) {
        } else if (choice == 1) {
            monthlySummary(workMonth);
        } else if (choice == 2) {
            annualSummary();
        }
    }

    void monthlySummary(int workMonth) {
        for (Expense expense : expensesList) {
            if (expense.getMonth() == workMonth) {
                totalAmount = totalAmount.add(expense.getAmount());
            }
        }
        System.out.println("Suma wydatków w tym miesiącu(" + workMonth + "): " + totalAmount);
        totalAmount = new BigDecimal("0");
        int i = 0;
        for (Expense expense : expensesList) {
            if (expense.getMonth() == workMonth) {
                averageAmount = averageAmount.add(expense.getAmount());
                i++;
            }
        }
        averageAmount = averageAmount.divide(new BigDecimal(String.valueOf(i)), 2, RoundingMode.HALF_UP);
        System.out.println("Średnie wydatki w tym miesiącu(" + workMonth + "): " + averageAmount);
        averageAmount = new BigDecimal("0");
    }

    private void annualSummary() {
        System.out.println("Lista wszytkich wydatków w tym roku: ");
        for (Expense expense : expensesList) {
            System.out.println(expense);
        }
        for (Expense expense : expensesList) {
            totalAmount = totalAmount.add(expense.getAmount()) ;
        }
        System.out.println("\nSuma wydatków w tym roku: " + totalAmount);
        totalAmount = new BigDecimal("0");
        for (Expense expense : expensesList) {
            averageAmount = averageAmount.add(expense.getAmount());
        }
        averageAmount = averageAmount.divide(new BigDecimal(expensesList.size()), 2, RoundingMode.HALF_UP);
        System.out.println("Średni jednorazowy wydatek w tym roku: " + averageAmount);
        averageAmount = new BigDecimal("0");
    }

    private static Predicate<Expense> selectByMonth(int workMonth) {
        return expense -> expense.getMonth() == workMonth;
    }

    private static void sortByAmountDescending(List<Expense> expensesList, int workMonth) {
        expensesList.stream()
                .filter(selectByMonth(workMonth))
                .sorted((o1, o2) -> o2.getAmount().compareTo(o1.getAmount()))
                .forEach(System.out::println);
    }

    private static void sortByAmountAscending(List<Expense> expensesList, int workMonth) {
        expensesList.stream()
                .filter(selectByMonth(workMonth))
                .sorted((Comparator.comparing(Expense::getAmount)))
                .forEach(System.out::println);
    }

    private static void sortByName(List<Expense> expensesList, int workMonth) {
        expensesList.stream()
                .filter(selectByMonth(workMonth))
                .sorted(Expense::compareTo)
                .forEach(System.out::println);
//                .forEach(expense -> System.out.printf("%s %.2f\n", expense, expense.getAmount())); //lub String.format()
    }


}
