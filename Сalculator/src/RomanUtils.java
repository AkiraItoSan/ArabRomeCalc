import java.util.*;
import java.util.stream.Collectors;

public class RomanUtils {
    public static final Map<String, Integer> ROME = new HashMap<>();

    static boolean isRoman(String[] input) throws MyException {
        ROME.put("I", 1);
        ROME.put("II", 2);
        ROME.put("III", 3);
        ROME.put("IV", 4);
        ROME.put("V", 5);
        ROME.put("VI", 6);
        ROME.put("VII", 7);
        ROME.put("VIII", 8);
        ROME.put("IX", 9);
        ROME.put("X", 10);

        if (ROME.containsKey(input[0]) && ROME.containsKey(input[2])) {
            return true;
        }
        if (ROME.containsKey(input[0]) || ROME.containsKey(input[2])) {
            throw new MyException("Одно из чисел арабское, другое римское");
        }
        try {
            Integer.parseInt(input[0]); //Пробуем спарсить первый элемент в Integer
            Integer.parseInt(input[2]); //Пробуем спарсить второй элемент в Integer
        } catch (NumberFormatException e) {
            if (!ROME.containsKey(input[0]) || !ROME.containsKey(input[2])) {
                throw new NumberFormatException("Неверная запись числа.");
//Пробрасываем исключение, если компонент не парсится в Integer и не находится в rome Map
            }
        }
        return false;
    }

    static String romanNumeralConvertor(int arabicNumeral) {

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((arabicNumeral > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= arabicNumeral) {
                sb.append(currentSymbol.name());
                arabicNumeral -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

}
