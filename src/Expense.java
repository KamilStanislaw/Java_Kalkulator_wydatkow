public class Expense {
    private String name;
    private double amount;
    private int month;

    public Expense(String name, double amount, int month) {
        this.name = name;
        this.amount = amount;
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getMonth() {
        return month;
    }

    public String toString() {
        return "MIESIĄC: " + month + ", " + name + " - " + amount;
    }

}
