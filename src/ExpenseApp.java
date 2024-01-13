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

        System.out.println("Witaj w aplikacji do zarz¹dzania wydatkami.");

        while (true) {
            System.out.println("Wybierz miesi¹c [1-12]");
            Scanner scanner = new Scanner(System.in);
            try {
                workMonth = scanner.nextInt();
                if (workMonth > 12 || workMonth < 1) {
                    System.out.println("Nieprawid³owa wartoœæ. Spróbuj ponownie");
                }

                while (true) {
                    System.out.println("");
                    System.out.println("0. Powrót do wyboru miesi¹ca");
                    System.out.println("1. Lista wydatków");
                    System.out.println("2. Dodaj wydatek");
                    System.out.println("3. Podsumowanie");
                    System.out.println("4. Wyjœcie");
                    System.out.println("Wybieram opcjê: ");
                    choice = scanner.nextInt();

                    if (choice == 0) {
                        break;
                    } else if (choice == 1) {
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
                                System.out.println("Poda³eœ niew³aœciwy typ. Spróbuj jeszcze raz.");
                            }
                        }
                        System.out.println("Opisz swój wydatek: ");
                        description = scanner.nextLine();

                        System.out.println("Podaj kwotê wydatku (u¿yj przecinka ','): ");
                        amount = scanner.nextDouble(); //wzi¹æ w pêtle, bo siê przerywa!!!!

                        Expense myExpense = new Expense(name, description, amount, workMonth);
                        expensesList.add(myExpense);
                    } else if (choice == 3) {
                        System.out.println("0. Powrót");
                        System.out.println("1. Miesiêczne");
                        System.out.println("2. Roczne");
                        choice = scanner.nextInt();

                        if (choice == 0) {
                        } else if (choice == 1) {
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

                        } else if (choice == 2) {
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
                    } else if (choice == 4) {
                        System.out.println("Do zobaczenia!");
                        scanner.close();
                        System.exit(0);
                    } else {
                        System.out.println("Wybra³eœ nieprawid³ow¹ opcjê. Spróbuj ponownie.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Nieprawid³owa wartoœæ. Spróbuj ponownie.");
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
