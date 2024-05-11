import java.util.*;

public class ExpenseApp {
    ExpenseManager expenseManager = new ExpenseManager();
    public void start() {
        expenseManager.loadListFromFile();

        int workMonth;
        int choice;

        System.out.println("Witaj w aplikacji do zarz�dzania wydatkami.");

        while (true) {
            System.out.println("Wybierz miesi�c [1-12]");
            Scanner scanner = new Scanner(System.in);
            try {
                workMonth = scanner.nextInt();
                if (workMonth > 12 || workMonth < 1) {
                    System.out.println("Nieprawid�owa warto��. Spr�buj ponownie");
                }
                while (true) {
                    System.out.println("");
                    System.out.println("0. Powr�t do wyboru miesi�ca");
                    System.out.println("1. Lista wydatk�w");
                    System.out.println("2. Dodaj wydatek");
                    System.out.println("3. Usu� wydatek z listy");
                    System.out.println("4. Podsumowanie");
                    System.out.println("5. Wyj�cie");
                    System.out.println("Wybieram opcj�: ");
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
                        System.out.println("Wybra�e� nieprawid�ow� opcj�. Spr�buj ponownie.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Nieprawid�owa warto��. Spr�buj ponownie.");
            }
        }
    }
}
