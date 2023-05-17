import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        List<Expense> expensesList = new ArrayList<>();
        List<Expense> expensesNames = new ArrayList<>();

        DecimalFormat format = new DecimalFormat("##.00");

        int workMonth;
        int choice;
        double amount;
        String name;
        double totalAmount = 0;
        double averageAmount = 0;
        String line;


        while (true) {
            System.out.println("wybierz miesiąc [1-12]");
            Scanner scanner = new Scanner(System.in);
            try {
                workMonth = scanner.nextInt();

                if (workMonth > 12 || workMonth < 1) {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie");
                }

                while (true) {
                    System.out.println("");
                    System.out.println("0. Powrót do wyboru miesiąca");
                    System.out.println("1. Wyświetl wszystkie wydatki");
                    System.out.println("2. Dodaj wydatek");
                    System.out.println("3. Statystyki");
                    System.out.println("Wybieram opcję: ");
                    choice = scanner.nextInt();

                    if (choice == 0) {
                        break;
                    } else if (choice == 1) {
                        

                        System.out.println("Oto lista Twoich wydatków w tym miesiącu");

                        for (Expense expense : expensesList) {
                            if (expense.getMonth() == workMonth) {
                                System.out.printf(format.format(expense));
                            }
                        }
                        System.out.println("\n0. Powrót");
                        System.out.println("1. Sortowanie według nazwy");
//                        System.out.println("2. Sortowanie od najmniejszej kwoty");
//                        System.out.println("3. Sortowanie od największej kwoty");
                        System.out.println("Wybieram opcję: ");
                        choice = scanner.nextInt();

                        if (choice == 0) {
                            break;
                        } else if (choice == 1) {
                            for (Expense expense1 : expensesList) {
                                if (expense1.getName().equals("dom") && expense1.getMonth() == workMonth) {
                                    expensesNames.add(expense1);
                                }
                            }
                            for (Expense expenseHouse : expensesNames) {
                                if (expenseHouse.getName().equals("dom") && expenseHouse.getMonth() == workMonth) {
                                    System.out.printf(format.format(expenseHouse));
                                }
                            }
                            expensesNames.removeAll(expensesNames);

                            for (Expense expense2 : expensesList) {
                                if (expense2.getName().equals("jedzenie") && expense2.getMonth() == workMonth) {
                                    expensesNames.add(expense2);
                                }
                            }
                            for (Expense expenseFood : expensesNames) {
                                if (expenseFood.getName().equals("jedzenie") && expenseFood.getMonth() == workMonth) {
                                    System.out.printf(format.format(expenseFood));
                                }
                            }
                            expensesNames.removeAll(expensesNames);

                            for (Expense expense3 : expensesList) {
                                if (expense3.getName().equals("auto") && expense3.getMonth() == workMonth) {
                                    expensesNames.add(expense3);
                                }
                            }
                            for (Expense expenseCar : expensesNames) {
                                if (expenseCar.getName().equals("auto") && expenseCar.getMonth() == workMonth) {
                                    System.out.println(expenseCar);
                                }
                            }
                            expensesNames.removeAll(expensesNames);

                            for (Expense expense4 : expensesList) {
                                if (expense4.getName().equals("rozrywka") && expense4.getMonth() == workMonth) {
                                    expensesNames.add(expense4);
                                }
                            }
                            for (Expense expenseJoy : expensesNames) {
                                if (expenseJoy.getName().equals("rozrywka") && expenseJoy.getMonth() == workMonth) {
                                    System.out.printf(format.format(expenseJoy));
                                }
                            }
                            expensesNames.removeAll(expensesNames);

                            for (Expense expense5 : expensesList) {
                                if (expense5.getName().equals("inny") && expense5.getMonth() == workMonth) {
                                    expensesNames.add(expense5);
                                }
                            }
                            for (Expense expenseOther : expensesNames) {
                                if (expenseOther.getName().equals("inny") && expenseOther.getMonth() == workMonth) {
                                    System.out.printf(format.format(expenseOther));
                                }
                            }
                            expensesNames.removeAll(expensesNames);
                        }


                    } else if (choice == 2) {
                        scanner.nextLine();
                        System.out.println("Podaj typ wydatku (dom, jedzenie, rozrywka, auto, inny): ");
                        line = scanner.nextLine();
                        line = line.toLowerCase();
                        if (line.equals("dom") || line.equals("jedzenie") || line.equals("rozrywka") || line.equals("auto") || line.equals("inny")) {
                            name = line;
                        } else {
                            System.out.println("Podałeś niewłaściwy typ. Spróbuj jeszcze raz.");
                            break;
                        }

                        System.out.println("Podaj kwotę wydatku: ");
                        amount = scanner.nextDouble();

                        Expense myExpense = new Expense(name, amount, workMonth);
                        expensesList.add(myExpense);

                    } else if (choice == 3) {
                        System.out.println("0. Powrót");
                        System.out.println("1. Miesięczne");
                        System.out.println("2. Roczne");
                        choice = scanner.nextInt();

                        if (choice == 0) {
                            break;
                        } else if (choice == 1) {
                            System.out.printf("Suma wydatków w tym miesiącu[" + format.format(workMonth) + "]: ");
                            for (Expense expense : expensesList) {
                                if (expense.getMonth() == workMonth) {
                                    totalAmount += expense.getAmount();
                                }
                            }
                            System.out.printf(format.format(totalAmount) + "\n");
                            totalAmount = 0;

                            System.out.printf("Średnie wydatki w tym miesiącu[" + format.format(workMonth) + "]: ");
                            int i = 0;
                            for (Expense expense : expensesList) {
                                if (expense.getMonth() == workMonth) {
                                    averageAmount += expense.getAmount();
                                    i++;
                                }
                            }
                            averageAmount /= i;
                            System.out.printf(format.format(averageAmount) + "\n");
                            averageAmount = 0;

                        } else if (choice == 2) {
                            System.out.println("Lista rocznych wydatków: ");
                            for (Expense expense : expensesList) {
                                System.out.printf(format.format(expense));
                            }

                            System.out.println("Suma wydatków w roku: ");
                            for (Expense expense : expensesList) {
                                totalAmount += expense.getAmount();
                            }
                            System.out.printf(format.format(totalAmount  + "\n"));
                            totalAmount = 0;

                            System.out.println("Średnie roczne wydatki: ");

                            for (Expense expense : expensesList) {
                                averageAmount += expense.getAmount();
                            }
                            averageAmount /= expensesList.size();
                            System.out.printf(format.format(averageAmount)  + "\n");
                            averageAmount = 0;
                        }
                    } else {
                        System.out.println("Wybrałeś nieprawidłową opcję. Spróbuj ponownie.");
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
            }
        }
    }
}