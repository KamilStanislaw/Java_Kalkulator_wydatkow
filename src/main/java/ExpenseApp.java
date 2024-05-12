import java.util.*;

public class ExpenseApp {
    ExpenseManager expenseManager = new ExpenseManager();
    public void start() {
        expenseManager.loadListFromFile();

        int workMonth;
        int choice;
        int year;
        boolean monthMenu = true;

        while (true) {
            System.out.println("Wpisz rok: ");
            Scanner scanner = new Scanner(System.in);
            try {
                year = scanner.nextInt();
                monthMenu = true;
                while (monthMenu) {
                    System.out.println("Wybierz miesiąc [1-12]: ");
                    try {
                        workMonth = scanner.nextInt();
                        if (workMonth > 12 || workMonth < 1) {
                            System.out.println("Nieprawidłowa wartość. Spróbuj ponownie");
                        }
                        while (true) {
                            System.out.println("");
                            System.out.println("0. Powrót do wyboru roku");
                            System.out.println("1. Powrót do wyboru miesiąca");
                            System.out.println("2. Lista wydatków");
                            System.out.println("3. Dodaj wydatek");
                            System.out.println("4. Usuń wydatek z listy");
                            System.out.println("5. Podsumowanie");
                            System.out.println("6. Wyjście");
                            System.out.println("Wybieram opcję: ");
                            choice = scanner.nextInt();

                            if (choice == 0) {
                                monthMenu = false;
                                break;
                            } else if (choice == 1) {
                                break;
                            } else if (choice == 2) {
                                expenseManager.listMonthExpenses(scanner, workMonth, year);
                            } else if (choice == 3) {
                                expenseManager.addExpense(scanner, workMonth, year);
                            } else if (choice == 4) {
                                expenseManager.deleteExpense(workMonth, year);
                            } else if (choice == 5) {
                                expenseManager.summary(scanner, workMonth, year);
                            } else if (choice == 6) {
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
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
            }
        }
    }
}
