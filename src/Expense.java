import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Expense implements Comparable<Expense> {
    private ExpenseType name;
    private double amount;
    private int month;
    private MonthName monthName;

    public Expense(ExpenseType name, double amount, int month) {
        this.name = name;
        this.amount = amount;
        this.month = month;
        this.monthName = Arrays.stream(MonthName.values()).collect(Collectors.toCollection(LinkedList::new)).get(month - 1);
    }


    public ExpenseType getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getMonth() {
        return month;
    }

    public MonthName getMonthName() {
        return monthName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Double.compare(amount, expense.amount) == 0 && month == expense.month && name == expense.name && monthName == expense.monthName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, month, monthName);
    }

    public String toString() {
        return "MIESI¥C: " + monthName + ", " + name + " - ";
    }

    @Override
    public int compareTo(Expense o) {
        return this.getName().toString().compareTo(o.getName().toString());
    }
}
