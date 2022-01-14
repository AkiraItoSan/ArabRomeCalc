import java.util.*;
import java.util.stream.Collectors;

public class Main {



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

        boolean isRoman = RomanUtils.isRoman(inputArray); //Триггер римских чисел

        int firstValue;
        int secondValue;
        String firstInput = inputArray[0];
        String secondInput = inputArray[2];

        if (isRoman) { // Проверяем, римские числа мы складываем или нет
            firstValue = RomanUtils.ROME.get(firstInput);
            secondValue = RomanUtils.ROME.get(secondInput);
        } else if (isValidInput(firstInput,secondInput)) { // Проверяем, попадает число в диапозон от 0 до 10
            firstValue = Integer.parseInt(firstInput);
            secondValue = Integer.parseInt(secondInput);
        } else {
            throw new MyException("Числа слишком большие"); // Пробрасываем исключение, если число не входит в диапозон
        }

        int result = switch (operator) {
            case "+" -> firstValue + secondValue;
            case "-" -> firstValue - secondValue;
            case "*" -> firstValue * secondValue;
            case "/" -> firstValue / secondValue;
            default -> throw new IllegalStateException("Неверный оператор: " + operator);
        };

        if (isRoman) {
            if (result <= 0) {
                throw new MyException("Ответ не может быть меньше или равен нулю");
            }
            return RomanUtils.romanNumeralConvertor(result);
        }
        return String.valueOf(result);

    }

    private static boolean isValidInput (String value1, String value2) {
        return Integer.parseInt(value1) > 0 && Integer.parseInt(value1) < 11
                && Integer.parseInt(value2) > 0 && Integer.parseInt(value2) < 11;
    }

}