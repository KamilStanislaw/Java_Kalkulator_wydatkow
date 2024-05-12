import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Expense implements Comparable<Expense>, MonthNumberToName, Serializable {
    private final ExpenseType type;
    private final String description;
    private final BigDecimal amount;
    private final int month;
    private final MonthName monthName;
    private int year;

    public Expense(ExpenseType type, String description, BigDecimal amount, int month, int year) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.month = month;
        this.monthName = MonthNumberToName.monthNumberToName(month);
        this.year = year;
    }

    public ExpenseType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getMonth() {
        return month;
    }

    public MonthName getMonthName() {
        return monthName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return month == expense.month && year == expense.year && type == expense.type && Objects.equals(description, expense.description) && Objects.equals(amount, expense.amount) && monthName == expense.monthName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, description, amount, month, monthName, year);
    }

    public String toString() {
        return "MIESIĄC: " + monthName + ", " + type + ": " + description + " - " + amount;
    }

    @Override
    public int compareTo(Expense o) {
        return this.getType().toString().compareTo(o.getType().toString());
    }

}
