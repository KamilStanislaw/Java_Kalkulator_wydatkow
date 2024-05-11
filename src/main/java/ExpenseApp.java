import java.util.*;

public class ExpenseApp {
    ExpenseManager expenseManager = new ExpenseManager();
    public void start() {
        expenseManager.loadListFromFile();

        int workMonth;
        int choice;

        System.out.println("Witaj w aplikacji do zarządzania wydatkami.");

        while (true) {
            System.out.println("Wybierz miesiąc [1-12]");
            Scanner scanner = new Scanner(System.in);
            try {
                workMonth = scanner.nextInt();
                if (workMonth > 12 || workMonth < 1) {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie");
                }
                while (true) {
                    System.out.println("");
                    System.out.println("0. Powrót do wyboru miesiąca");
                    System.out.println("1. Lista wydatków");
                    System.out.println("2. Dodaj wydatek");
                    System.out.println("3. Usuń wydatek z listy");
                    System.out.println("4. Podsumowanie");
                    System.out.println("5. Wyjście");
                    System.out.println("Wybieram opcję: ");
                    choice = scanner.nextInt();

                    if (choice == 0) {
                        break;
                    } else if (choice == 1) {
                        expenseManager.listMonthExpenses(scanner, workMonth);
                    } else if (choice == 2) {
                        expenseManager.addExpense(scanner, workMonth);
                    } else if (choice == 3) {
                        expenseManager.deleteExpense(workMonth);
                    } else if (choice == 4) {
                        expenseManager.summary(scanner, workMonth);
                    } else if (choice == 5) {
                        expenseManager.saveListToFile();
                        System.out.println("Do zobaczenia!");
                        scanner.close();
                        System.exit(0);
                    } else {
                        System.out.println("Wybrałeś nieprawidłową opcję. Spróbuj ponownie.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
            }
        }
    }
}
