import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Map<String, Integer> ROME = new HashMap<>();


    public static void main(String[] args) throws MyException {

        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        in.close();

        System.out.println(calc(input));
    }

    public static String calc(String input) throws MyException {
        String[] inputArray = input.split(" "); //Разбиваем строку на отдельные компоненты
        String operator = inputArray[1];

        if (inputArray.length != 3) {
            throw new MyException("Неверное колличество элементов: " + inputArray.length + " из 3");
        }  //Пробрасываем исключение, если количество компонентов не равно 3-ем

        boolean isRoman = isRoman(inputArray); //Триггер римских чисел

        int value1;
        int value2;
        String firstInput = inputArray[0];
        String secondInput = inputArray[2];

        if (isRoman) {
            value1 = ROME.get(firstInput);
            value2 = ROME.get(secondInput);
        } else if (isValidInput(firstInput,secondInput)) {
            value1 = Integer.parseInt(firstInput);
            value2 = Integer.parseInt(secondInput);
        } else {
            throw new MyException("Числа слишком большие");
        }

        int result = switch (operator) {
            case "+" -> value1 + value2;
            case "-" -> value1 - value2;
            case "*" -> value1 * value2;
            case "/" -> value1 / value2;
            default -> throw new IllegalStateException("Неверный оператор: " + operator);
        };

        if (isRoman) {
            if (result <= 0) {
                throw new MyException("Ответ не может быть меньше или равен нулю");
            }
            return romanNumeralConvertor(result);
        } else {
            return String.valueOf(result);
        }
    }

    static boolean isValidInput (String value1, String value2) {
        return Integer.parseInt(value1) > 0 && Integer.parseInt(value1) < 11
                && Integer.parseInt(value2) > 0 && Integer.parseInt(value2) < 11;
    }

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

    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }
}
