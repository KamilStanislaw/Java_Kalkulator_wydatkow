import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Expense implements Comparable<Expense>, MonthNumberToName, Serializable {
    private final ExpenseType type;
    private final String description;
    private final BigDecimal amount;
    private final int month;
    private final MonthName monthName;

    public Expense(ExpenseType type, String description, BigDecimal amount, int month) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.month = month;
        this.monthName = MonthNumberToName.monthNumberToName(month);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return month == expense.month && type == expense.type && Objects.equals(description, expense.description) && Objects.equals(amount, expense.amount) && monthName == expense.monthName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, description, amount, month, monthName);
    }

    public String toString() {
        return "MIESI�C: " + monthName + ", " + type + ": " + description + " - " + amount;
    }

    @Override
    public int compareTo(Expense o) {
        return this.getType().toString().compareTo(o.getType().toString());
    }
}