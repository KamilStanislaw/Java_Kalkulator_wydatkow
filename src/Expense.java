import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Expense implements Comparable<Expense> {
    LinkedList<MonthName> monthNamesList = Arrays.stream(MonthName.values()).collect(Collectors.toCollection(LinkedList::new));
    private final ExpenseType type;
    private final String description;
    private final double amount;
    private final int month;
    private final MonthName monthName;

    public Expense(ExpenseType type, String description, double amount, int month) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.month = month;
        this.monthName = monthNamesList.get(month - 1);
    }


    public ExpenseType getType() {
        return type;
    }

    public String getDescription() {
        return description;
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
        return Double.compare(amount, expense.amount) == 0 && month == expense.month && type == expense.type && Objects.equals(description, expense.description) && monthName == expense.monthName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, description, amount, month, monthName);
    }

    public String toString() {
        return "MIESI¥C: " + monthName + ", " + type + ": " + description + " - ";
    }

    @Override
    public int compareTo(Expense o) {
        return this.getType().toString().compareTo(o.getType().toString());
    }
}
