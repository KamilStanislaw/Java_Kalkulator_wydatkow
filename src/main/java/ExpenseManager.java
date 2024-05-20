import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Predicate;

public class ExpenseManager {
    private static final String FILE_PATH_SER = "src\\main\\resources\\ListOfExpenses.ser";
//    private static final String FILE_PATH_SER = "~\\ListOfExpenses.ser".replace("~", System.getProperty("user.home"));
    List<Expense> expensesList = new ArrayList<>();
    List<Expense> tempExpensesList = new LinkedList<>();
    int choice;
    String description;
    BigDecimal amount;
    BigDecimal totalAmount = new BigDecimal("0");
    BigDecimal averageAmount = new BigDecimal("0");
    String line;

    void loadListFromFile() { //wczytanie przy uzyciu wbudowanych klas i metod, do pliku .ser (serializable)
        try {
            System.out.println();
            System.out.println("Witaj w aplikacji do zarządzania wydatkami!");
            System.out.println("-------------------------------------------");
            System.out.println("Wczytuję listę Twoich wydatków...");
            System.out.println("-------------------------------------------");
            ObjectInputStream loadingStream = new ObjectInputStream(
                    new FileInputStream(FILE_PATH_SER));
            expensesList = (List<Expense>) loadingStream.readObject();
            loadingStream.close();
            System.out.println("Sukces! Zaczynajmy!");
            System.out.println("-------------------------------------------");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Wczytywanie pliku nie powiodło się.");
            System.err.println("Najwidoczniej nie masz jeszcze zapisanych wydatków, lub plik jest uszkodzony.");
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

    void listMonthExpenses(Scanner scanner, int workMonth, int year) {
        System.out.println("Oto lista Twoich wydatków w miesiącu: " + workMonth);
        for (Expense expense : expensesList) {
            if ((expense.getMonth() == workMonth) && (expense.getYear() == year)) {
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
            sortByName(expensesList, workMonth, year);
        } else if (choice == 2) {
            sortByAmountAscending(expensesList, workMonth, year);
        } else if (choice == 3) {
            sortByAmountDescending(expensesList, workMonth, year);
        }
    }

    void addExpense(Scanner scanner, int workMonth, int year) {
        ExpenseType name = null;
        scanner.nextLine();
        System.out.println("Podaj typ wydatku (dom, jedzenie, rozrywka, edukacja, auto, inny): ");
        while (true) {
            line = scanner.nextLine();
            line = line.toUpperCase();
            if ("DOM".equals(line) || "JEDZENIE".equals(line) || "ROZRYWKA".equals(line) || "EDUKACJA".equals(line) || "AUTO".equals(line) || "INNY".equals(line)) {
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

        Expense myExpense = new Expense(name, description, amount, workMonth, year);
        expensesList.add(myExpense);
    }

    public void deleteExpense(int workMonth, int year) {
        Scanner scanner2 = new Scanner(System.in);
        for (Expense expense : expensesList) {
            if ((expense.getMonth() == workMonth) && (expense.getYear() == year)) {
                System.out.println(expense);
            }
        }
        System.out.println("Opis wydatku do usunięcia: ");
        description = scanner2.nextLine();

        System.out.println("Podaj kwotę wydatku (użyj przecinka ','): ");
        String parseToString = String.valueOf(scanner2.nextDouble());
        amount = new BigDecimal(parseToString);

        for (Expense expense : expensesList) {
            if ((expense.getMonth() == workMonth) && (expense.getDescription().equals(description))
                    && (expense.getAmount().equals(amount)) && (expense.getYear() == year)) {
                System.out.println("Usunięto wydatek: " + expense);
                expensesList.remove(expense);
                break;
            }
        }

    }

    void summary(Scanner scanner, int workMonth, int year) {
        System.out.println("0. Powrót");
        System.out.println("1. Miesięczne");
        System.out.println("2. Roczne");
        choice = scanner.nextInt();

        if (choice == 0) {
        } else if (choice == 1) {
            monthlySummary(workMonth, year);
        } else if (choice == 2) {
            annualSummary(year);
        }
    }

    void monthlySummary(int workMonth, int year) {
        for (Expense expense : expensesList) {
            if ((expense.getMonth() == workMonth) && (expense.getYear() == year)) {
                totalAmount = totalAmount.add(expense.getAmount());
                tempExpensesList.add(expense);
            }
        }
        System.out.println("Suma wydatków w tym miesiącu(" + workMonth + "): " + totalAmount);
        totalAmount = new BigDecimal("0");

        for (Expense expense : tempExpensesList) {
            averageAmount = averageAmount.add(expense.getAmount());
        }

        if (!tempExpensesList.isEmpty()) {
            averageAmount = averageAmount.divide(new BigDecimal(tempExpensesList.size()), 2, RoundingMode.HALF_UP);
            System.out.println("Średnie wydatki w tym miesiącu(" + workMonth + "): " + averageAmount);
            averageAmount = new BigDecimal("0");
        }
        tempExpensesList.clear();
    }

    private void annualSummary(int year) {
        System.out.println("Lista wszytkich wydatków w tym roku: ");
        for (Expense expense : expensesList) {
            if (expense.getYear() == year) {
                tempExpensesList.add(expense);
                System.out.println(expense);
            }
        }
        for (Expense expense : tempExpensesList) {
            totalAmount = totalAmount.add(expense.getAmount()) ;
        }
        System.out.println("\nSuma wydatków w tym roku: " + totalAmount);
        totalAmount = new BigDecimal("0");
        for (Expense expense : tempExpensesList) {
                averageAmount = averageAmount.add(expense.getAmount());
        }

        if (!tempExpensesList.isEmpty()) {
            averageAmount = averageAmount.divide(new BigDecimal(tempExpensesList.size()), 2, RoundingMode.HALF_UP);
            System.out.println("Średni jednorazowy wydatek w tym roku: " + averageAmount);
            averageAmount = new BigDecimal("0");
        }
        tempExpensesList.clear();
    }

    private static Predicate<Expense> selectByMonth(int workMonth) {
        return expense -> expense.getMonth() == workMonth;
    }

    private static void sortByAmountDescending(List<Expense> expensesList, int workMonth, int year) {
        expensesList.stream()
                .filter(selectByMonth(workMonth))
                .filter(expense -> expense.getYear() == year)
                .sorted((o1, o2) -> o2.getAmount().compareTo(o1.getAmount()))
                .forEach(System.out::println);
    }

    private static void sortByAmountAscending(List<Expense> expensesList, int workMonth, int year) {
        expensesList.stream()
                .filter(selectByMonth(workMonth))
                .filter(expense -> expense.getYear() == year)
                .sorted((Comparator.comparing(Expense::getAmount)))
                .forEach(System.out::println);
    }

    private static void sortByName(List<Expense> expensesList, int workMonth, int year) {
        expensesList.stream()
                .filter(selectByMonth(workMonth))
                .filter(expense -> expense.getYear() == year)
                .sorted(Expense::compareTo)
                .forEach(System.out::println);
//                .forEach(expense -> System.out.printf("%s %.2f\n", expense, expense.getAmount())); //lub String.format()
    }

}
