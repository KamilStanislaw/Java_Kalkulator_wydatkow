import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public interface MonthNumberToName {
    static MonthName monthNumberToName(int month) {
        return Arrays.stream(MonthName.values()).collect(Collectors.toCollection(LinkedList::new)).get(month - 1);
    }
}
