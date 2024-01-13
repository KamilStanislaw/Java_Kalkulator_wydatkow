import java.text.DecimalFormat;
import java.util.*;

public class ExpenseApp {
    public void start() {
        List<Expense> expensesList = new ArrayList<>();
        List<Expense> expensesToFilterBy = new ArrayList<>();

        DecimalFormat format = new DecimalFormat("##.00");

        int workMonth;
        int choice;
        String description;
        double amount;
        double totalAmount = 0;
        double averageAmount = 0;
        String line;

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
                    System.out.println("3. Podsumowanie");
                    System.out.println("4. Wyj�cie");
                    System.out.println("Wybieram opcj�: ");
                    choice = scanner.nextInt();

                    if (choice == 0) {
                        break;
                    } else if (choice == 1) {
                        System.out.println("Oto lista Twoich wydatk�w w miesi�cu: " + workMonth);

                        for (Expense expense : expensesList) {
                            if (expense.getMonth() == workMonth) {
                                System.out.println(expense + format.format(expense.getAmount()));
                            }
                        }
                        System.out.println("\n0. Powr�t");
                        System.out.println("1. Sortowanie wed�ug nazwy");
                        System.out.println("2. Sortowanie od najmniejszej kwoty");
                        System.out.println("3. Sortowanie od najwi�kszej kwoty");
                        System.out.println("Wybieram opcj�: ");
                        choice = scanner.nextInt();

                        if (choice == 1) { //sort by name
                            sortByName(expensesList, workMonth, expensesToFilterBy, format);
                        } else if (choice == 2) {
                            sortByAmountAscending(expensesList, workMonth, expensesToFilterBy, format);
                        } else if (choice == 3) {
                            sortByAmountDescending(expensesList, workMonth, expensesToFilterBy, format);
                        }
                    } else if (choice == 2) {
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
                                System.out.println("Poda�e� niew�a�ciwy typ. Spr�buj jeszcze raz.");
                            }
                        }
                        System.out.println("Opisz sw�j wydatek: ");
                        description = scanner.nextLine();

                        System.out.println("Podaj kwot� wydatku (u�yj przecinka ','): ");
                        amount = scanner.nextDouble(); //wzi�� w p�tle, bo si� przerywa!!!!

                        Expense myExpense = new Expense(name, description, amount, workMonth);
                        expensesList.add(myExpense);
                    } else if (choice == 3) {
                        System.out.println("0. Powr�t");
                        System.out.println("1. Miesi�czne");
                        System.out.println("2. Roczne");
                        choice = scanner.nextInt();

                        if (choice == 0) {
                        } else if (choice == 1) {
                            for (Expense expense : expensesList) {
                                if (expense.getMonth() == workMonth) {
                                    totalAmount += expense.getAmount();
                                }
                            }
                            System.out.println("Suma wydatk�w w tym miesi�cu(" + workMonth + "): " + format.format(totalAmount));
                            totalAmount = 0;

                            int i = 0;
                            for (Expense expense : expensesList) {
                                if (expense.getMonth() == workMonth) {
                                    averageAmount += expense.getAmount();
                                    i++;
                                }
                            }
                            averageAmount /= i;
                            System.out.println("�rednie wydatki w tym miesi�cu(" + workMonth + "): " + format.format(averageAmount));
                            averageAmount = 0;

                        } else if (choice == 2) {
                            System.out.println("Lista wszytkich wydatk�w w tym roku: ");
                            for (Expense expense : expensesList) {
                                System.out.println(expense + format.format(expense.getAmount()));
                            }

                            for (Expense expense : expensesList) {
                                totalAmount += expense.getAmount();
                            }
                            System.out.println("\nSuma wydatk�w w tym roku: " + format.format(totalAmount));
                            totalAmount = 0;

                            for (Expense expense : expensesList) {
                                averageAmount += expense.getAmount();
                            }
                            averageAmount /= expensesList.size();
                            System.out.println("�redni jednorazowy wydatek w tym roku: " + format.format(averageAmount));
                            averageAmount = 0;
                        }
                    } else if (choice == 4) {
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

    private static void filterExpenseByMonth(List<Expense> expensesList, int workMonth, List<Expense> expensesNames) {
        expensesList.stream()
                .filter(expense -> expense.getMonth() == workMonth)
                .forEach(expensesNames::add);
    }

    private static void sortByAmountDescending(List<Expense> expensesList, int workMonth, List<Expense> expensesNames, DecimalFormat format) {
        filterExpenseByMonth(expensesList, workMonth, expensesNames);

        expensesNames.stream()
                .sorted((o1, o2) -> Double.compare(o2.getAmount(), o1.getAmount()))
                .forEach(expense -> System.out.println(expense + format.format(expense.getAmount())));

        expensesNames.removeAll(expensesNames);
    }

    private static void sortByAmountAscending(List<Expense> expensesList, int workMonth, List<Expense> expensesNames, DecimalFormat format) {
        filterExpenseByMonth(expensesList, workMonth, expensesNames);

        expensesNames.stream()
                .sorted(Comparator.comparingDouble(expense -> expense.getAmount()))
                .forEach(expense -> System.out.println(expense + format.format(expense.getAmount())));

        expensesNames.removeAll(expensesNames);
    }

    private static void sortByName(List<Expense> expensesList, int workMonth, List<Expense> expensesNames, DecimalFormat format) {
        filterExpenseByMonth(expensesList, workMonth, expensesNames);

        expensesNames.stream()
                .sorted(Expense::compareTo)
                .forEach(expense -> System.out.println(expense + format.format(expense.getAmount())));

        expensesNames.removeAll(expensesNames);
    }
}
